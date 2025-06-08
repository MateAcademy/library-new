package com.example.demo.repository.book;

import com.example.demo.models.BookCopy;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository {

    Integer countByBookId(Long bookId);
    Integer countByBookIdInLibrary(Long bookId, Long libraryId);
    void saveAll(List<BookCopy> copies);
    void assignBookCopy(Long copyId, Long personId);
    void unassignBookCopy(Long copyId);
    Optional<BookCopy> findById(Long id);
    List<BookCopy> findAvailableByBookId(Long bookId);
    List<BookCopy> findAllWithOwnerByBookId(Long bookId);
    Optional<List<BookCopy>> findByPersonId(Long personId);

}
