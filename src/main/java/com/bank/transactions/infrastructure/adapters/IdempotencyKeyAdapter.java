package com.bank.transactions.infrastructure.adapters;

import com.bank.transactions.domain.ports.out.IdempotencyKeyPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ReactiveStringRedisTemplate;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@Slf4j
@Repository
@RequiredArgsConstructor
public class IdempotencyKeyAdapter implements IdempotencyKeyPort {

    private final ReactiveStringRedisTemplate redisTemplate;

    private static final String KEY_PREFIX = "idempotency:";
    private static final Duration DEFAULT_TTL = Duration.ofHours(24);

    @Override
    public Mono<Boolean> exists(String idempotencyKey) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.debug("Verificando existencia de clave de idempotencia en Redis: {}", fullKey);

        return redisTemplate.hasKey(fullKey)
                .doOnSuccess(exists -> log.debug("Clave {} existe: {}", fullKey, exists))
                .doOnError(e -> log.error("Error al verificar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<Boolean> store(String idempotencyKey, String transactionId) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.info("Almacenando clave de idempotencia en Redis: {} -> {}", fullKey, transactionId);

        return redisTemplate.opsForValue()
                .set(fullKey, transactionId, DEFAULT_TTL)
                .doOnSuccess(stored -> {
                    if (Boolean.TRUE.equals(stored)) {
                        log.info("Clave de idempotencia almacenada exitosamente: {}", fullKey);
                    } else {
                        log.warn("No se pudo almacenar la clave de idempotencia: {}", fullKey);
                    }
                })
                .doOnError(e -> log.error("Error al almacenar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<String> retrieve(String idempotencyKey) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.debug("Recuperando clave de idempotencia de Redis: {}", fullKey);

        return redisTemplate.opsForValue()
                .get(fullKey)
                .doOnSuccess(value -> {
                    if (value != null) {
                        log.debug("Clave de idempotencia recuperada: {} -> {}", fullKey, value);
                    } else {
                        log.debug("No se encontró valor para clave de idempotencia: {}", fullKey);
                    }
                })
                .doOnError(e -> log.error("Error al recuperar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<Boolean> delete(String idempotencyKey) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.info("Eliminando clave de idempotencia de Redis: {}", fullKey);

        return redisTemplate.delete(fullKey)
                .map(count -> count > 0)
                .doOnSuccess(deleted -> log.info("Clave de idempotencia eliminada: {} -> {}", fullKey, deleted))
                .doOnError(e -> log.error("Error al eliminar clave de idempotencia: {}", e.getMessage()));
    }

    @Override
    public Mono<Boolean> extendTTL(String idempotencyKey, Duration ttl) {
        String fullKey = KEY_PREFIX + idempotencyKey;
        log.debug("Extendiendo TTL de clave de idempotencia: {} por {}", fullKey, ttl);

        return redisTemplate.expire(fullKey, ttl)
                .doOnSuccess(extending -> log.debug("TTL extendido para clave: {}", fullKey))
                .doOnError(e -> log.error("Error al extender TTL: {}", e.getMessage()));
    }

    public String generateIdempotencyKey(UUID accountId, String requestHash) {
        String rawKey = accountId.toString() + "-" + requestHash;
        return UUID.nameUUIDFromBytes(rawKey.getBytes()).toString();
    }

    public Mono<Boolean> isValid(String idempotencyKey) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            return Mono.just(false);
        }

        String fullKey = KEY_PREFIX + idempotencyKey;
        return redisTemplate.hasKey(fullKey)
                .flatMap(exists -> {
                    if (!exists) {
                        return Mono.just(false);
                    }
                    return redisTemplate.getExpire(fullKey)
                            .map(expire -> expire > 0);
                });
    }
}