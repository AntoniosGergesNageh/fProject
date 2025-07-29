package com.VirtualBankingSystem.AccountService.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAccountResponseDTO {
    private UUID accountId;
    private String accountNumber;
    private String message;
}
