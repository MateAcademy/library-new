package com.example.demo.utils.validators;

import com.example.demo.dto.CreatePersonDTO;
import com.example.demo.repository.person.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class PersonFormDTOValidator implements Validator {

    final PersonRepository personRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return CreatePersonDTO.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        CreatePersonDTO person = (CreatePersonDTO) target;


        if (personRepository.findByEmail(person.getEmail()).isPresent()) {
            errors.rejectValue("email", "400 Error", "Person with this email already exists");
        }

//        // Если новая запись (ID == null)
//        if (person.getId() == null) {
//            if (personRepository.findByEmail(person.getEmail()).isPresent()) {
//                errors.rejectValue("email", "400 Error", "Person with this email already exists");
//            }
//        } else {
//            // Обновление — проверка, отличается ли email
//            personRepository.findByPersonId(person.getId()).ifPresent(existingPerson -> {
//                if (!Objects.equals(existingPerson.getEmail(), person.getEmail())) {
//                    if (personRepository.findByEmail(person.getEmail()).isPresent()) {
//                        errors.rejectValue("email", "400 Error", "Person with this email already exists");
//                    }
//                }
//            });
//        }
    }
}
