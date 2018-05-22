package com.gelo.persistance.exception;


/**
 * The type Transaction failed exception.
 */
public class TransactionRuntimeException extends RuntimeException {

    /**
     * Instantiates a new Transaction failed exception.
     */
    public TransactionRuntimeException() {
        super();
    }

    /**
     * Instantiates a new Transaction failed exception.
     *
     * @param cause the cause
     */
    public TransactionRuntimeException(Throwable cause) {
        super(cause);
    }

}
