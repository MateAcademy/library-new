package com.example.demo.repository.book;

import com.example.demo.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BookRepository {

    Page<Book> findAll(Pageable pageable);
    Optional<Book> findById(long bookId);
    Optional<Book> findByTitle(String title);
    void save(Book book);
    void update(Book book);
    void delete(long book_id);
    void butchSaveAll(List<Book> books);

}
