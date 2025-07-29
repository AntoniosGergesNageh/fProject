package com.VirtualBankingSystem.BFFService.DTO;

import com.VirtualBankingSystem.BFFService.Constants.TransactionStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferExecutionResponseDTO {
    private UUID transferId;
    private UUID transactionId;
    private TransactionStatusEnum status;
    private BigDecimal sourceAccountNewBalance;
    private BigDecimal destinationAccountNewBalance;
    private String message;
}
