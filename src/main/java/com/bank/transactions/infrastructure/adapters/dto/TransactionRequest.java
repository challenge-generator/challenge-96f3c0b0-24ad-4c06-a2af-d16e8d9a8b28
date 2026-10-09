package com.bank.transactions.infrastructure.adapters.dto;




import com.bank.transactions.domain.model.TransactionStatus;
import com.bank.transactions.domain.model.Channel;
import com.bank.transactions.domain.model.TransactionType;
import com.bank.transactions.domain.model.Transaction;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.UUID;

public record TransactionRequest(
    @NotNull(message = "El ID de cuenta es obligatorio")
    UUID accountId,

    @NotNull(message = "El monto es obligatorio")
    @Positive(message = "El monto debe ser positivo")
    BigDecimal amount,

    @NotNull(message = "El tipo de transaccion es obligatorio")
    Transaction.TransactionType type,

    @NotNull(message = "El canal de origen es obligatorio")
    Transaction.Channel channel,

    @NotBlank(message = "La clave de idempotencia es obligatoria")
    String idempotencyKey
) {
    public Transaction toDomain() {
        return new Transaction(
            UUID.randomUUID(),
            accountId,
            amount,
            type,
            channel,
            Transaction.TransactionStatus.PENDING,
            java.time.Instant.now(),
            idempotencyKey,
            null
        );
    }

    public Transaction toDomainWithId(UUID transactionId) {
        return new Transaction(
            transactionId,
            accountId,
            amount,
            type,
            channel,
            Transaction.TransactionStatus.PENDING,
            java.time.Instant.now(),
            idempotencyKey,
            null
        );
    }

    public void validate() {
        if (accountId == null) {
            throw new IllegalArgumentException("El ID de cuenta no puede ser null");
        }
        if (amount == null) {
            throw new IllegalArgumentException("El monto no puede ser null");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero");
        }
        if (type == null) {
            throw new IllegalArgumentException("El tipo de transaccion no puede ser null");
        }
        if (channel == null) {
            throw new IllegalArgumentException("El canal de origen no puede ser null");
        }
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("La clave de idempotencia no puede estar vacia");
        }
    }

    public boolean hasValidAccountId() {
        return accountId != null;
    }

    public boolean hasValidAmount() {
        return amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean hasValidType() {
        return type != null;
    }

    public boolean hasValidChannel() {
        return channel != null;
    }

    public boolean hasValidIdempotencyKey() {
        return idempotencyKey != null && !idempotencyKey.isBlank();
    }

    public boolean isComplete() {
        return hasValidAccountId() && hasValidAmount() && hasValidType() 
            && hasValidChannel() && hasValidIdempotencyKey();
    }
}