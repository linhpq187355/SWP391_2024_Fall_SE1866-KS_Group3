package com.homesharing.exception;


/**
 * Custom exception class for handling general exceptions related to UserDao operations
 * or any other application-specific logic.
 * <p>
 * This exception extends {@code RuntimeException} and can be thrown when an
 * error occurs during runtime.
 */
public class GeneralException extends RuntimeException {

    /**
     * Constructs a new {@code GeneralException} with the specified detail message.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     */
    public GeneralException(String message) {
        super(message);
    }

    /**
     * Constructs a new {@code GeneralException} with the specified detail message and cause.
     *
     * @param message the detail message (which is saved for later retrieval by the {@link #getMessage()} method).
     * @param cause   the cause (which is saved for later retrieval by the {@link #getCause()} method).
     */
    public GeneralException(String message, Throwable cause) {
        super(message, cause);
    }
}
