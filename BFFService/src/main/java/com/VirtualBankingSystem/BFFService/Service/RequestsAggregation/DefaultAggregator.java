package com.VirtualBankingSystem.BFFService.Service.RequestsAggregation;

import com.VirtualBankingSystem.BFFService.Client.AccountServiceClient;
import com.VirtualBankingSystem.BFFService.Client.TransactionServiceClient;
import com.VirtualBankingSystem.BFFService.Client.UserServiceClient;
import com.VirtualBankingSystem.BFFService.DTO.AccountResponseDTO;
import com.VirtualBankingSystem.BFFService.DTO.TransactionResponseDTO;
import com.VirtualBankingSystem.BFFService.DTO.UserResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class DefaultAggregator extends DataAggregator {
    @Autowired
    private AccountServiceClient accountServiceClient;

    @Autowired
    private UserServiceClient userServiceClient;

    @Autowired
    private TransactionServiceClient transactionServiceClient;

    @Override
    protected UserResponseDTO getUserProfile(UUID userId) {
        return userServiceClient.getUserProfile(userId);
    }

    @Override
    protected List<AccountResponseDTO> getUserAccounts(UUID userId) {
        return accountServiceClient.getUserAccounts(userId);
    }

    @Override
    protected List<TransactionResponseDTO> getUserTransactions(UUID accountId) {
        return transactionServiceClient.getAccountTransactions(accountId);
    }
}
