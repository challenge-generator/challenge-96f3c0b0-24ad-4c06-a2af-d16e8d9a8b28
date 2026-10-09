package com.bank.transactions.application;

import com.bank.transactions.domain.exception.InsufficientFundsException;
import com.bank.transactions.domain.exception.InvalidAmountException;
import com.bank.transactions.domain.exception.TransactionAlreadyProcessedException;
import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.in.TransactionServicePort;
import com.bank.transactions.domain.ports.out.IdempotencyKeyPort;
import com.bank.transactions.domain.ports.out.TransactionPersistencePort;
import com.bank.transactions.infrastructure.adapters.dto.TransactionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class TransactionService implements TransactionServicePort {

    private final TransactionPersistencePort persistencePort;
    private final IdempotencyKeyPort idempotencyPort;

    @Override
    public Mono<Transaction> processTransaction(Transaction transaction) {
        log.info("Iniciando procesamiento de transacción con idempotencyKey: {}", 
                transaction.idempotencyKey());

        return validateTransaction(transaction)
                .flatMap(this::checkIdempotency)
                .flatMap(this::executeTransaction)
                .doOnSuccess(result -> log.info("Transacción procesada exitosamente: {}", result.id()))
                .doOnError(error -> log.error("Error al procesar transacción: {}", error.getMessage()));
    }

    private Mono<Transaction> validateTransaction(Transaction transaction) {
        if (transaction.amount() == null || transaction.amount().compareTo(BigDecimal.ZERO) <= 0) {
            return Mono.error(new InvalidAmountException("El monto debe ser mayor que cero"));
        }

        if (transaction.type() == TransactionType.WITHDRAWAL || 
            transaction.type() == TransactionType.TRANSFER) {
            return validateSufficientFunds(transaction)
                    .thenReturn(transaction);
        }

        return Mono.just(transaction);
    }

    private Mono<Void> validateSufficientFunds(Transaction transaction) {
        return persistencePort.findByAccountId(transaction.accountId())
                .filter(t -> t.status() == TransactionStatus.COMPLETED)
                .collectList()
                .flatMap(transactions -> {
                    BigDecimal totalSpent = transactions.stream()
                            .map(Transaction::amount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add);

                    BigDecimal availableBalance = getAccountBalance(transaction.accountId())
                            .subtract(totalSpent);

                    if (availableBalance.compareTo(transaction.amount()) < 0) {
                        return Mono.error(new InsufficientFundsException(
                                "Saldo insuficiente. Disponible: " + availableBalance));
                    }
                    return Mono.empty();
                });
    }

    private BigDecimal getAccountBalance(UUID accountId) {
        return new BigDecimal("10000.00");
    }

    private Mono<Transaction> checkIdempotency(Transaction transaction) {
        String key = transaction.idempotencyKey();
        if (key == null || key.isBlank()) {
            String generatedKey = UUID.randomUUID().toString() + "-" + 
                    transaction.accountId().toString().hashCode();
            return persistencePort.saveTransaction(
                    new Transaction(
                        transaction.id() != null ? transaction.id() : UUID.randomUUID(),
                        generatedKey,
                        transaction.accountId(),
                        transaction.amount(),
                        transaction.type(),
                        transaction.channel(),
                        TransactionStatus.PENDING,
                        transaction.description(),
                        LocalDateTime.now(),
                        LocalDateTime.now()
                    )
            );
        }

        return persistencePort.existsByIdempotencyKey(key)
                .flatMap(exists -> {
                    if (Boolean.TRUE.equals(exists)) {
                        return persistencePort.findByIdempotencyKey(key)
                                .flatMap(existing -> {
                                    log.info("Transacción duplicada detectada, retornando existente: {}", existing.id());
                                    return Mono.error(new TransactionAlreadyProcessedException(
                                            "Transacción ya procesada con clave: " + key));
                                });
                    }

                    return persistencePort.saveTransaction(
                            new Transaction(
                                transaction.id() != null ? transaction.id() : UUID.randomUUID(),
                                key,
                                transaction.accountId(),
                                transaction.amount(),
                                transaction.type(),
                                transaction.channel(),
                                TransactionStatus.PENDING,
                                transaction.description(),
                                LocalDateTime.now(),
                                LocalDateTime.now()
                            )
                    );
                });
    }

    private Mono<Transaction> executeTransaction(Transaction transaction) {
        return persistencePort.saveTransaction(
                transaction.withStatus(TransactionStatus.COMPLETED)
        );
    }

    @Override
    public Flux<Transaction> getAllTransactions() {
        log.info("Obteniendo todas las transacciones");
        return persistencePort.findAll()
                .doOnNext(t -> log.debug("Transacción encontrada: {}", t.id()));
    }

    @Override
    public Mono<Transaction> getTransactionById(UUID id) {
        log.info("Obteniendo transacción por ID: {}", id);
        return persistencePort.findById(id)
                .switchIfEmpty(Mono.defer(() -> {
                    log.warn("Transacción no encontrada: {}", id);
                    return Mono.empty();
                }));
    }

    @Override
    public Flux<Transaction> getTransactionsByAccountId(UUID accountId) {
        log.info("Obteniendo transacciones para cuenta: {}", accountId);
        return persistencePort.findByAccountId(accountId);
    }

    @Override
    public Mono<Void> cancelTransaction(UUID id) {
        log.info("Cancelando transacción: {}", id);
        return persistencePort.findById(id)
                .flatMap(transaction -> {
                    if (transaction.status() == TransactionStatus.COMPLETED) {
                        return Mono.error(new IllegalStateException(
                                "No se puede cancelar una transacción completada"));
                    }
                    return persistencePort.saveTransaction(
                            transaction.withStatus(TransactionStatus.CANCELLED)
                    );
                })
                .then();
    }

    public Mono<TransactionResponse> toResponse(Transaction transaction) {
        return Mono.just(TransactionResponse.fromDomain(transaction));
    }

    public Flux<TransactionResponse> toResponseFlux(Flux<Transaction> transactions) {
        return transactions.map(TransactionResponse::fromDomain);
    }
}