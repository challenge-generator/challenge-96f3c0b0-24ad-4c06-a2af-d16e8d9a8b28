package com.bank.transactions.domain.ports.out;

import com.bank.transactions.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionPersistencePort {
    Mono<Transaction> saveTransaction(Transaction transaction);
    
    Mono<Transaction> findById(UUID id);
    
    Flux<Transaction> findAll();
    
    Flux<Transaction> findByAccountId(UUID accountId);
    
    Mono<Void> deleteById(UUID id);
    
    Mono<Boolean> existsByIdempotencyKey(String idempotencyKey);
    
    Mono<Transaction> findByIdempotencyKey(String idempotencyKey);
}