package com.example.demo.repository.person.jpa;

import com.example.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonJpaSpringDataRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByEmailAndPassword(String email, String password);

    Optional<Person> findByEmail(String email);

    @Query("SELECT MAX(p.personId) FROM Person p")
    Optional<Long> findMaxPersonId();

    @Query("SELECT p FROM Person p JOIN p.libraries l WHERE l.libraryId = :libraryId")
    Page<Person> findByLibraries_LibraryId(Long libraryId, Pageable pageable);
}