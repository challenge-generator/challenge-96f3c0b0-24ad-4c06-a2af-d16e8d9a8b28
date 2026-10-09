package com.bank.transactions.domain.exception;

import java.util.UUID;

public class TransactionAlreadyProcessedException extends RuntimeException {

    private final String idempotencyKey;
    private final UUID transactionId;

    public TransactionAlreadyProcessedException(String idempotencyKey) {
        super("Transaccion ya procesada con clave de idempotencia: " + idempotencyKey);
        this.idempotencyKey = idempotencyKey;
        this.transactionId = null;
    }

    public TransactionAlreadyProcessedException(String idempotencyKey, UUID transactionId) {
        super("Transaccion ya procesada con clave de idempotencia: " + idempotencyKey + ", ID de transaccion: " + transactionId);
        this.idempotencyKey = idempotencyKey;
        this.transactionId = transactionId;
    }

    public TransactionAlreadyProcessedException(String message, Throwable cause, String idempotencyKey, UUID transactionId) {
        super(message, cause);
        this.idempotencyKey = idempotencyKey;
        this.transactionId = transactionId;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public UUID getTransactionId() {
        return transactionId;
    }
}