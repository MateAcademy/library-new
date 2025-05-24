package com.example.demo.repository.book.jpa;

import com.example.demo.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookJpaSpringDataRepository extends JpaRepository<Book, Long> {
    Optional<Book> findByTitle(String title);
}