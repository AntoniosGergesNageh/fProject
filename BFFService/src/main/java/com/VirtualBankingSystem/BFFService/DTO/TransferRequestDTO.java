package com.VirtualBankingSystem.BFFService.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferRequestDTO {
    @NotBlank(message = "From account ID cannot be blank")
    private String fromAccountId;
    
    @NotBlank(message = "To account ID cannot be blank")
    private String toAccountId;
    
    @NotNull(message = "Amount cannot be null")
    @Positive(message = "Amount must be positive")
    private BigDecimal amount;
    
    private String description;
}
