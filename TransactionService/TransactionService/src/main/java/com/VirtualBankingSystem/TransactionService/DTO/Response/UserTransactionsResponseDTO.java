package com.VirtualBankingSystem.TransactionService.DTO.Response;

import com.VirtualBankingSystem.TransactionService.Constants.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserTransactionsResponseDTO {
    private UUID transactionId;
    private UUID accountId;
    private BigDecimal amount;
    private String description;
    private TransactionStatusEnum status;
    private Timestamp timestamp;
}
