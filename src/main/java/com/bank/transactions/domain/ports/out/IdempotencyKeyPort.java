package com.bank.transactions.domain.ports.out;

import reactor.core.publisher.Mono;

/**
 * Puerto de salida para el manejo de claves de idempotencia utilizando Redis.
 * Define el contrato para almacenar y recuperar claves que garantizan
 * que una transacción no se procese más de una vez.
 */
public interface IdempotencyKeyPort {

    /**
     * Almacena una clave de idempotencia asociada a un ID de transacción.
     * La clave expira después de 24 horas para permitir la reutilización
     * del espacio en Redis y evitar acumulación de datos históricos.
     *
     * @param idempotencyKey La clave única de idempotencia (UUID + hash)
     * @param transactionId  El ID de la transacción asociada
     * @return Mono vacío que completa exitosamente si se almacena correctamente
     */
    Mono<Void> store(String idempotencyKey, String transactionId);

    /**
     * Recupera el ID de transacción asociado a una clave de idempotencia.
     * Si la clave no existe o ha expirado, retorna Mono.empty().
     *
     * @param idempotencyKey La clave de idempotencia a buscar
     * @return Mono con el ID de transacción si existe, o Mono.empty() si no
     */
    Mono<String> get(String idempotencyKey);

    /**
     * Verifica si una clave de idempotencia ya existe en el store.
     * Útil para validar rápidamente antes de procesar una nueva transacción.
     *
     * @param idempotencyKey La clave a verificar
     * @return Mono con true si existe, false en caso contrario
     */
    Mono<Boolean> exists(String idempotencyKey);

    /**
     * Elimina una clave de idempotencia después de que la transacción
     * ha sido procesada exitosamente. Esta operación es opcional y
     * depende de la política de retención de datos del sistema.
     *
     * @param idempotencyKey La clave a eliminar
     * @return Mono vacío que completa cuando se elimina la clave
     */
    Mono<Void> remove(String idempotencyKey);
}