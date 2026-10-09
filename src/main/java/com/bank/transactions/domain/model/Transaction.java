package com.bank.transactions.domain.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record Transaction(
    @NotNull
    UUID id,

    @NotNull
    @Size(min = 36, max = 36)
    String idempotencyKey,

    @NotNull
    TransactionType type,

    @NotNull
    @Positive
    BigDecimal amount,

    @NotNull
    Channel channel,

    @NotNull
    LocalDateTime timestamp,

    @NotNull
    TransactionStatus status
) {
    public enum TransactionType {
        DEPOSIT,
        WITHDRAWAL,
        TRANSFER,
        PAYMENT
    }

    public enum Channel {
        MOBILE_APP,
        WEB,
        ATM,
        BRANCH
    }

    public enum TransactionStatus {
        PENDING,
        COMPLETED,
        FAILED,
        REVERSED
    }

    public Transaction withStatus(TransactionStatus newStatus) {
        return new Transaction(
            this.id,
            this.idempotencyKey,
            this.type,
            this.amount,
            this.channel,
            this.timestamp,
            newStatus
        );
    }
}