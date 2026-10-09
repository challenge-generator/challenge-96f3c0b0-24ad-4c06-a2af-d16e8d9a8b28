package com.bank.transactions.infrastructure.adapters.dto;




import com.bank.transactions.domain.model.TransactionStatus;
import com.bank.transactions.domain.model.Channel;
import com.bank.transactions.domain.model.TransactionType;
import com.bank.transactions.domain.model.Transaction;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record TransactionResponse(
    UUID id,
    UUID accountId,
    BigDecimal amount,
    Transaction.TransactionType type,
    Transaction.Channel channel,
    Transaction.TransactionStatus status,
    Instant createdAt,
    String idempotencyKey
) {
    public static TransactionResponse fromDomain(Transaction transaction) {
        return new TransactionResponse(
            transaction.id(),
            transaction.accountId(),
            transaction.amount(),
            transaction.type(),
            transaction.channel(),
            transaction.status(),
            transaction.createdAt(),
            transaction.idempotencyKey()
        );
    }

    public boolean isPending() {
        return status == Transaction.TransactionStatus.PENDING;
    }

    public boolean isCompleted() {
        return status == Transaction.TransactionStatus.COMPLETED;
    }

    public boolean isFailed() {
        return status == Transaction.TransactionStatus.FAILED;
    }

    public boolean isCancelled() {
        return status == Transaction.TransactionStatus.CANCELLED;
    }

    public boolean isSuccessful() {
        return isCompleted();
    }

    public String getStatusDescription() {
        return switch (status) {
            case PENDING -> "Transaccion pendiente de procesamiento";
            case COMPLETED -> "Transaccion completada exitosamente";
            case FAILED -> "Transaccion fallida";
            case CANCELLED -> "Transaccion cancelada";
        };
    }

    public String getTypeDescription() {
        return switch (type) {
            case WITHDRAWAL -> "Retiro de fondos";
            case DEPOSIT -> "Deposito de fondos";
            case TRANSFER -> "Transferencia entre cuentas";
            case BALANCE_INQUIRY -> "Consulta de saldo";
        };
    }

    public String getChannelDescription() {
        return switch (channel) {
            case MOBILE -> "Aplicacion movil";
            case WEB -> "Portal web";
            case ATM -> "Cajero automatico";
            case BRANCH -> "Sucursal fisica";
            case API -> "API externa";
        };
    }

    public String formatAmount() {
        return String.format("$%,.2f", amount);
    }

    public String formatCreatedAt() {
        return createdAt != null ? createdAt.toString() : "N/A";
    }
}