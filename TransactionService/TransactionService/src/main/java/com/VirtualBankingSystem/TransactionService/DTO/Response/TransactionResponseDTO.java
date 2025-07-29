package com.VirtualBankingSystem.TransactionService.DTO.Response;

import com.VirtualBankingSystem.TransactionService.Constants.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponseDTO {
    private UUID transactionId;
    private TransactionStatusEnum status;
    private Timestamp timestamp;
}
