package com.example.demo.repository.book.impl;

import com.example.demo.models.Book;
import com.example.demo.repository.book.jpa.BookJpaSpringDataRepository;
import com.example.demo.repository.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@RequiredArgsConstructor
public class BookJpaRepositoryImpl implements BookRepository {

    private final BookJpaSpringDataRepository jpaRepository;

    @Override
    public Page<Book> findAll(Pageable pageable) {
        return jpaRepository.findAll(pageable);
    }

    @Override
    public Optional<Book> findById(long bookId) {
        return jpaRepository.findById(bookId);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return jpaRepository.findByTitle(title);
    }

    @Override
    public void save(Book book) {
        jpaRepository.save(book);
    }

    @Override
    public void update(Book book) {
        jpaRepository.save(book); // save работает и для обновления
    }

    @Override
    public void delete(long bookId) {
        jpaRepository.deleteById(bookId);
    }

    @Override
    public void butchSaveAll(List<Book> books) {
        jpaRepository.saveAll(books);
    }
}