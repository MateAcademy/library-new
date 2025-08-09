package com.example.demo.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Getter
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClientRequestException extends IllegalStateException implements TranslationException {

    private final ExceptionMessage exceptionMessage;
    private final List<Object> args;

    public ClientRequestException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.name());
        this.exceptionMessage = exceptionMessage;
        args = List.of();
    }

    public ClientRequestException(ExceptionMessage exceptionMessage, List<Object> args) {
        super(exceptionMessage.name());
        this.exceptionMessage = exceptionMessage;
        this.args = args;
    }

    public ClientRequestException(ExceptionMessage exceptionMessage, Exception cause) {
        super(exceptionMessage.name(), cause);
        this.exceptionMessage = exceptionMessage;
        this.args = List.of();
    }

    public ClientRequestException(ExceptionMessage exceptionMessage,  List<Object> args, Exception cause) {
        super(exceptionMessage.name(), cause);
        this.exceptionMessage = exceptionMessage;
        this.args = args;
    }
}
