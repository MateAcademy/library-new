package com.example.demo.utils.validators;

import com.example.demo.models.Person;
import com.example.demo.repository.person.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;
import java.util.Optional;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PersonValidator implements Validator {

    final PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return Person.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Person person = (Person) target;

        // Если новая запись (ID == null)
        if (person.getPersonId() == null) {
            if (personRepository.findByEmail(person.getEmail()).isPresent()) {
                errors.rejectValue("email", "400 Error", "Person with this email already exists");
            }
        } else {
            // Обновление — проверка, отличается ли email
            personRepository.findByPersonId(person.getPersonId()).ifPresent(existingPerson -> {
                if (!Objects.equals(existingPerson.getEmail(), person.getEmail())) {
                    if (personRepository.findByEmail(person.getEmail()).isPresent()) {
                        errors.rejectValue("email", "400 Error", "Person with this email already exists");
                    }
                }
            });
        }
    }

}
