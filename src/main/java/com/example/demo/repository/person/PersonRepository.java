package com.example.demo.repository.person;

import com.example.demo.models.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    List<Person> findAll();
    Page<Person> findAll(Pageable pageable);
    Optional<Person> findById(Long id);
    Optional<Person> findByEmail(String email);
    void save(Person person);
    void update(Person person);
    void delete(Long id);
    void butchSaveAll(List<Person> people);
    Optional<Long> findLastPersonId();
}
