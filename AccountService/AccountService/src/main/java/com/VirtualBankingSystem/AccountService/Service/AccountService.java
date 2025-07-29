package com.VirtualBankingSystem.AccountService.Service;

import com.VirtualBankingSystem.AccountService.Constants.AccountStatus;
import com.VirtualBankingSystem.AccountService.DTO.Response.AccountDetailsResponseDTO;
import com.VirtualBankingSystem.AccountService.Entity.Account;
import com.VirtualBankingSystem.AccountService.Exceptions.AccountNotFoundException;
import com.VirtualBankingSystem.AccountService.Exceptions.InsufficientBalanceException;
import com.VirtualBankingSystem.AccountService.Exceptions.UserNotFoundException;
import com.VirtualBankingSystem.AccountService.Repository.AccountRepositoryI;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AccountService {
    @Autowired
    private AccountRepositoryI accountRepository;

    public Account getAccountById(String accountId) throws BadRequestException {
        if (accountId == null || accountId.isEmpty()) {
            throw new BadRequestException("Account ID cannot be null or empty");
        }
        Account account = accountRepository.findById(UUID.fromString(accountId)).orElse(null);
        if (account == null) {
            throw new AccountNotFoundException("Account with ID " + accountId + " not found");
        }
        return account;
    }

    @Transactional
    public void createAccount(Account account) throws BadRequestException {
        if (account == null || account.getUserId() == null || account.getAccountType() == null) {
            throw new BadRequestException("Account details are incomplete");
        }
        if (account.getBalance().doubleValue() < 0) {
            throw new BadRequestException("Initial balance cannot be negative");
        }
        account.setAccountNumber(this.generateAccountNumber());
        account.setStatus(AccountStatus.ACTIVE);
        account.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        accountRepository.save(account);
    }

    public List<AccountDetailsResponseDTO> getUserAccounts(String userId) throws BadRequestException {
        if (userId == null || userId.isEmpty()) {
            throw new BadRequestException("User ID cannot be null or empty");
        }
        List<AccountDetailsResponseDTO> userAccounts = accountRepository.findByUserId(UUID.fromString(userId));
        if (userAccounts == null || userAccounts.isEmpty()) {
            throw new UserNotFoundException("No accounts found for user with ID " + userId);
        }
        return userAccounts;
    }

    @Transactional
    public void transferBalance(UUID fromAccountId, UUID toAccountId, BigDecimal amount) throws BadRequestException {
        if (fromAccountId == null || toAccountId == null || amount.doubleValue() <= 0) {
            throw new BadRequestException("Invalid transfer parameters");
        }

        Account fromAccount = getAccountById(fromAccountId.toString());
        Account toAccount = getAccountById(toAccountId.toString());

        if (fromAccount == null || toAccount == null) {
            throw new AccountNotFoundException("One or both accounts do not exist");
        }

        if (fromAccount.getBalance().doubleValue() < amount.doubleValue()) {
            throw new InsufficientBalanceException("Insufficient balance in the source account");
        }

        fromAccount.setBalance(fromAccount.getBalance().subtract(amount));
        toAccount.setBalance(toAccount.getBalance().add(amount));

        fromAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        toAccount.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

        fromAccount.setStatus(AccountStatus.ACTIVE);
        toAccount.setStatus(AccountStatus.ACTIVE);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    @Scheduled(fixedRate = 5 * 60 * 1000) // Runs every 5 minutes
    protected void inactivateAccounts() {
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(1);
        accountRepository.updateAccountsStatus(cutoffDate);
    }

    private String generateAccountNumber() {
        String accountNumber;
        do {
            accountNumber = UtilityService.generateRandom20DigitNumber();
        } while (accountRepository.existsByAccountNumber(accountNumber));
        return accountNumber;
    }
}
