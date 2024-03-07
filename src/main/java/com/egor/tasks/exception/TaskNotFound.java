package com.egor.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Task not found.")
public class TaskNotFound extends RuntimeException {
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
