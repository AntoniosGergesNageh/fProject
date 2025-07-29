package com.VirtualBankingSystem.TransactionService.Constants;

public class TransactionTableConstants {
    public static final String TABLE_NAME = "transactions";
    public static final String ID_COLUMN = "id";
    public static final String FROM_ACCOUNT_ID_COLUMN = "from_account_id";
    public static final String TO_ACCOUNT_ID_COLUMN = "to_account_id";
    public static final String AMOUNT_COLUMN = "amount";
    public static final String STATUS_COLUMN = "status";
    public static final String DESCRIPTION_COLUMN = "description";
    public static final String TIMESTAMP_COLUMN = "timestamp";

    private TransactionTableConstants() {
    }
}
