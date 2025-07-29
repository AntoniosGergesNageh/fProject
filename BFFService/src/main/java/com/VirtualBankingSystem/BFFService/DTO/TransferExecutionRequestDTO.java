package com.VirtualBankingSystem.BFFService.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferExecutionRequestDTO {
    @NotNull(message = "Transfer ID is required")
    private UUID transferId;
    
    private String otp; // Optional One-Time Password for additional security
}
