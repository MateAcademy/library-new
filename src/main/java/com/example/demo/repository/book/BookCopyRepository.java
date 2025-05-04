package com.example.demo.repository.book;

import com.example.demo.models.Book;
import com.example.demo.models.BookCopy;

import java.util.List;
import java.util.Optional;

public interface BookCopyRepository {
    void save(BookCopy copy);
    void saveAll(List<BookCopy> copies);
    void assignCopy(Long copyId, Long personId);
    void unassignCopy(Long copyId);
    Optional<BookCopy> findById(Long id);
    List<BookCopy> findAvailableByBookId(Long bookId);
    List<BookCopy> findAllWithOwnerByBookId(Long bookId);
    Optional<List<BookCopy>> findByPersonId(Long personId);
    //List<Book> findByPersonId(Long personId);
}
