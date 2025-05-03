package com.example.demo.controller.exception;

import com.example.demo.errors.BookNotDeletedException;
import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.errors.PersonNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoSuchBucketException.class)
//    public ServiceErrorResponse exception(NoSuchBucketException exception) {
//        log.warn("NoSuchBucketException: ", exception);
//        return ServiceErrorResponse.builder()
//            .timestamp(LocalDateTime.now().toString())
//            .statusCode(HttpStatus.NOT_FOUND.value())
//            .message("No such bucket")
//            .build();
//    }


    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFound(PersonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotDeletedException.class)
    public String exception(PersonNotDeletedException exception) {
        //log.warn("NoSuchBucketException: ", exception);
        return "/admin/person-not-deleted";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotDeletedException.class)
    public String exception(BookNotDeletedException exception) {
        //log.warn("NoSuchBucketException: ", exception);
        return "/admin/book-not-deleted";
    }

}
