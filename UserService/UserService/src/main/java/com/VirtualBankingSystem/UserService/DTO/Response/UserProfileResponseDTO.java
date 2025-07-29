package com.VirtualBankingSystem.UserService.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserProfileResponseDTO {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Timestamp createdAt;
}
