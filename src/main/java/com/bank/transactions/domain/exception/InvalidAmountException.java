package com.bank.transactions.domain.exception;

import java.math.BigDecimal;

/**
 * Excepción personalizada que se lanza cuando el monto de una transacción
 * es inválido. Esto incluye montos negativos, cero, o que excedan los
 * límites permitidos por las regulaciones bancarias.
 */
public class InvalidAmountException extends RuntimeException {

    private final BigDecimal invalidAmount;
    private final String reason;

    /**
     * Constructor con monto inválido y razón del error.
     *
     * @param invalidAmount El monto que causó el error
     * @param reason        Razón específica del error (NEGATIVE, ZERO, EXCEEDS_LIMIT)
     */
    public InvalidAmountException(BigDecimal invalidAmount, String reason) {
        super(String.format("Monto inválido: %s. Razón: %s", invalidAmount, reason));
        this.invalidAmount = invalidAmount;
        this.reason = reason;
    }

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message Mensaje de error personalizado
     */
    public InvalidAmountException(String message) {
        super(message);
        this.invalidAmount = null;
        this.reason = null;
    }

    /**
     * Constructor con causa raíz.
     *
     * @param message Mensaje de error
     * @param cause   Excepción que causó este error
     */
    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
        this.invalidAmount = null;
        this.reason = null;
    }

    /**
     * Constructor con monto, razón y causa.
     *
     * @param invalidAmount El monto inválido
     * @param reason        Razón del error
     * @param cause         Causa raíz
     */
    public InvalidAmountException(BigDecimal invalidAmount, String reason, Throwable cause) {
        super(String.format("Monto inválido: %s. Razón: %s", invalidAmount, reason), cause);
        this.invalidAmount = invalidAmount;
        this.reason = reason;
    }

    public BigDecimal getInvalidAmount() {
        return invalidAmount;
    }

    public String getReason() {
        return reason;
    }

    /**
     * Verifica si el monto es negativo.
     *
     * @return true si el monto es menor que cero
     */
    public boolean isNegative() {
        return "NEGATIVE".equalsIgnoreCase(reason);
    }

    /**
     * Verifica si el monto es cero.
     *
     * @return true si el monto es igual a cero
     */
    public boolean isZero() {
        return "ZERO".equalsIgnoreCase(reason);
    }

    /**
     * Verifica si el monto excede el límite permitido.
     *
     * @return true si el monto excede el límite
     */
    public boolean exceedsLimit() {
        return "EXCEEDS_LIMIT".equalsIgnoreCase(reason);
    }

    /**
     * Proporciona información detallada para logging.
     *
     * @return String con los detalles del error
     */
    public String getDetailedMessage() {
        return String.format("InvalidAmountException{invalidAmount=%s, reason='%s'}",
                invalidAmount, reason);
    }
}