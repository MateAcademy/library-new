package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class DocumentGenerationException extends RuntimeException {

    public DocumentGenerationException(Throwable cause, String message, Object... args) {
        super(String.format(message, args), cause);
    }
}
