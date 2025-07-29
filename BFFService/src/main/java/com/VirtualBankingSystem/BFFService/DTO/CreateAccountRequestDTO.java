package com.VirtualBankingSystem.BFFService.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequestDTO {
    @NotNull(message = "User ID cannot be null")
    private UUID userId;
    
    @NotBlank(message = "Account type cannot be blank")
    private String accountType;
    
    @NotNull(message = "Initial deposit cannot be null")
    @Positive(message = "Initial deposit must be positive")
    private BigDecimal initialDeposit;
    
    private String holderName;
    
    private String email;
    
    private String phoneNumber;
}
