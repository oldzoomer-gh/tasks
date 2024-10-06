package ru.gavrilovegor519.tasks.exception;

public class ForbiddenChangesException extends RuntimeException {
    public ForbiddenChangesException() {
        super();
    }

    public ForbiddenChangesException(String message) {
        super(message);
    }

    public ForbiddenChangesException(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenChangesException(Throwable cause) {
        super(cause);
    }

    protected ForbiddenChangesException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
