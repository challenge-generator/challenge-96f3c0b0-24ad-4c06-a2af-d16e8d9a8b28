package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration;
import io.r2dbc.postgresql.PostgresqlConnectionFactory;
import io.r2dbc.postgresql.codec.Json;
import io.r2dbc.spi.ConnectionFactory;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@DisplayName("TransactionPersistenceAdapterTest - Pruebas de integración con PostgreSQL reactivo")
class TransactionPersistenceAdapterTest {

    @Container
    private static final PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:16-alpine")
        .withDatabaseName("transactions_db")
        .withUsername("test")
        .withPassword("test");

    private TransactionPersistenceAdapter adapter;

    @BeforeEach
    void setUp() {
        PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
            .host(postgresContainer.getHost())
            .port(postgresContainer.getMappedPort(5432))
            .database("transactions_db")
            .username("test")
            .password("test")
            .build();

        ConnectionFactory connectionFactory = new PostgresqlConnectionFactory(config);
        adapter = new TransactionPersistenceAdapter(connectionFactory);

        createSchema();
    }

    private void createSchema() {
        String schema = """
            CREATE TABLE IF NOT EXISTS transactions (
                id UUID PRIMARY KEY,
                account_id UUID NOT NULL,
                amount DECIMAL(19,4) NOT NULL,
                type VARCHAR(50) NOT NULL,
                channel VARCHAR(50) NOT NULL,
                status VARCHAR(50) NOT NULL,
                idempotency_key VARCHAR(255) UNIQUE NOT NULL,
                description TEXT,
                created_at TIMESTAMP WITH TIME ZONE NOT NULL
            );
            CREATE INDEX IF NOT EXISTS idx_transactions_account_id ON transactions(account_id);
            CREATE INDEX IF NOT EXISTS idx_transactions_idempotency_key ON transactions(idempotency_key);
            """;

        Mono.from(connectionFactory().create())
            .flatMap(conn -> Mono.from(conn.createStatement(schema).execute()))
            .block();
    }

    private ConnectionFactory connectionFactory() {
        PostgresqlConnectionConfiguration config = PostgresqlConnectionConfiguration.builder()
            .host(postgresContainer.getHost())
            .port(postgresContainer.getMappedPort(5432))
            .database("transactions_db")
            .username("test")
            .password("test")
            .build();
        return new PostgresqlConnectionFactory(config);
    }

    @Nested
    @DisplayName("Save Transaction - Persistencia de transacciones")
    class SaveTransactionTests {

