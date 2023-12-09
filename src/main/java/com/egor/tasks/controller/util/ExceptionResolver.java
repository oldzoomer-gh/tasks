package com.egor.tasks.controller.util;

import com.egor.tasks.exception.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionResolver {

    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgumentException(HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(CommentNotFound.class)
    public void commentNotFoundException(HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(TaskNotFound.class)
    public void taskNotFoundException(HttpServletResponse response) {
        response.setStatus(HttpStatus.BAD_REQUEST.value());
    }

    @ExceptionHandler(DuplicateUser.class)
    public void duplicateUserException(HttpServletResponse response) {
        response.setStatus(HttpStatus.CONFLICT.value());
    }

    @ExceptionHandler(UserNotFound.class)
    public void userNotFoundException(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }

    @ExceptionHandler(IncorrectPassword.class)
    public void incorrectPasswordException(HttpServletResponse response) {
        response.setStatus(HttpStatus.FORBIDDEN.value());
    }
}
