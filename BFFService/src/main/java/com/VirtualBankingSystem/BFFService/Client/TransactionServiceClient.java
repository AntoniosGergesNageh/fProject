package com.VirtualBankingSystem.BFFService.Client;

import com.VirtualBankingSystem.BFFService.DTO.TransactionResponseDTO;
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
public class TransactionServiceClient {
    @Autowired
    @Qualifier("transactionService")
    private WebClient webClient;

    public List<TransactionResponseDTO> getAccountTransactions(UUID accountId) {
        return webClient.get()
                .uri("/accounts/{accountId}/transactions", accountId)
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, response ->
                        Mono.error(new RuntimeException("Client error"))
                )
                .onStatus(HttpStatusCode::is5xxServerError, response ->
                        Mono.error(new ServiceInternalErrorException("Transaction service server error"))
                )
                .bodyToFlux(TransactionResponseDTO.class)
                .collectList()
                .block();
    }
}
