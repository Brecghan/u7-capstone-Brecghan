package com.nashss.se.trainingmatrix.exceptions;

/**
 * Exception to throw when a given training ID is not found in the database.
 */
public class TrainingNotFoundException extends RuntimeException {


    private static final long serialVersionUID = 8943169493423330230L;

    /**
     * Exception with no message or cause.
     */
    public TrainingNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public TrainingNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public TrainingNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public TrainingNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
