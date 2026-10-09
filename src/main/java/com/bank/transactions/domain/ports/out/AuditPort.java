package com.bank.transactions.domain.ports.out;

import com.bank.transactions.domain.model.Transaction;
import reactor.core.publisher.Mono;

public interface AuditPort {
    Mono<Void> recordTransactionAccepted(Transaction transaction);
    Mono<Void> recordTransactionRejected(Transaction transaction, String reason);
}