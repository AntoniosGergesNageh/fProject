package com.VirtualBankingSystem.BFFService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationResponseDTO {
    private String message;
    private UUID userId;
    private String username;
}
