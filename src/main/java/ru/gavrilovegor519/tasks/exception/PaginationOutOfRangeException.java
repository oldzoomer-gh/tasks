package ru.gavrilovegor519.tasks.exception;

public class PaginationOutOfRangeException extends RuntimeException {
    public PaginationOutOfRangeException() {
        super();
    }

    public PaginationOutOfRangeException(String message) {
        super(message);
    }

    public PaginationOutOfRangeException(String message, Throwable cause) {
        super(message, cause);
    }

    public PaginationOutOfRangeException(Throwable cause) {
        super(cause);
    }

    protected PaginationOutOfRangeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
