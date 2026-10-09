package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.out.TransactionPersistencePort;
import io.r2dbc.postgresql.codec.Json;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class TransactionPersistenceAdapter implements TransactionPersistencePort {

    private final DatabaseClient databaseClient;

    private static final String INSERT_SQL = """
            INSERT INTO transactions 
            (id, idempotency_key, account_id, amount, type, channel, status, 
             description, created_at, updated_at)
            VALUES (:$1, $2, $3, $4, $5, $6, $7, $8, $9, $10)
            """;

    private static final String SELECT_BY_ID = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions WHERE id = :id
            """;

    private static final String SELECT_ALL = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions ORDER BY created_at DESC
            """;

    private static final String SELECT_BY_ACCOUNT = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions WHERE account_id = :accountId ORDER BY created_at DESC
            """;

    private static final String EXISTS_BY_KEY = """
            SELECT COUNT(*) > 0 FROM transactions WHERE idempotency_key = :key
            """;

    private static final String SELECT_BY_IDEMPOTENCY_KEY = """
            SELECT id, idempotency_key, account_id, amount, type, channel, status, 
                   description, created_at, updated_at
            FROM transactions WHERE idempotency_key = :key
            """;

    private static final String DELETE_BY_ID = """
            DELETE FROM transactions WHERE id = :id
            """;

    @Override
    public Mono<Transaction> saveTransaction(Transaction transaction) {
        log.debug("Persistiendo transacción: {}", transaction.id());

        return databaseClient.sql(INSERT_SQL)
                .bind("$1", transaction.id().toString())
                .bind("$2", transaction.idempotencyKey())
                .bind("$3", transaction.accountId().toString())
                .bind("$4", transaction.amount().toString())
                .bind("$5", transaction.type().name())
                .bind("$6", transaction.channel().name())
                .bind("$7", transaction.status().name())
                .bind("$8", transaction.description() != null ? transaction.description() : "")
                .bind("$9", transaction.createdAt())
                .bind("$10", transaction.updatedAt())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    log.info("Transacción persistida exitosamente: {}", transaction.id());
                    return Mono.just(transaction);
                })
                .onErrorResume(e -> {
                    log.error("Error al persistir transacción: {}", e.getMessage());
                    return Mono.error(e);
                });
    }

    @Override
    public Mono<Transaction> findById(UUID id) {
        log.debug("Buscando transacción por ID: {}", id);

        return databaseClient.sql(SELECT_BY_ID)
                .bind("id", id.toString())
                .map((row, metadata) -> mapRowToTransaction(row))
                .first()
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("No se encontró transacción con ID: {}", id);
                    return Mono.empty();
                }));
    }

    @Override
    public Flux<Transaction> findAll() {
        log.debug("Obteniendo todas las transacciones");

        return databaseClient.sql(SELECT_ALL)
                .map((row, metadata) -> mapRowToTransaction(row))
                .all()
                .doOnComplete(() -> log.debug("Todas las transacciones recuperadas"))
                .doOnError(e -> log.error("Error al obtener transacciones: {}", e.getMessage()));
    }

    @Override
    public Flux<Transaction> findByAccountId(UUID accountId) {
        log.debug("Buscando transacciones para cuenta: {}", accountId);

        return databaseClient.sql(SELECT_BY_ACCOUNT)
                .bind("accountId", accountId.toString())
                .map((row, metadata) -> mapRowToTransaction(row))
                .all()
                .doOnComplete(() -> log.debug("Transacciones de cuenta recuperadas: {}", accountId))
                .doOnError(e -> log.error("Error al buscar transacciones por cuenta: {}", e.getMessage()));
    }

    @Override
    public Mono<Void> deleteById(UUID id) {
        log.debug("Eliminando transacción: {}", id);

        return databaseClient.sql(DELETE_BY_ID)
                .bind("id", id.toString())
                .fetch()
                .rowsUpdated()
                .flatMap(rows -> {
                    log.info("Transacción eliminada: {}", id);
                    return Mono.empty();
                });
    }

    @Override
    public Mono<Boolean> existsByIdempotencyKey(String idempotencyKey) {
        log.debug("Verificando existencia de clave de idempotencia: {}", idempotencyKey);

        return databaseClient.sql(EXISTS_BY_KEY)
                .bind("key", idempotencyKey)
                .map((row, metadata) -> row.get("count", Boolean.class))
                .first()
                .defaultIfEmpty(false)
                .onErrorResume(e -> {
                    log.error("Error al verificar idempotencia: {}", e.getMessage());
                    return Mono.just(false);
                });
    }

    @Override
    public Mono<Transaction> findByIdempotencyKey(String idempotencyKey) {
        log.debug("Buscando transacción por clave de idempotencia: {}", idempotencyKey);

        return databaseClient.sql(SELECT_BY_IDEMPOTENCY_KEY)
                .bind("key", idempotencyKey)
                .map((row, metadata) -> mapRowToTransaction(row))
                .first()
                .switchIfEmpty(Mono.defer(() -> {
                    log.debug("No se encontró transacción con clave de idempotencia: {}", idempotencyKey);
                    return Mono.empty();
                }));
    }

    private Transaction mapRowToTransaction(org.springframework.r2dbc.core.Row row) {
        String idStr = row.get("id", String.class);
        String accountIdStr = row.get("account_id", String.class);
        String amountStr = row.get("amount", String.class);
        String typeStr = row.get("type", String.class);
        String channelStr = row.get("channel", String.class);
        String statusStr = row.get("status", String.class);
        String description = row.get("description", String.class);
        LocalDateTime createdAt = row.get("created_at", LocalDateTime.class);
        LocalDateTime updatedAt = row.get("updated_at", LocalDateTime.class);

        return new Transaction(
                UUID.fromString(idStr),
                row.get("idempotency_key", String.class),
                UUID.fromString(accountIdStr),
                new BigDecimal(amountStr),
                TransactionType.valueOf(typeStr),
                Channel.valueOf(channelStr),
                TransactionStatus.valueOf(statusStr),
                description,
                createdAt,
                updatedAt
        );
    }
}