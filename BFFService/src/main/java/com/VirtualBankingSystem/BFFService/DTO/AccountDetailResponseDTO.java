package com.VirtualBankingSystem.BFFService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDetailResponseDTO {
    private String id;
    private String accountNumber;
    private String accountType;
    private BigDecimal balance;
    private String holderName;
    private LocalDateTime createdAt;
    private String currency;
    private List<TransactionResponseDTO> recentTransactions;
}
