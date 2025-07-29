package com.VirtualBankingSystem.UserService.DTO.Response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateAndLoginUserResponseDTO {
    private UUID userId;
    private String username;
    private String message;
}
