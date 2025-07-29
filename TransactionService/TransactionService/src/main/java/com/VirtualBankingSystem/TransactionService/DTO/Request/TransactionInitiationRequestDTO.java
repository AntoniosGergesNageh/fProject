package com.VirtualBankingSystem.TransactionService.DTO.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionInitiationRequestDTO {
    @NotNull(message = "Sender Account ID cannot be null")
    private UUID fromAccountId;

    @NotNull(message = "Receiver Account ID cannot be null")
    private UUID toAccountId;

    @Positive(message = "Amount must be positive")
    @NotNull(message = "Amount cannot be null")
    private BigDecimal amount;

    private String description;
}
