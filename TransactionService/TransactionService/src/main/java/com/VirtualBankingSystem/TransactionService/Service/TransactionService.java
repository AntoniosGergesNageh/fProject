package com.VirtualBankingSystem.TransactionService.Service;

import com.VirtualBankingSystem.TransactionService.Adapter.TransactionAdapter;
import com.VirtualBankingSystem.TransactionService.Constants.TransactionStatusEnum;
import com.VirtualBankingSystem.TransactionService.DTO.Request.TransactionExecutionRequestDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Request.TransactionInitiationRequestDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Response.TransactionResponseDTO;
import com.VirtualBankingSystem.TransactionService.DTO.Response.UserTransactionsResponseDTO;
import com.VirtualBankingSystem.TransactionService.Entity.Transaction;
import com.VirtualBankingSystem.TransactionService.Exception.TransactionNotFoundException;
import com.VirtualBankingSystem.TransactionService.Repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponseDTO initiateTransaction(TransactionInitiationRequestDTO request) {
        Transaction transaction = TransactionAdapter.convertTransactionInitiationRequestDTOToTransaction(request);
        transaction.setStatus(TransactionStatusEnum.INITIATED);
        transactionRepository.save(transaction);

        return TransactionAdapter.convertTransactionToTransactionResponseDTO(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    public TransactionResponseDTO executeTransaction(TransactionExecutionRequestDTO request) {
        Transaction transaction = transactionRepository.findById(request.getTransactionId()).orElse(null);

        if (transaction == null) {
            throw new TransactionNotFoundException("Transaction " + request.getTransactionId() + " not found");
        }
        if (transaction.getStatus() != TransactionStatusEnum.INITIATED) {
            throw new IllegalStateException("Transaction cannot be executed in its current state");
        }

        try {
            transaction.setStatus(TransactionStatusEnum.SUCCESS);
            transactionRepository.save(transaction);
        } catch (Exception e) {
            transaction.setStatus(TransactionStatusEnum.FAILED);
            transactionRepository.save(transaction);
            throw e;
        }

        return TransactionAdapter.convertTransactionToTransactionResponseDTO(transaction);
    }

    public List<UserTransactionsResponseDTO> getAccountTransactions(UUID accountId) {
        List<Transaction> accountTransactions = transactionRepository.findAllByAccountIdOrderByTimestampDesc(accountId);

        if (accountTransactions.isEmpty()) {
            return new ArrayList<>();
        }

        for (Transaction transaction : accountTransactions) {
            if(transaction.getFromAccountId().equals(accountId)) {
                transaction.setAmount(transaction.getAmount().multiply(BigDecimal.valueOf(-1)));
            }
        }

        List<UserTransactionsResponseDTO> userTransactions = new ArrayList<>(accountTransactions.size());
        for (Transaction transaction : accountTransactions) {
            UserTransactionsResponseDTO userTransaction = TransactionAdapter.convertTransactionToUserTransactionsResponseDTO(transaction, accountId);
            userTransactions.add(userTransaction);
        }

        return userTransactions;
    }
}
