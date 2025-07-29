package com.VirtualBankingSystem.BFFService.Service;

import com.VirtualBankingSystem.BFFService.Client.AccountServiceClient;
import com.VirtualBankingSystem.BFFService.Client.TransactionServiceClient;
import com.VirtualBankingSystem.BFFService.Client.UserServiceClient;
import com.VirtualBankingSystem.BFFService.DTO.*;
import com.VirtualBankingSystem.BFFService.Service.RequestsAggregation.DefaultAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BFFService {
    private final DefaultAggregator defaultAggregator;
    private final TransactionServiceClient transactionServiceClient;
    private final AccountServiceClient accountServiceClient;
    private final UserServiceClient userServiceClient;

    public BFFService(
            @Autowired DefaultAggregator defaultAggregator,
            @Autowired TransactionServiceClient transactionServiceClient,
            @Autowired AccountServiceClient accountServiceClient,
            @Autowired UserServiceClient userServiceClient) {
        this.defaultAggregator = defaultAggregator;
        this.transactionServiceClient = transactionServiceClient;
        this.accountServiceClient = accountServiceClient;
        this.userServiceClient = userServiceClient;
    }

    public UserResponseDTO getUserData(UUID userId) {
        return defaultAggregator.aggregateUserData(userId);
    }
    
    // User service methods
    public UserLoginResponseDTO loginUser(UserLoginRequestDTO request) {
        return userServiceClient.loginUser(request);
    }
    
    public UserRegistrationResponseDTO registerUser(UserRegistrationRequestDTO request) {
        return userServiceClient.registerUser(request);
    }
    
    public UserProfileResponseDTO getUserProfile(UUID userId) {
        return userServiceClient.getUserProfile(userId);
    }
    
    // Account service methods
    public List<AccountResponseDTO> getUserAccounts(UUID userId) {
        return accountServiceClient.getUserAccounts(userId);
    }
    
    public AccountDetailResponseDTO getAccountDetails(String accountId) {
        return accountServiceClient.getAccountDetails(accountId);
    }
    
    public CreateAccountResponseDTO createAccount(CreateAccountRequestDTO request) {
        return accountServiceClient.createAccount(request);
    }
    
    public TransferResponseDTO transferFunds(TransferRequestDTO request) {
        return accountServiceClient.transferFunds(request);
    }
    
    // Transaction service methods
    public TransferInitiationResponseDTO initiateTransfer(TransferInitiationRequestDTO request) {
        return transactionServiceClient.initiateTransfer(request);
    }
    
    public TransferExecutionResponseDTO executeTransfer(TransferExecutionRequestDTO request) {
        return transactionServiceClient.executeTransfer(request);
    }
}
