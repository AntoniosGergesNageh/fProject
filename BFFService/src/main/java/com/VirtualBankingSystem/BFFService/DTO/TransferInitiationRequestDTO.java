package com.VirtualBankingSystem.BFFService.DTO;

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
public class TransferInitiationRequestDTO {
    @NotNull(message = "Source account ID is required")
    private UUID sourceAccountId;
    
    @NotNull(message = "Destination account ID is required")
    private UUID destinationAccountId;
    
    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    private String description;
}
