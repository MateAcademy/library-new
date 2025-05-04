package com.example.demo.service;

import com.example.demo.models.BookCopy;
import com.example.demo.repository.book.BookCopyRepository;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyService {

    final BookCopyRepository bookCopyRepository;

    public List<BookCopy> getCopiesByBookId(Long bookId) {
        return bookCopyRepository.findAllWithOwnerByBookId(bookId);
    }

    public List<BookCopy> findByPersonId(Long personId) {
        return bookCopyRepository.findByPersonId(personId).orElse(null);
    }
}