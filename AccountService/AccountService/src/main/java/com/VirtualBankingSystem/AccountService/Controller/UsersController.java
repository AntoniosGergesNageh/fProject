package com.VirtualBankingSystem.AccountService.Controller;

import com.VirtualBankingSystem.AccountService.DTO.Response.AccountDetailsResponseDTO;
import com.VirtualBankingSystem.AccountService.Exceptions.UserNotFoundException;
import com.VirtualBankingSystem.AccountService.Service.AccountService;
import jakarta.validation.constraints.NotBlank;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private AccountService accountService;

    @GetMapping("/{userId}/accounts")
    public ResponseEntity<List<AccountDetailsResponseDTO>> getUserAccounts(@NotBlank @PathVariable("userId") String userId)
            throws BadRequestException, UserNotFoundException {
        List<AccountDetailsResponseDTO> accounts = accountService.getUserAccounts(userId);
        return ResponseEntity.ok(accounts);
    }
}
