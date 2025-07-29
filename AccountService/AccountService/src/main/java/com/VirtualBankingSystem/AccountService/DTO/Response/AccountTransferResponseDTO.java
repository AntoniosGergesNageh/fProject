package com.VirtualBankingSystem.AccountService.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AccountTransferResponseDTO {
    private String message;
    private int status;
}
