package com.VirtualBankingSystem.TransactionService.Controller;

import com.VirtualBankingSystem.TransactionService.DTO.Response.UserTransactionsResponseDTO;
import com.VirtualBankingSystem.TransactionService.Service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Validated
@RestController
@RequestMapping("/accounts")
public class AccountController {
    @Autowired
    private TransactionService transactionService;

    @GetMapping("/{accountId}/transactions")
    public ResponseEntity<List<UserTransactionsResponseDTO>> getAccountTransactions(@PathVariable(name = "accountId") UUID accountId) {
        return ResponseEntity.ok(transactionService.getAccountTransactions(accountId));
    }
}
