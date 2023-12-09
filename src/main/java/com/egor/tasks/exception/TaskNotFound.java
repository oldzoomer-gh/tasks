package com.egor.tasks.exception;

public class TaskNotFound extends Exception {
    public TaskNotFound() {
        super();
    }

    public TaskNotFound(String message) {
        super(message);
    }

    public TaskNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public TaskNotFound(Throwable cause) {
        super(cause);
    }

    protected TaskNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
