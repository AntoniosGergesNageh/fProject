package com.VirtualBankingSystem.BFFService.Service.RequestsAggregation;

import com.VirtualBankingSystem.BFFService.DTO.AccountResponseDTO;
import com.VirtualBankingSystem.BFFService.DTO.TransactionResponseDTO;
import com.VirtualBankingSystem.BFFService.DTO.UserResponseDTO;
// Line removed as it is unused.

import java.util.List;
import java.util.UUID;

public abstract class DataAggregator {
    public final UserResponseDTO aggregateUserData(UUID userId) {
        UserResponseDTO userProfile = getUserProfile(userId);
        List<AccountResponseDTO> userAccounts = getUserAccounts(userId);

        for (AccountResponseDTO account : userAccounts) {
            List<TransactionResponseDTO> transactions = getUserTransactions(account.getAccountId());
            account.setTransactions(transactions);
        }

        userProfile.setAccounts(userAccounts);
        return userProfile;
    }

    protected abstract UserResponseDTO getUserProfile(UUID userId);
    protected abstract List<AccountResponseDTO> getUserAccounts(UUID userId);
    protected abstract List<TransactionResponseDTO> getUserTransactions(UUID accountId);
}
