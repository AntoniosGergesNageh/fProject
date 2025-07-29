package com.VirtualBankingSystem.AccountService.Controller;

import com.VirtualBankingSystem.AccountService.Adapter.AccountAdapter;
import com.VirtualBankingSystem.AccountService.DTO.Request.AccountTransferRequestDTO;
import com.VirtualBankingSystem.AccountService.DTO.Request.CreateAccountRequestDTO;
import com.VirtualBankingSystem.AccountService.DTO.Response.AccountDetailsResponseDTO;
import com.VirtualBankingSystem.AccountService.DTO.Response.AccountTransferResponseDTO;
import com.VirtualBankingSystem.AccountService.DTO.Response.CreateAccountResponseDTO;
import com.VirtualBankingSystem.AccountService.Entity.Account;
import com.VirtualBankingSystem.AccountService.Exceptions.AccountNotFoundException;
import com.VirtualBankingSystem.AccountService.Exceptions.InsufficientBalanceException;
import com.VirtualBankingSystem.AccountService.Service.AccountService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @PostMapping("/transfer")
    public ResponseEntity<AccountTransferResponseDTO> transferBalance(@RequestBody @Valid AccountTransferRequestDTO transferRequest)
            throws BadRequestException, InsufficientBalanceException, AccountNotFoundException {
        accountService.transferBalance(transferRequest.getFromAccountId(),
                transferRequest.getToAccountId(),
                transferRequest.getAmount());

        return ResponseEntity.ok(new AccountTransferResponseDTO("Transfer successful", 200));
    }

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDetailsResponseDTO> getAccountDetails(@NotBlank @PathVariable("accountId") String accountId)
            throws BadRequestException, AccountNotFoundException {
        Account userAccount = accountService.getAccountById(accountId);
        AccountDetailsResponseDTO accountDetails = AccountAdapter.convertAccountToAccountDetailsResponseDTO(userAccount);
        return ResponseEntity.ok(accountDetails);
    }

    @PostMapping
    public ResponseEntity<CreateAccountResponseDTO> createBankAccount(@RequestBody @Valid CreateAccountRequestDTO createAccountRequestDTO)
            throws BadRequestException {
        Account account = AccountAdapter.convertCreateAccountRequestDTOToAccount(createAccountRequestDTO);
        accountService.createAccount(account);
        CreateAccountResponseDTO response = new CreateAccountResponseDTO(
                account.getId(),
                account.getAccountNumber(),
                "Account created successfully"
        );
        return ResponseEntity.status(201).body(response);
    }
}
