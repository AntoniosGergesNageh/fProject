package com.VirtualBankingSystem.BFFService.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Timestamp createdAt;
}
