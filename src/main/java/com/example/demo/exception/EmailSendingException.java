package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmailSendingException extends RuntimeException implements TranslationException {

    private final ExceptionMessage exceptionMessage;
    private final List<Object> args;

    public EmailSendingException(ExceptionMessage exceptionMessage, List<Object> args, Exception cause) {
        super(exceptionMessage.name(), cause);
        this.exceptionMessage = exceptionMessage;
        this.args = args;
    }
}
