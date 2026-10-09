package com.bank.transactions.infrastructure.config;


import com.bank.transactions.domain.model.Transaction;
import com.bank.transactions.domain.exception.InsufficientFundsException;
import com.bank.transactions.domain.exception.InvalidAmountException;
import com.bank.transactions.domain.exception.TransactionAlreadyProcessedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    private static final String PROBLEM_JSON_CONTENT_TYPE = "application/problem+json";

    @ExceptionHandler(InvalidAmountException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleInvalidAmount(InvalidAmountException ex) {
        log.warn("Invalid amount exception: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Invalid Amount",
            "https://api.bank.com/errors/invalid-amount",
            ex.getMessage(),
            "The transaction amount must be positive and within valid limits."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(InsufficientFundsException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleInsufficientFunds(InsufficientFundsException ex) {
        log.warn("Insufficient funds exception: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.UNPROCESSABLE_ENTITY,
            "Insufficient Funds",
            "https://api.bank.com/errors/insufficient-funds",
            ex.getMessage(),
            "The account does not have sufficient funds to complete this transaction."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.UNPROCESSABLE_ENTITY)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(TransactionAlreadyProcessedException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleTransactionAlreadyProcessed(
            TransactionAlreadyProcessedException ex) {
        log.warn("Transaction already processed: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.CONFLICT,
            "Transaction Already Processed",
            "https://api.bank.com/errors/duplicate-transaction",
            ex.getMessage(),
            "A transaction with the same idempotency key has already been processed."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.CONFLICT)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleIllegalArgument(IllegalArgumentException ex) {
        log.warn("Illegal argument: {}", ex.getMessage());
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.BAD_REQUEST,
            "Bad Request",
            "https://api.bank.com/errors/bad-request",
            ex.getMessage(),
            "The request contains invalid parameters."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<Map<String, Object>>> handleGenericException(Exception ex) {
        log.error("Unexpected error occurred", ex);
        
        Map<String, Object> problem = buildProblemDetail(
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Internal Server Error",
            "https://api.bank.com/errors/internal-error",
            "An unexpected error occurred. Please try again later.",
            "The server encountered an unexpected condition that prevented it from fulfilling the request."
        );
        
        return Mono.just(ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .header("Content-Type", PROBLEM_JSON_CONTENT_TYPE)
            .body(problem));
    }

    private Map<String, Object> buildProblemDetail(
            HttpStatus status,
            String title,
            String type,
            String detail,
            String instance) {
        return Map.of(
            "type", type,
            "title", title,
            "status", status.value(),
            "detail", detail,
            "instance", instance,
            "timestamp", Instant.now().toString()
        );
    }
}