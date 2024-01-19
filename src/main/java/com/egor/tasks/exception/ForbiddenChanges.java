package com.egor.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Changes of data must do only his author!")
public class ForbiddenChanges extends RuntimeException {
    public ForbiddenChanges() {
        super();
    }

    public ForbiddenChanges(String message) {
        super(message);
    }

    public ForbiddenChanges(String message, Throwable cause) {
        super(message, cause);
    }

    public ForbiddenChanges(Throwable cause) {
        super(cause);
    }

    protected ForbiddenChanges(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
