package com.example.demo.errors;

public class PersonNotDeletedException extends RuntimeException {

    public PersonNotDeletedException(Long person_id) {
        super("Person with person_id " + person_id + " not deleted");
    }
}