        @Test
        @DisplayName("Debe guardar una transacción exitosamente")
        void shouldSaveTransaction() {
            Transaction transaction = createTestTransaction(TransactionType.DEPOSIT, new BigDecimal("500.00"));

            StepVerifier.create(adapter.saveTransaction(transaction))
                .assertNext(saved -> {
                    assertThat(saved.id()).isEqualTo(transaction.id());
                    assertThat(saved.accountId()).isEqualTo(transaction.accountId());
                    assertThat(saved.amount()).isEqualByComparingTo(transaction.amount());
                    assertThat(saved.status()).isEqualTo(TransactionStatus.PENDING);
                })
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe actualizar el estado de una transacción existente")
        void shouldUpdateTransactionStatus() {
            Transaction transaction = createTestTransaction(TransactionType.WITHDRAWAL, new BigDecimal("100.00"));

            StepVerifier.create(adapter.saveTransaction(transaction)
                    .flatMap(saved -> adapter.saveTransaction(saved.withStatus(TransactionStatus.COMPLETED))))
                .assertNext(updated -> {
                    assertThat(updated.status()).isEqualTo(TransactionStatus.COMPLETED);
                })
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Find Transactions - Consulta de transacciones persistidas")
    class FindTransactionTests {

        @Test
        @DisplayName("Debe encontrar transacción por ID")
        void shouldFindById() {
            Transaction transaction = createTestTransaction(TransactionType.TRANSFER, new BigDecimal("250.00"));

            adapter.saveTransaction(transaction)
                .flatMap(saved -> adapter.findById(saved.id()))
                .as(StepVerifier::create)
                .assertNext(found -> assertThat(found.id()).isEqualTo(transaction.id()))
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar vacío para ID inexistente")
        void shouldReturnEmptyForNonExistentId() {
            UUID randomId = UUID.randomUUID();

            adapter.findById(randomId)
                .as(StepVerifier::create)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe encontrar transacciones por accountId")
        void shouldFindByAccountId() {
            UUID accountId = UUID.randomUUID();
            Transaction t1 = createTestTransactionWithAccountId(accountId, TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTestTransactionWithAccountId(accountId, TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            adapter.saveTransaction(t1)
                .then(adapter.saveTransaction(t2))
                .thenMany(adapter.findByAccountId(accountId))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar todas las transacciones")
        void shouldFindAll() {
            Transaction t1 = createTestTransaction(TransactionType.DEPOSIT, new BigDecimal("100.00"));
            Transaction t2 = createTestTransaction(TransactionType.WITHDRAWAL, new BigDecimal("50.00"));

            adapter.saveTransaction(t1)
                .then(adapter.saveTransaction(t2))
                .thenMany(adapter.findAll())
                .as(StepVerifier::create)
                .expectNextCount(2)
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Idempotency - Verificación de clave de idempotencia")
    class IdempotencyTests {

        @Test
        @DisplayName("Debe verificar existencia de clave de idempotencia")
        void shouldCheckIdempotencyKeyExists() {
            Transaction transaction = createTestTransaction(TransactionType.DEPOSIT, new BigDecimal("200.00"));

            adapter.saveTransaction(transaction)
                .then(adapter.existsByIdempotencyKey(transaction.idempotencyKey()))
                .as(StepVerifier::create)
                .expectNext(true)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe retornar false para clave de idempotencia inexistente")
        void shouldReturnFalseForNonExistentKey() {
            adapter.existsByIdempotencyKey("non-existent-key")
                .as(StepVerifier::create)
                .expectNext(false)
                .verifyComplete();
        }

        @Test
        @DisplayName("Debe encontrar transacción por clave de idempotencia")
        void shouldFindByIdempotencyKey() {
            Transaction transaction = createTestTransaction(TransactionType.TRANSFER, new BigDecimal("300.00"));

            adapter.saveTransaction(transaction)
                .flatMap(saved -> adapter.findByIdempotencyKey(saved.idempotencyKey()))
                .as(StepVerifier::create)
                .assertNext(found -> assertThat(found.idempotencyKey()).isEqualTo(transaction.idempotencyKey()))
                .verifyComplete();
        }
    }

    @Nested
    @DisplayName("Delete Transaction - Eliminación de transacciones")
    class DeleteTransactionTests {

        @Test
        @Debe eliminar transacción por ID")
        void shouldDeleteTransaction() {
            Transaction transaction = createTestTransaction(TransactionType.WITHDRAWAL, new BigDecimal("75.00"));

            adapter.saveTransaction(transaction)
                .flatMap(saved -> adapter.deleteById(saved.id()).thenReturn(saved))
                .flatMap(saved -> adapter.findById(saved.id()))
                .as(StepVerifier::create)
                .verifyComplete();
        }
    }

    private Transaction createTestTransaction(TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            UUID.randomUUID(),
            amount,
            type,
            Channel.MOBILE_APP,
            TransactionStatus.PENDING,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Integration test transaction"
        );
    }

    private Transaction createTestTransactionWithAccountId(UUID accountId, TransactionType type, BigDecimal amount) {
        return new Transaction(
            UUID.randomUUID(),
            accountId,
            amount,
            type,
            Channel.WEB,
            TransactionStatus.COMPLETED,
            UUID.randomUUID().toString(),
            Instant.now(),
            "Test transaction for account " + accountId
        );
    }
}