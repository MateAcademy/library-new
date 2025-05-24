package com.example.demo.repository.person;

import com.example.demo.models.Person;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

public interface PersonRepository {

    Optional<Person> findByEmailAndPassword(String email, String password);
    Optional<Long> findMaxPersonId();
    List<Person> findAll();
    Page<Person> findAll(Pageable pageable);
    List<Person> findAll(Sort sort);
    Page<Person> findByLibraryId(Long libraryId, Pageable pageable);
    Optional<Person> findByPersonId(Long id);
    Optional<Person> findByEmail(String email);
    void save(Person person);
    void update(Person person);
    void delete(Long id);
    void butchSaveAll(List<Person> people);
    Optional<Long> findLastPersonId();
}
