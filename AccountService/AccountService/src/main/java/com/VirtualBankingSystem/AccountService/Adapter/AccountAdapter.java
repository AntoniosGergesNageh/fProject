package com.VirtualBankingSystem.AccountService.Adapter;

import com.VirtualBankingSystem.AccountService.Constants.AccountStatus;
import com.VirtualBankingSystem.AccountService.Constants.AccountType;
import com.VirtualBankingSystem.AccountService.DTO.Request.CreateAccountRequestDTO;
import com.VirtualBankingSystem.AccountService.DTO.Response.AccountDetailsResponseDTO;
import com.VirtualBankingSystem.AccountService.Entity.Account;
import org.apache.coyote.BadRequestException;

public class AccountAdapter {
    private AccountAdapter() {
    }

    public static AccountDetailsResponseDTO convertAccountToAccountDetailsResponseDTO(Account account) {
        if (account == null) {
            return null;
        }
        AccountDetailsResponseDTO responseDTO = new AccountDetailsResponseDTO();
        responseDTO.setAccountId(account.getId());
        responseDTO.setAccountNumber(account.getAccountNumber());
        responseDTO.setAccountType(String.valueOf(account.getAccountType()));
        responseDTO.setBalance(account.getBalance());
        responseDTO.setStatus(account.getStatus().name());

        return responseDTO;
    }

    public static Account convertCreateAccountRequestDTOToAccount(CreateAccountRequestDTO createAccountRequestDTO)
            throws BadRequestException {
        if (createAccountRequestDTO == null) {
            throw new BadRequestException("Account creation request cannot be null");
        }
        Account account = new Account();
        account.setUserId(createAccountRequestDTO.getUserId());
        account.setAccountType(AccountType.valueOf(createAccountRequestDTO.getAccountType()));
        account.setBalance(createAccountRequestDTO.getInitialBalance());
        account.setStatus(AccountStatus.ACTIVE);

        return account;
    }
}
