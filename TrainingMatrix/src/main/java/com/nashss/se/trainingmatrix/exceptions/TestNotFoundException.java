package com.nashss.se.trainingmatrix.exceptions;

/**
 * Exception to throw when a given Test ID is not found in the database.
 */
public class TestNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 8699091180194172987L;

    /**
     * Exception with no message or cause.
     */
    public TestNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public TestNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public TestNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public TestNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}