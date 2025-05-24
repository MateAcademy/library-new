package com.example.demo.repository.book.impl;

import com.example.demo.models.BookCopy;
import com.example.demo.repository.book.BookCopyRepository;
import com.example.demo.repository.book.jpa.BookCopyJpaSpringDataRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@RequiredArgsConstructor
public class BookCopyJpaRepositoryImpl implements BookCopyRepository {

    private final BookCopyJpaSpringDataRepository jpaRepository;

    @Override
    public int countByBookId(Long bookId) {
        return jpaRepository.countByBook_BookId(bookId);
    }

    @Override
    public void saveAll(List<BookCopy> copies) {
        jpaRepository.saveAll(copies);
    }

    @Override
    @Transactional
    public void assignBookCopy(Long copyId, Long personId) {
        jpaRepository.assignBookCopy(copyId, personId);
    }

    @Override
    @Transactional
    public void unassignBookCopy(Long copyId) {
        jpaRepository.unassignBookCopy(copyId);
    }

    @Override
    public Optional<BookCopy> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public List<BookCopy> findAvailableByBookId(Long bookId) {
        return jpaRepository.findByBook_BookIdAndIsAvailableTrue(bookId);
    }

    @Override
    public List<BookCopy> findAllWithOwnerByBookId(Long bookId) {
        return jpaRepository.findByBook_BookId(bookId);
    }

    @Override
    public Optional<List<BookCopy>> findByPersonId(Long personId) {
        List<BookCopy> list = jpaRepository.findByOwner_PersonId(personId);
        return list.isEmpty() ? Optional.empty() : Optional.of(list);
    }
}