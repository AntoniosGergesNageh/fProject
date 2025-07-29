package com.VirtualBankingSystem.BFFService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountResponseDTO {
    private String id;
    private String accountNumber;
    private String message;
}
