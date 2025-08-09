package com.example.demo.exception;

import java.util.List;

public interface TranslationException {

    ExceptionMessage getExceptionMessage();
    List<Object> getArgs();
}
