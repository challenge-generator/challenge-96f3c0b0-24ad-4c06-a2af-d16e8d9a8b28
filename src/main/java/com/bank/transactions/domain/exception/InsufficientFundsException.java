package com.bank.transactions.domain.exception;

/**
 * Excepción personalizada que se lanza cuando una cuenta no tiene
 * fondos suficientes para realizar una operación bancaria.
 * Esta excepción indica un error de negocio recuperable donde el
 * cliente debe verificar su saldo antes de intentar nuevamente.
 */
public class InsufficientFundsException extends RuntimeException {

    private final String accountId;
    private final java.math.BigDecimal availableBalance;
    private final java.math.BigDecimal requestedAmount;

    /**
     * Constructor con mensaje de error y detalles de la cuenta.
     *
     * @param accountId        ID de la cuenta sin fondos suficientes
     * @param availableBalance Saldo disponible en la cuenta
     * @param requestedAmount  Monto solicitado que excede el saldo
     */
    public InsufficientFundsException(String accountId, java.math.BigDecimal availableBalance, 
                                       java.math.BigDecimal requestedAmount) {
        super(String.format("Fondos insuficientes en cuenta %s: disponible=%s, solicitado=%s",
                accountId, availableBalance, requestedAmount));
        this.accountId = accountId;
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    /**
     * Constructor con mensaje personalizado.
     *
     * @param message Mensaje de error personalizado
     */
    public InsufficientFundsException(String message) {
        super(message);
        this.accountId = null;
        this.availableBalance = null;
        this.requestedAmount = null;
    }

    /**
     * Constructor con causa raíz.
     *
     * @param message Mensaje de error
     * @param cause   Excepción que causó este error
     */
    public InsufficientFundsException(String message, Throwable cause) {
        super(message, cause);
        this.accountId = null;
        this.availableBalance = null;
        this.requestedAmount = null;
    }

    /**
     * Constructor con causa y detalles de cuenta.
     *
     * @param accountId        ID de la cuenta
     * @param availableBalance Saldo disponible
     * @param requestedAmount  Monto solicitado
     * @param cause            Causa raíz
     */
    public InsufficientFundsException(String accountId, java.math.BigDecimal availableBalance,
                                       java.math.BigDecimal requestedAmount, Throwable cause) {
        super(String.format("Fondos insuficientes en cuenta %s: disponible=%s, solicitado=%s",
                accountId, availableBalance, requestedAmount), cause);
        this.accountId = accountId;
        this.availableBalance = availableBalance;
        this.requestedAmount = requestedAmount;
    }

    public String getAccountId() {
        return accountId;
    }

    public java.math.BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public java.math.BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    /**
     * Proporciona detalles adicionales para logging y debugging.
     *
     * @return String con información detallada del error
     */
    public String getDetailedMessage() {
        return String.format("InsufficientFundsException{accountId='%s', availableBalance=%s, requestedAmount=%s}",
                accountId, availableBalance, requestedAmount);
    }
}