package com.VirtualBankingSystem.BFFService.Client;

import com.VirtualBankingSystem.BFFService.DTO.UserResponseDTO;
import com.VirtualBankingSystem.BFFService.Exception.ServiceInternalErrorException;
import com.VirtualBankingSystem.BFFService.Exception.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
public class UserServiceClient {
    @Autowired
    @Qualifier("userService")
    private WebClient webClient;

    public UserResponseDTO getUserProfile(UUID userId){
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
                .bodyToMono(UserResponseDTO.class)
                .block();
    }
}
