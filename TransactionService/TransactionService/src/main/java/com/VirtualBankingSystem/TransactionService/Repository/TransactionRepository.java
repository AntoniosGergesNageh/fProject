package com.VirtualBankingSystem.TransactionService.Repository;

import com.VirtualBankingSystem.TransactionService.Entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

@EnableJpaRepositories
public interface TransactionRepository extends JpaRepository<Transaction, UUID> {
    @Query("SELECT t FROM Transaction t" +
            " WHERE t.fromAccountId = :accountId OR t.toAccountId = :accountId" +
            " ORDER BY t.timestamp DESC")
    List<Transaction> findAllByAccountIdOrderByTimestampDesc(@Param("accountId") UUID accountId);
}
