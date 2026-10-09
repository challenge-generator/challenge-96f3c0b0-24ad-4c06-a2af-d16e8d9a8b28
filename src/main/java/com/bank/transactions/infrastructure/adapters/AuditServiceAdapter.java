package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.ports.out.AuditPort;
import io.r2dbc.postgresql.codec.Json;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;
import java.util.UUID;

@Repository
public class AuditServiceAdapter implements AuditPort {

    private static final Logger log = LoggerFactory.getLogger(AuditServiceAdapter.class);
    private static final String INSERT_AUDIT_SQL = """
        INSERT INTO audit_log (id, transaction_id, event_type, event_data, created_at)
        VALUES ($1, $2, $3, $4, $5)
        """;

    private final DatabaseClient databaseClient;

    public AuditServiceAdapter(DatabaseClient databaseClient) {
        this.databaseClient = databaseClient;
    }

    @Override
    public Mono<Void> recordTransactionAccepted(Transaction transaction) {
        log.info("Recording accepted transaction in audit log: id={}, idempotencyKey={}",
                transaction.id(), transaction.idempotencyKey());
        
        String eventData = buildEventData(transaction);
        
        return databaseClient.sql(INSERT_AUDIT_SQL)
                .bind(0, UUID.randomUUID().toString())
                .bind(1, transaction.id().toString())
                .bind(2, "TRANSACTION_ACCEPTED")
                .bind(3, eventData)
                .bind(4, Instant.now())
                .fetch()
                .rowsUpdated()
                .doOnSuccess(rows -> log.debug("Audit log recorded for transaction: {}", transaction.id()))
                .doOnError(error -> log.error("Failed to record audit log for transaction: {}", 
                        transaction.id(), error))
                .then();
    }

    @Override
    public Mono<Void> recordTransactionRejected(Transaction transaction, String reason) {
        log.warn("Recording rejected transaction in audit log: id={}, reason={}",
                transaction.id(), reason);
        
        String eventData = buildEventDataWithRejection(transaction, reason);
        
        return databaseClient.sql(INSERT_AUDIT_SQL)
                .bind(0, UUID.randomUUID().toString())
                .bind(1, transaction.id().toString())
                .bind(2, "TRANSACTION_REJECTED")
                .bind(3, eventData)
                .bind(4, Instant.now())
                .fetch()
                .rowsUpdated()
                .doOnSuccess(rows -> log.debug("Audit rejection log recorded for transaction: {}", transaction.id()))
                .doOnError(error -> log.error("Failed to record audit rejection log for transaction: {}", 
                        transaction.id(), error))
                .then();
    }

    private String buildEventData(Transaction transaction) {
        Map<String, Object> data = Map.of(
            "transactionId", transaction.id().toString(),
            "accountId", transaction.accountId().toString(),
            "amount", transaction.amount().toPlainString(),
            "type", transaction.type().name(),
            "channel", transaction.channel().name(),
            "status", transaction.status().name(),
            "idempotencyKey", transaction.idempotencyKey(),
            "createdAt", transaction.createdAt().toString()
        );
        return """{"transactionId":"%s","accountId":"%s","amount":"%s","type":"%s","channel":"%s","status":"%s","idempotencyKey":"%s","createdAt":"%s"}""".formatted(
            transaction.id(),
            transaction.accountId(),
            transaction.amount().toPlainString(),
            transaction.type().name(),
            transaction.channel().name(),
            transaction.status().name(),
            transaction.idempotencyKey(),
            transaction.createdAt()
        );
    }

    private String buildEventDataWithRejection(Transaction transaction, String reason) {
        return """{"transactionId":"%s","accountId":"%s","amount":"%s","type":"%s","channel":"%s","reason":"%s","rejectedAt":"%s"}""".formatted(
            transaction.id(),
            transaction.accountId(),
            transaction.amount().toPlainString(),
            transaction.type().name(),
            transaction.channel().name(),
            reason,
            Instant.now().toString()
        );
    }
}