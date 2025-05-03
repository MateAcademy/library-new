package com.example.demo.errors;

public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(Long person_id) {
        super("Person with person_id " + person_id + " not found");
    }
}
