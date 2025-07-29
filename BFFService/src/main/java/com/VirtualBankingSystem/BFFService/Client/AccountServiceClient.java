package com.VirtualBankingSystem.BFFService.Client;

import com.VirtualBankingSystem.BFFService.DTO.AccountResponseDTO;
import com.VirtualBankingSystem.BFFService.Exception.ServiceInternalErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Component;
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
}
