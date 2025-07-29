package com.VirtualBankingSystem.TransactionService.Controller;

import com.VirtualBankingSystem.TransactionService.DTO.Request.TransactionExecutionRequestDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Request.TransactionInitiationRequestDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Response.TransactionResponseDTO;
import com.VirtualBankingSystem.TransactionService.Service.TransactionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/transactions")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer/initiation")
    public ResponseEntity<TransactionResponseDTO> initiateTransaction(
            @RequestBody @Valid TransactionInitiationRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.initiateTransaction(transaction));
    }

    @PostMapping("/transfer/execution")
    public ResponseEntity<TransactionResponseDTO> executeTransaction(
            @RequestBody @Valid TransactionExecutionRequestDTO transaction) {
        return ResponseEntity.ok(transactionService.executeTransaction(transaction));
    }
}
