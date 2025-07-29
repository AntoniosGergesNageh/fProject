package com.VirtualBankingSystem.BFFService.Client;

import com.VirtualBankingSystem.BFFService.DTO.*;
import com.VirtualBankingSystem.BFFService.Exception.ServiceInternalErrorException;
import com.VirtualBankingSystem.BFFService.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserServiceClient {
    @Autowired
    @Qualifier("userService")
    private WebClient webClient;

    public UserProfileResponseDTO getUserProfile(UUID userId){
        return webClient.get()
                .uri("/users/{userId}/profile", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.statusCode().value() == 404
                                ? Mono.error(new UserNotFoundException("User not found with ID: " + userId))
                                : response.statusCode().value() == 400
                                ? Mono.error(new IllegalArgumentException("Bad request for user ID: " + userId))
                                : Mono.error(new RuntimeException("Client error"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("User service server error"))
                )
                .bodyToMono(UserProfileResponseDTO.class)
                .block();
    }
    
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO request) {
        return webClient.post()
                .uri("/users/login")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.statusCode().value() == 401
                                ? Mono.error(new RuntimeException("Invalid username or password"))
                                : Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("User service server error"))
                )
                .bodyToMono(UserLoginResponseDTO.class)
                .block();
    }
    
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO request) {
        // Convert BFF UserRegistrationRequestDTO to UserService CreateUserRequestDTO
        CreateUserRequestDTO createUserRequest = convertToCreateUserRequest(request);
        
        return webClient.post()
                .uri("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(createUserRequest))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.statusCode().value() == 409
                                ? Mono.error(new RuntimeException("User already exists"))
                                : Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("User service server error"))
                )
                .bodyToMono(CreateAndLoginUserResponseDTO.class)
                .map(this::convertToUserRegistrationResponse)
                .block();
    }
    
    private CreateUserRequestDTO convertToCreateUserRequest(UserRegistrationRequestDTO request) {
        CreateUserRequestDTO createUserRequest = new CreateUserRequestDTO();
        createUserRequest.setUsername(request.getUsername());
        createUserRequest.setPassword(request.getPassword());
        createUserRequest.setEmail(request.getEmail());
        createUserRequest.setFirstName(request.getFirstName());
        createUserRequest.setLastName(request.getLastName());
        
        return createUserRequest;
    }
    
    private UserRegistrationResponseDTO convertToUserRegistrationResponse(CreateAndLoginUserResponseDTO response) {
        UserRegistrationResponseDTO registrationResponse = new UserRegistrationResponseDTO();
        registrationResponse.setMessage(response.getMessage());
        registrationResponse.setUserId(response.getUserId());
        registrationResponse.setUsername(response.getUsername());
        return registrationResponse;
    }
}
