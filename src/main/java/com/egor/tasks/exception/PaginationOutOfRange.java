package com.egor.tasks.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Pagination out of range.")
public class PaginationOutOfRange extends Exception {
    public PaginationOutOfRange() {
        super();
    }

    public PaginationOutOfRange(String message) {
        super(message);
    }

    public PaginationOutOfRange(String message, Throwable cause) {
        super(message, cause);
    }

    public PaginationOutOfRange(Throwable cause) {
        super(cause);
    }

    protected PaginationOutOfRange(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
