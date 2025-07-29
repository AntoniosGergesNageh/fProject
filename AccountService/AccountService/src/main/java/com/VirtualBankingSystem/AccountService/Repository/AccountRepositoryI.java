package com.VirtualBankingSystem.AccountService.Repository;

import com.VirtualBankingSystem.AccountService.DTO.Response.AccountDetailsResponseDTO;
import com.VirtualBankingSystem.AccountService.Entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface AccountRepositoryI extends JpaRepository<Account, UUID> {
    boolean existsByAccountNumber(String accountNumber);

    @Query("SELECT new com.VirtualBankingSystem.AccountService.DTO.Response.AccountDetailsResponseDTO(" +
            "a.id, a.accountNumber, CAST(a.accountType AS string), a.balance, CAST(a.status AS string)) " +
            "FROM Account a WHERE a.userId = :userId")
    List<AccountDetailsResponseDTO> findByUserId(@Param("userId") UUID userId);

    @Modifying
    @Transactional
    @Query("UPDATE Account a SET a.status = 'INACTIVE' " +
            "WHERE a.status = 'ACTIVE' AND a.updatedAt < :cutoffDate")
    void updateAccountsStatus(@Param("cutoffDate") LocalDateTime cutoffDate);
}
