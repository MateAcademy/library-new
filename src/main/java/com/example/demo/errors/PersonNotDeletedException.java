package com.example.demo.errors;

public class PersonNotDeletedException extends RuntimeException {

    public PersonNotDeletedException(Long id) {
        super("Person with id " + id + " not deleted");
    }
}
