package com.VirtualBankingSystem.TransactionService.Adapter;

import com.VirtualBankingSystem.TransactionService.DTO.Request.TransactionInitiationRequestDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Response.TransactionResponseDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Response.UserTransactionsResponseDTO;
import com.VirtualBankingSystem.TransactionService.Entity.Transaction;

import java.util.UUID;

public class TransactionAdapter {
    public static Transaction convertTransactionInitiationRequestDTOToTransaction(
            TransactionInitiationRequestDTO transactionInitiationRequestDTO) {
        Transaction transaction = new Transaction();
        transaction.setFromAccountId(transactionInitiationRequestDTO.getFromAccountId());
        transaction.setToAccountId(transactionInitiationRequestDTO.getToAccountId());
        transaction.setAmount(transactionInitiationRequestDTO.getAmount());
        transaction.setDescription(transactionInitiationRequestDTO.getDescription());

        return transaction;
    }

    public static TransactionResponseDTO convertTransactionToTransactionResponseDTO(Transaction transaction) {
        TransactionResponseDTO transactionResponseDTO = new TransactionResponseDTO();
        transactionResponseDTO.setTransactionId(transaction.getId());
        transactionResponseDTO.setStatus(transaction.getStatus());
        transactionResponseDTO.setTimestamp(transaction.getTimestamp());

        return transactionResponseDTO;
    }

    public static UserTransactionsResponseDTO convertTransactionToUserTransactionsResponseDTO(
            Transaction transaction,
            UUID accountId) {
        UserTransactionsResponseDTO userTransaction = new UserTransactionsResponseDTO();
        userTransaction.setTransactionId(transaction.getId());
        userTransaction.setAccountId(accountId);
        userTransaction.setAmount(transaction.getAmount());
        userTransaction.setDescription(transaction.getDescription());
        userTransaction.setStatus(transaction.getStatus());
        userTransaction.setTimestamp(transaction.getTimestamp());

        return userTransaction;
    }
}
