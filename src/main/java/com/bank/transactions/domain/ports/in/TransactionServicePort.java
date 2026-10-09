package com.bank.transactions.domain.ports.in;

import com.bank.transactions.domain.model.Transaction;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

public interface TransactionServicePort {
    Mono<Transaction> processTransaction(Transaction transaction);
    
    Flux<Transaction> getAllTransactions();
    
    Mono<Transaction> getTransactionById(UUID id);
    
    Flux<Transaction> getTransactionsByAccountId(UUID accountId);
    
    Mono<Void> cancelTransaction(UUID id);
}