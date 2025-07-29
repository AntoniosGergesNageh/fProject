package com.VirtualBankingSystem.UserService.Adapter;


import com.VirtualBankingSystem.UserService.DTO.Request.CreateUserRequestDTO;
import com.VirtualBankingSystem.UserService.DTO.Response.UserProfileResponseDTO;
import com.VirtualBankingSystem.UserService.Entity.User;

import java.sql.Timestamp;

public class UserAdapter {
    public static User convertCreateUserRequestToUser(CreateUserRequestDTO request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        return user;
    }

    public static UserProfileResponseDTO convertUserToUserProfileResponse(User user) {
        UserProfileResponseDTO response = new UserProfileResponseDTO();
        response.setId(user.getId());
        response.setFirstName(user.getFirstName());
        response.setLastName(user.getLastName());
        response.setEmail(user.getEmail());
        response.setUsername(user.getUsername());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}
