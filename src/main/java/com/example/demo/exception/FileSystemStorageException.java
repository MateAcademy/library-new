package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.io.IOException;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class FileSystemStorageException extends RuntimeException {

    public FileSystemStorageException(IOException cause) {
        super(cause);
    }
}