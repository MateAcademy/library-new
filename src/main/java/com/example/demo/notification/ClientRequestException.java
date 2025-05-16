package com.example.demo.notification;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientRequestException extends IllegalStateException {

    private final ExceptionMessage exceptionMessage;
    private final Exception  cause;
    private final Object[] args;

    public ClientRequestException(ExceptionMessage exceptionMessage, Exception cause, Object... args) {
        super(exceptionMessage.name());
        this.exceptionMessage = exceptionMessage;
        this. cause =  cause;
        this.args = args;
    }
}