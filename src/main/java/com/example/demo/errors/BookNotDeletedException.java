package com.example.demo.errors;

public class BookNotDeletedException extends RuntimeException {

    public BookNotDeletedException(Long book_id) {
        super("Book with book_id " + book_id + " not deleted");
    }
}