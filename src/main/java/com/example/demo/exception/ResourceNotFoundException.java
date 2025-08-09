package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    private final ExceptionMessage exceptionMessage;
    private final List<Object> args;

    public ResourceNotFoundException(ExceptionMessage exceptionMessage) {
        this.exceptionMessage = exceptionMessage;
        this.args = List.of();
    }

    public ResourceNotFoundException(ExceptionMessage exceptionMessage, List<Object> args) {
        this.exceptionMessage = exceptionMessage;
        this.args = args;
    }
}
