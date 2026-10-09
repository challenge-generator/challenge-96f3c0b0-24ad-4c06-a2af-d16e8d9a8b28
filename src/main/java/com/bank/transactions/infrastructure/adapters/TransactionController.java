package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.model.Transaction.Channel;
import com.bank.transactions.domain.model.Transaction.TransactionStatus;
import com.bank.transactions.domain.model.Transaction.TransactionType;
import com.bank.transactions.domain.ports.in.TransactionServicePort;
import com.bank.transactions.infrastructure.adapters.dto.TransactionRequest;
import com.bank.transactions.infrastructure.adapters.dto.TransactionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Component
@RequestMapping("/api/v1/transactions")
public class TransactionController {

    private final TransactionServicePort transactionService;

    public TransactionController(TransactionServicePort transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> createTransaction(
            @RequestBody Mono<TransactionRequest> requestMono,
            @RequestHeader(value = "X-Channel", defaultValue = "WEB") String channelHeader) {

        Channel channel = parseChannel(channelHeader);

        return requestMono
                .flatMap(request -> {
                    Transaction transaction = mapToTransaction(request, channel);
                    return transactionService.processTransaction(transaction);
                })
                .flatMap(transaction -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(mapToResponse(transaction)));
    }

    @PostMapping(value = "/batch", consumes = MediaType.APPLICATION_NDJSON_VALUE)
    public Flux<ServerResponse> createTransactionBatch(
            @RequestBody Flux<TransactionRequest> requestFlux,
            @RequestHeader(value = "X-Channel", defaultValue = "WEB") String channelHeader) {

        Channel channel = parseChannel(channelHeader);

        return requestFlux
                .map(request -> mapToTransaction(request, channel))
                .flatMap(transactionService::processTransaction)
                .map(transaction -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(mapToResponse(transaction))
                        .orElse(ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> getAllTransactions() {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionService.getAllTransactions()
                        .map(this::mapToResponse), TransactionResponse.class);
    }

    @GetMapping(value = "/account/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> getTransactionsByAccountId(@PathVariable UUID accountId) {
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(transactionService.getTransactionsByAccountId(accountId)
                        .map(this::mapToResponse), TransactionResponse.class);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ServerResponse> getTransactionById(@PathVariable UUID id) {
        return transactionService.getTransactionById(id)
                .flatMap(transaction -> ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(mapToResponse(transaction)))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ServerResponse> cancelTransaction(@PathVariable UUID id) {
        return transactionService.cancelTransaction(id)
                .then(ServerResponse.noContent().build())
                .onErrorResume(e -> ServerResponse
                        .status(HttpStatus.NOT_FOUND)
                        .build());
    }

    private Channel parseChannel(String channelHeader) {
        try {
            return Channel.valueOf(channelHeader.toUpperCase());
        } catch (IllegalArgumentException e) {
            return Channel.WEB;
        }
    }

    private Transaction mapToTransaction(TransactionRequest request, Channel channel) {
        return new Transaction(
                UUID.randomUUID(),
                request.accountId(),
                request.amount(),
                TransactionType.valueOf(request.type().toUpperCase()),
                channel,
                TransactionStatus.PENDING,
                request.description(),
                Instant.now(),
                request.idempotencyKey()
        );
    }

    private TransactionResponse mapToResponse(Transaction transaction) {
        return new TransactionResponse(
                transaction.id(),
                transaction.accountId(),
                transaction.amount(),
                transaction.type().name(),
                transaction.channel().name(),
                transaction.status().name(),
                transaction.description(),
                transaction.createdAt().toString()
        );
    }
}