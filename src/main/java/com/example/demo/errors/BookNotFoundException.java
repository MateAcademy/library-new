package com.example.demo.errors;

import java.util.List;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(String title) {
        super("Book with title " + title + " not found");
    }

    public BookNotFoundException(Long book_id) {
        super("Book with book_id " + book_id + " not found");
    }
}
