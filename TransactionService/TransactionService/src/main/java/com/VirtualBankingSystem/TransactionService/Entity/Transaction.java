package com.VirtualBankingSystem.TransactionService.Entity;

import com.VirtualBankingSystem.TransactionService.Constants.TransactionStatusEnum;
import com.VirtualBankingSystem.TransactionService.Constants.TransactionTableConstants;
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
@Table(name = TransactionTableConstants.TABLE_NAME)
public class Transaction {
    @Id
    @GeneratedValue(generator = "UUID")
    @Column(name = TransactionTableConstants.ID_COLUMN, nullable = false, unique = true, updatable = false)
    private UUID id;

    @Column(name = TransactionTableConstants.FROM_ACCOUNT_ID_COLUMN, nullable = false, updatable = false)
    private UUID fromAccountId;

    @Column(name = TransactionTableConstants.TO_ACCOUNT_ID_COLUMN, nullable = false, updatable = false)
    private UUID toAccountId;

    @Column(name = TransactionTableConstants.AMOUNT_COLUMN, nullable = false, precision = 15, scale = 2, updatable = false)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = TransactionTableConstants.STATUS_COLUMN, nullable = false)
    private TransactionStatusEnum status;

    @Column(name = TransactionTableConstants.DESCRIPTION_COLUMN)
    private String description;

    @Column(name = TransactionTableConstants.TIMESTAMP_COLUMN, nullable = false, updatable = false)
    private Timestamp timestamp;

    @PrePersist
    private void onCreate() {
        if (this.timestamp == null) {
            this.timestamp = new Timestamp(System.currentTimeMillis());
        }
    }
}
