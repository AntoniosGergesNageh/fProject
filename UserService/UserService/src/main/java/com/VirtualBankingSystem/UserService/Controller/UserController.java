package com.VirtualBankingSystem.UserService.Controller;

import com.VirtualBankingSystem.UserService.Adapter.UserAdapter;
import com.VirtualBankingSystem.UserService.DTO.Request.CreateUserRequestDTO;
import com.VirtualBankingSystem.UserService.DTO.Request.UserLoginRequestDTO;
import com.VirtualBankingSystem.UserService.DTO.Response.CreateAndLoginUserResponseDTO;
import com.VirtualBankingSystem.UserService.DTO.Response.UserProfileResponseDTO;
import com.VirtualBankingSystem.UserService.Entity.User;
import com.VirtualBankingSystem.UserService.Exceptions.InvalidUsernameOrPassword;
import com.VirtualBankingSystem.UserService.Exceptions.UserAlreadyExistsException;
import com.VirtualBankingSystem.UserService.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin; 
import java.util.UUID;
@CrossOrigin(origins = "*")
@Validated
@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<CreateAndLoginUserResponseDTO> createUser(@RequestBody @Valid CreateUserRequestDTO user) {
        User newUser = UserAdapter.convertCreateUserRequestToUser(user);
        if (userService.saveUser(newUser)) {
            CreateAndLoginUserResponseDTO response = new CreateAndLoginUserResponseDTO();
            response.setMessage("User created successfully");
            response.setUserId(newUser.getId());
            response.setUsername(newUser.getUsername());
            return ResponseEntity.ok(response);
        } else {
            throw new UserAlreadyExistsException("User with this username or email already exists.");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<CreateAndLoginUserResponseDTO> loginUser(@RequestBody @Valid UserLoginRequestDTO user) {
        if (userService.loginUser(user.getUsername(), user.getPassword())) {
            CreateAndLoginUserResponseDTO response = new CreateAndLoginUserResponseDTO();
            User existingUser = userService.getUserByUsername(user.getUsername());
            response.setMessage("Login successful");
            response.setUserId(existingUser.getId());
            response.setUsername(existingUser.getUsername());
            return ResponseEntity.ok(response);
        } else {
            throw new InvalidUsernameOrPassword("Invalid username or password.");
        }
    }

    @GetMapping("/{userId}/profile")
    public ResponseEntity<UserProfileResponseDTO> getUserProfile(@PathVariable UUID userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(UserAdapter.convertUserToUserProfileResponse(user));
    }
}
