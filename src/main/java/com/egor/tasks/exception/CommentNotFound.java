package com.egor.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Comment not found.")
public class CommentNotFound extends RuntimeException {
    public CommentNotFound() {
        super();
    }

    public CommentNotFound(String message) {
        super(message);
    }

    public CommentNotFound(String message, Throwable cause) {
        super(message, cause);
    }

    public CommentNotFound(Throwable cause) {
        super(cause);
    }

    protected CommentNotFound(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
