package com.VirtualBankingSystem.AccountService.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
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
public class CreateAccountRequestDTO {
    @NotNull(message = "User id can not be empty")
    private UUID userId;

    @NotBlank(message = "Account type can not be empty")
    private String accountType;

    @PositiveOrZero(message = "Balance can not be negative")
    private BigDecimal initialBalance;
}
