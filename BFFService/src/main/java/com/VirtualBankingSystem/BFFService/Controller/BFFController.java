package com.VirtualBankingSystem.BFFService.Controller;

import com.VirtualBankingSystem.BFFService.DTO.*;
import com.VirtualBankingSystem.BFFService.Service.BFFService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@CrossOrigin(origins = "*") // Allow requests from front-end
public class BFFController {
    @Autowired
    private BFFService bffService;

    // User endpoints that forward to UserService
    @PostMapping("/users/login")
    public ResponseEntity<UserLoginResponseDTO> loginUser(@Valid @RequestBody UserLoginRequestDTO request) {
        return ResponseEntity.ok(bffService.loginUser(request));
    }
    
    @PostMapping("/users/register")
    public ResponseEntity<UserRegistrationResponseDTO> registerUser(@Valid @RequestBody UserRegistrationRequestDTO request) {
        return ResponseEntity.ok(bffService.registerUser(request));
    }
    
    @GetMapping("/users/{userId}/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(@NotNull @PathVariable UUID userId) {
        return ResponseEntity.ok(bffService.getUserProfile(userId));
    }

    // Account endpoints that forward to AccountService
    @GetMapping("/users/{userId}/accounts")
    public ResponseEntity<List<AccountResponseDTO>> getUserAccounts(@NotNull @PathVariable UUID userId) {
        return ResponseEntity.ok(bffService.getUserAccounts(userId));
    }
    
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDetailResponseDTO> getAccountDetails(@NotBlank @PathVariable String accountId) {
        return ResponseEntity.ok(bffService.getAccountDetails(accountId));
    }
    
    @PostMapping("/accounts")
    public ResponseEntity<CreateAccountResponseDTO> createAccount(@Valid @RequestBody CreateAccountRequestDTO request) {
        return ResponseEntity.ok(bffService.createAccount(request));
    }
    
    @PostMapping("/accounts/transfer")
    public ResponseEntity<TransferResponseDTO> transferFunds(@Valid @RequestBody TransferRequestDTO request) {
        return ResponseEntity.ok(bffService.transferFunds(request));
    }

    // Transaction endpoints that forward to TransactionService
    @PostMapping("/transactions/transfer/initiation")
    public ResponseEntity<TransferInitiationResponseDTO> initiateTransfer(
            @Valid @RequestBody TransferInitiationRequestDTO request) {
        return ResponseEntity.ok(bffService.initiateTransfer(request));
    }
    
    @PostMapping("/transactions/transfer/execution")
    public ResponseEntity<TransferExecutionResponseDTO> executeTransfer(
            @Valid @RequestBody TransferExecutionRequestDTO request) {
        return ResponseEntity.ok(bffService.executeTransfer(request));
    }
    
    // Dashboard endpoint for combined data
    @GetMapping("/dashboard/{userId}")
    public ResponseEntity<UserResponseDTO> getDashboard(@NotNull @PathVariable UUID userId) {
        return ResponseEntity.ok(bffService.getUserData(userId));
    }
}
