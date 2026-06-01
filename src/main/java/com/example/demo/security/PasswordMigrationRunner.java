package com.example.demo.security;

import com.example.demo.model.Person;
import com.example.demo.repository.person.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PasswordMigrationRunner implements ApplicationRunner {

    final PersonRepository personRepository;
    final PasswordEncoder passwordEncoder;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        final List<Person> people = personRepository.findAll();
        int migrated = 0;

        for (final Person person : people) {
            if (!person.getPassword().startsWith("$2")) {
                person.setPassword(passwordEncoder.encode(person.getPassword()));
                personRepository.save(person);
                migrated++;
            }
        }

        if (migrated > 0) {
            log.info("Миграция паролей завершена: {} пользователей перехешировано в BCrypt", migrated);
        } else {
            log.info("Миграция паролей: все пароли уже в BCrypt");
        }
    }
}
