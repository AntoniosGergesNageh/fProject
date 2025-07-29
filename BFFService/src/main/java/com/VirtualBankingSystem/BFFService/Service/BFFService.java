package com.VirtualBankingSystem.BFFService.Service;

import com.VirtualBankingSystem.BFFService.Client.TransactionServiceClient;
import com.VirtualBankingSystem.BFFService.DTO.*;
import com.VirtualBankingSystem.BFFService.Service.RequestsAggregation.DefaultAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BFFService {
    private final DefaultAggregator defaultAggregator;
    private final TransactionServiceClient transactionServiceClient;

    public BFFService(
            @Autowired DefaultAggregator defaultAggregator,
            @Autowired TransactionServiceClient transactionServiceClient) {
        this.defaultAggregator = defaultAggregator;
        this.transactionServiceClient = transactionServiceClient;
    }

    public UserResponseDTO getUserData(UUID userId) {
        return defaultAggregator.aggregateUserData(userId);
    }
    
    public TransferInitiationResponseDTO initiateTransfer(TransferInitiationRequestDTO request) {
        // Delegate to the transaction service client
        return transactionServiceClient.initiateTransfer(request);
    }
    
    public TransferExecutionResponseDTO executeTransfer(TransferExecutionRequestDTO request) {
        // Delegate to the transaction service client
        return transactionServiceClient.executeTransfer(request);
    }
}
