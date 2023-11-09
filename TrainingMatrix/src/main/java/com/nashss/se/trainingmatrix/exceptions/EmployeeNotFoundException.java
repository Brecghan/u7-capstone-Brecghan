package com.nashss.se.trainingmatrix.exceptions;

/**
 * Exception to throw when a given employee ID is not found in the database.
 */
public class EmployeeNotFoundException extends RuntimeException {


    private static final long serialVersionUID = -5292026141820203251L;

    /**
     * Exception with no message or cause.
     */
    public EmployeeNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public EmployeeNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public EmployeeNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public EmployeeNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}