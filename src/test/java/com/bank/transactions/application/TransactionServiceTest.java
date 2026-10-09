package com.bank.transactions.application;

import com.bank.transactions.domain.exception.InsufficientFundsException;
import com.bank.transactions.domain.exception.InvalidAmountException;
import com.bank.transactions.domain.exception.TransactionAlreadyProcessedException;
import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.out.IdempotencyKeyPort;
import com.bank.transactions.domain.ports.out.TransactionPersistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("TransactionServiceTest - Pruebas unitarias del servicio de transacciones")
class TransactionServiceTest {

    @Mock
    private TransactionPersistencePort persistencePort;

    @Mock
    private IdempotencyKeyPort idempotencyKeyPort;

    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionService = new TransactionService(persistencePort, idempotencyKeyPort);
    }

    @Nested
    @DisplayName("Process Transaction - Procesamiento de transacciones")
    class ProcessTransactionTests {

        @Test
        @DisplayName("Debe procesar una transacción válida exitosamente")
        void shouldProcessValidTransaction() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("100.00"));
            
            when(idempotencyKeyPort.exists(anyString())).thenReturn(Mono.just(false));
            when(persistencePort.saveTransaction(any(Transaction.class))).thenReturn(Mono.just(transaction));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectNextMatches(t -> t.status() == TransactionStatus.COMPLETED)
                .verifyComplete();

            verify(idempotencyKeyPort).exists(anyString());
            verify(persistencePort).saveTransaction(any(Transaction.class));
        }

        @Test
        @DisplayName("Debe rechazar transacción con monto negativo")
        void shouldRejectNegativeAmount() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("-50.00"));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(InvalidAmountException.class)
                .verify();

            verify(persistencePort, never()).saveTransaction(any());
        }

        @Test
        @DisplayName("Debe rechazar transacción con monto cero")
        void shouldRejectZeroAmount() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, BigDecimal.ZERO);

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(InvalidAmountException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe rechazar transacción por fondos insuficientes")
        void shouldRejectInsufficientFunds() {
            Transaction transaction = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("1000.00"));
            transaction = new Transaction(
                transaction.id(), transaction.accountId(), new BigDecimal("50.00"),
                transaction.type(), transaction.channel(), TransactionStatus.PENDING,
                transaction.idempotencyKey(), transaction.createdAt(), transaction.description()
            );

            when(idempotencyKeyPort.exists(anyString())).thenReturn(Mono.just(false));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(InsufficientFundsException.class)
                .verify();
        }

        @Test
        @DisplayName("Debe rechazar transacción duplicada por clave de idempotencia")
        void shouldRejectDuplicateTransaction() {
            Transaction transaction = createTransaction(TransactionType.TRANSFER, new BigDecimal("200.00"));

            when(idempotencyKeyPort.exists(transaction.idempotencyKey())).thenReturn(Mono.just(true));
            when(persistencePort.findByIdempotencyKey(transaction.idempotencyKey()))
                .thenReturn(Mono.just(transaction.withStatus(TransactionStatus.COMPLETED)));

            StepVerifier.create(transactionService.processTransaction(transaction))
                .expectError(TransactionAlreadyProcessedException.class)
                .verify();
        }
    }

    @Nested
    @DisplayName("Get Transactions - Consulta de transacciones")
    class GetTransactionTests {

        @Test
        @DisplayName("Debe obtener todas las transacciones")
        void shouldGetAllTransactions() {
            Transaction t1 = createTransaction(TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTransaction(TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            when(persistencePort.findAll()).thenReturn(Flux.just(t1, t2));

            StepVerifier.create(transactionService.getAllTransactions())
                .expectNextCount(2)
                .verifyComplete();

            verify(persistencePort).findAll();
        }

        @Test
        @DisplayName("Debe obtener transacción por ID")
        void shouldGetTransactionById() {
            UUID id = UUID.randomUUID();
            Transaction transaction = createTransactionWithId(id, TransactionType.DEPOSIT, new BigDecimal("100.00"));

            when(persistencePort.findById(id)).thenReturn(Mono.just(transaction));

            StepVerifier.create(transactionService.getTransactionById(id))
                .expectNextMatches(t -> t.id().equals(id))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe obtener transacciones por accountId")
        void shouldGetTransactionsByAccountId() {
            UUID accountId = UUID.randomUUID();
            Transaction t1 = createTransactionWithAccountId(accountId, TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTransactionWithAccountId(accountId, TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            when(persistencePort.findByAccountId(accountId)).thenReturn(Flux.just(t1, t2));

            StepVerifier.create(transactionService.getTransactionsByAccountId(accountId))
                .expectNextCount(2)
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Cancel Transaction - Cancelación de transacciones")
    class CancelTransactionTests {

        @Test
        @DisplayName("Debe cancelar transacción exitosamente")
        void shouldCancelTransaction() {
            UUID id = UUID.randomUUID();
            Transaction transaction = createTransactionWithId(id, TransactionType.WITHDRAWAL, new BigDecimal("100.00"));

            when(persistencePort.findById(id)).thenReturn(Mono.just(transaction));
            when(persistencePort.saveTransaction(any(Transaction.class)))
                .thenAnswer(inv -> Mono.just(inv.getArgument(0)));

            StepVerifier.create(transactionService.cancelTransaction(id))
                .verifyComplete();

            verify(persistencePort).saveTransaction(argThat(t -> t.status() == TransactionStatus.CANCELLED));
        }

        @Test
        @DisplayName("Debe fallar al cancelar transacción ya completada")
        void shouldFailToCancelCompletedTransaction() {
            UUID id = UUID.randomUUID();
            Transaction completed = createTransactionWithId(id, TransactionType.WITHDRAWAL, new BigDecimal("100.00"));
            completed = new Transaction(
                completed.id(), completed.accountId(), completed.amount(),
                completed.type(), completed.channel(), TransactionStatus.COMPLETED,
                completed.idempotencyKey(), completed.createdAt(), completed.description()
            );

            when(persistencePort.findById(id)).thenReturn(Mono.just(completed));

            StepVerifier.create(transactionService.cancelTransaction(id))
                .expectError(IllegalStateException.class)
                .verify();
        }
    }

    private Transaction createTransaction(TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            amount,
            type,
            Channel.MOBILE_APP,
            TransactionStatus.PENDING,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction"
        );
    }

    private Transaction createTransactionWithId(UUID id, TransactionType type, BigDecimal amount) {
        return new Transaction(
            id,
            UUID.randomUUID(),
            amount,
            type,
            Channel.WEB,
            TransactionStatus.PENDING,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction with ID"
        );
    }

    private Transaction createTransactionWithAccountId(UUID accountId, TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            accountId,
            amount,
            type,
            Channel.ATM,
            TransactionStatus.COMPLETED,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction for account"
        );
    }
}