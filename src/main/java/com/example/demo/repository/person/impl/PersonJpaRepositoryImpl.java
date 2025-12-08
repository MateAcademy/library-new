package com.example.demo.repository.person.impl;

import com.example.demo.model.Person;
import com.example.demo.repository.person.PersonRepository;

import com.example.demo.repository.person.jpa.PersonJpaSpringDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@RequiredArgsConstructor
public class PersonJpaRepositoryImpl implements PersonRepository {

    private final PersonJpaSpringDataRepository jpaRepository;

    @Override
    public Optional<Person> findByEmailAndPassword(String email, String password) {
        return jpaRepository.findByEmailAndPassword(email, password);
    }

    @Override
    public Optional<Long> findMaxPersonId() {
        return jpaRepository.findMaxPersonId();
    }

    @Override
    public List<Person> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public List<Person> findAll(Sort sort) {
        return jpaRepository.findAll(sort);
    }

    @Override
    public Page<Person> findByLibraryId(Long libraryId, Pageable pageable) {
        return jpaRepository.findByLibraryId(libraryId, pageable);
    }

    @Override
    public Optional<Person> findByPersonId(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return jpaRepository.findByEmail(email);
    }

    @Override
    public void save(Person person) {
        jpaRepository.save(person);
    }

    @Override
    public void update(Person person) {
        jpaRepository.save(person); // save работает и как update, если personId != null
    }

    @Override
    public void delete(Long id) {
        jpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public void butchSaveAll(List<Person> people) {
        jpaRepository.saveAll(people);
    }

    @Override
    public Optional<Long> findLastPersonId() {
        return jpaRepository.findMaxPersonId(); // можно вернуть то же самое
    }

    @Override
    public Optional<Person> findByIdWithBooksAndLibraries(Long personId) {
        return jpaRepository.findByIdWithBooksAndLibraries(personId);
    }
}