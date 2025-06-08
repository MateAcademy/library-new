package com.example.demo.repository.person.jpa;

import com.example.demo.models.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PersonJpaSpringDataRepository extends JpaRepository<Person, Long> {

    @EntityGraph(attributePaths = "libraries")
    Optional<Person> findByEmailAndPassword(String email, String password);

    Optional<Person> findByEmail(String email);

    @Query("SELECT MAX(p.id) FROM Person p")
    Optional<Long> findMaxPersonId();

    @EntityGraph(attributePaths = {"bookCopy.book", "libraries"})
    @Query("SELECT p FROM Person p JOIN p.libraries l WHERE l.libraryId = :libraryId")
    Page<Person> findByLibraryIdWithBooksAndLibraries(Long libraryId, Pageable pageable);

    @EntityGraph(attributePaths = {"bookCopy.book", "libraries"})
    @Query("SELECT p FROM Person p WHERE p.id = :id")
    Optional<Person> findByIdWithBooksAndLibraries(Long id);
}