package com.rafaeldbl.xptosystems.exception;

public class InvalidColumnNameException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public InvalidColumnNameException(String message) {
        super(message);
    }

    public InvalidColumnNameException(final String message, final Throwable cause) {
        super(message, cause);
    }
}
