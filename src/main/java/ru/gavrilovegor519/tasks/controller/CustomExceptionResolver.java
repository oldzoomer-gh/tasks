package ru.gavrilovegor519.tasks.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.gavrilovegor519.tasks.dto.output.Response;
import ru.gavrilovegor519.tasks.exception.*;

@ControllerAdvice
public class CustomExceptionResolver {

    @ExceptionHandler({CommentNotFoundException.class,
            PaginationOutOfRangeException.class, TaskNotFoundException.class})
    public ResponseEntity<Response> badRequestHandler(Throwable e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Response> conflictHandler(Throwable e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }

    @ExceptionHandler({ForbiddenChangesException.class,
            IncorrectPasswordException.class, UserNotFoundException.class})
    public ResponseEntity<Response> forbiddenHandler(Throwable e) {
        Response response = new Response(e.getMessage());
        return new ResponseEntity<>(response, HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<Response> otherHandler() {
        Response response = new Response("Server error");
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
