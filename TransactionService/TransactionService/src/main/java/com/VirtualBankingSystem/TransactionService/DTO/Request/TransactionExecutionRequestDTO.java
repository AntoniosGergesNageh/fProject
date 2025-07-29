package com.VirtualBankingSystem.TransactionService.DTO.Request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionExecutionRequestDTO {
    @NotNull(message = "Transaction ID cannot be null")
    private UUID transactionId;
}
