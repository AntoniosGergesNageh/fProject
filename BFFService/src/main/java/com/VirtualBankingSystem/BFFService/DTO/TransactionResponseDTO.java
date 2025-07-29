package com.VirtualBankingSystem.BFFService.DTO;

import com.VirtualBankingSystem.BFFService.Constants.TransactionStatusEnum;
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
public class TransactionResponseDTO {
    private UUID transactionId;
    private BigDecimal amount;
    private UUID accountId;
    private String description;
    private Timestamp timestamp;
    private TransactionStatusEnum status;
}
