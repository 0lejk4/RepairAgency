package com.gelo.model.exception;

/**
 * The type Database exception.
 */
public class DatabaseException extends Exception {
    private String exceptionMessage = "";

    /**
     * Instantiates a new Database exception.
     */
    public DatabaseException() {
        super();
    }

    /**
     * Instantiates a new Database exception.
     *
     * @param exceptionMessage the exception message
     */
    public DatabaseException(String exceptionMessage) {
        super(exceptionMessage);
        this.exceptionMessage = exceptionMessage;
    }

    /**
     * Instantiates a new Database exception.
     *
     * @param cause the cause
     */
    public DatabaseException(Throwable cause) {
        super(cause);
    }

    @Override
    public String toString() {
        return exceptionMessage;
    }
}
