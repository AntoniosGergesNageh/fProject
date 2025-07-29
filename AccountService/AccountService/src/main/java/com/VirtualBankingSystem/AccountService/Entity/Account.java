package com.VirtualBankingSystem.AccountService.Entity;

import com.VirtualBankingSystem.AccountService.Constants.AccountStatus;
import com.VirtualBankingSystem.AccountService.Constants.AccountTableConstants;
import com.VirtualBankingSystem.AccountService.Constants.AccountType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = AccountTableConstants.TABLE_NAME)
public class Account {
    @Id
    @Column(name = AccountTableConstants.COLUMN_ID, nullable = false, updatable = false)
    @GeneratedValue(generator = "UUID")
    private UUID id;

    @Column(name = AccountTableConstants.COLUMN_USER_ID, nullable = false)
    private UUID userId;

    @Column(name = AccountTableConstants.COLUMN_ACCOUNT_NUMBER, nullable = false, unique = true, length = 20)
    private String accountNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = AccountTableConstants.COLUMN_ACCOUNT_TYPE, nullable = false)
    private AccountType accountType;

    @Column(name = AccountTableConstants.COLUMN_BALANCE, nullable = false, precision = 15, scale = 2)
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = AccountTableConstants.COLUMN_STATUS, nullable = false)
    private AccountStatus status;

    @Column(name = AccountTableConstants.COLUMN_CREATED_AT, nullable = false, updatable = false)
    private Timestamp createdAt;

    @Column(name = AccountTableConstants.COLUMN_UPDATED_AT, nullable = false)
    private Timestamp updatedAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }
}
