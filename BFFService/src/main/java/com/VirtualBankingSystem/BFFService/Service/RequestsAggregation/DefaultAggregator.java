package com.VirtualBankingSystem.BFFService.Service.RequestsAggregation;

import com.VirtualBankingSystem.BFFService.Client.AccountServiceClient;
import com.VirtualBankingSystem.BFFService.Client.TransactionServiceClient;
import com.VirtualBankingSystem.BFFService.Client.UserServiceClient;
import com.VirtualBankingSystem.BFFService.DTO.AccountResponseDTO;
import com.VirtualBankingSystem.BFFService.DTO.TransactionResponseDTO;
import com.VirtualBankingSystem.BFFService.DTO.UserProfileResponseDTO;
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
        UserProfileResponseDTO profileDTO = userServiceClient.getUserProfile(userId);
        // Convert UserProfileResponseDTO to UserResponseDTO
        UserResponseDTO responseDTO = new UserResponseDTO();
        responseDTO.setId(profileDTO.getId());
        responseDTO.setFirstName(profileDTO.getFirstName());
        responseDTO.setLastName(profileDTO.getLastName());
        responseDTO.setEmail(profileDTO.getEmail());
        responseDTO.setUsername(profileDTO.getUsername());
        responseDTO.setCreatedAt(profileDTO.getCreatedAt());
        // Other fields will be null or set later
        return responseDTO;
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
