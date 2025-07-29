package com.VirtualBankingSystem.BFFService.Client;

import com.VirtualBankingSystem.BFFService.DTO.*;
import com.VirtualBankingSystem.BFFService.Exception.ServiceInternalErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Component
public class AccountServiceClient {
    @Autowired
    @Qualifier("accountService")
    private WebClient webClient;

    public List<AccountResponseDTO> getUserAccounts(UUID userId) {
        return webClient.get()
                .uri("/users/{userId}/accounts", userId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.statusCode().value() == 400
                                ? Mono.error(new IllegalArgumentException("Bad request for user ID: " + userId))
                                : Mono.error(new RuntimeException("Client error"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("Account service server error"))
                )
                .bodyToFlux(AccountResponseDTO.class)
                .collectList()
                .block();
    }
    
    public AccountDetailResponseDTO getAccountDetails(String accountId) {
        return webClient.get()
                .uri("/accounts/{accountId}", accountId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.statusCode().value() == 404
                                ? Mono.error(new RuntimeException("Account not found with ID: " + accountId))
                                : Mono.error(new RuntimeException("Client error: " + response.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("Account service server error"))
                )
                .bodyToMono(AccountDetailResponseDTO.class)
                .block();
    }
    
    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        return webClient.post()
                .uri("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("Failed to create account: " + response.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("Account service server error"))
                )
                .bodyToMono(CreateAccountResponseDTO.class)
                .block();
    }
    
    public TransferResponseDTO transferFunds(TransferRequestDTO request) {
        return webClient.post()
                .uri("/accounts/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        response.statusCode().value() == 404
                                ? Mono.error(new RuntimeException("Account not found"))
                                : response.statusCode().value() == 400
                                ? Mono.error(new RuntimeException("Insufficient balance or invalid amount"))
                                : Mono.error(new RuntimeException("Transfer failed: " + response.statusCode()))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("Account service server error"))
                )
                .bodyToMono(TransferResponseDTO.class)
                .block();
    }
}
