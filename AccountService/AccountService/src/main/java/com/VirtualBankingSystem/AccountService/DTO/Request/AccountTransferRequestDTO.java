package com.VirtualBankingSystem.AccountService.DTO.Request;

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
public class AccountTransferRequestDTO {
    @NotNull(message = "Sender account can not be empty")
    private UUID fromAccountId;

    @NotNull(message = "Receiver account can not be empty")
    private UUID toAccountId;

    @Positive(message = "Balance must be greater than zero")
    private BigDecimal amount;
}
