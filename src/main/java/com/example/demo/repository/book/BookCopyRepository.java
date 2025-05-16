package com.example.demo.repository.book;

import com.example.demo.models.BookCopy;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository {

    int countByBookId(Long bookId);
    void saveAll(List<BookCopy> copies);
    void assignBookCopy(Long copyId, Long personId);
    void unassignBookCopy(Long copyId);
    Optional<BookCopy> findById(Long id);
    List<BookCopy> findAvailableByBookId(Long bookId);
    List<BookCopy> findAllWithOwnerByBookId(Long bookId);
    Optional<List<BookCopy>> findByPersonId(Long personId);

}
