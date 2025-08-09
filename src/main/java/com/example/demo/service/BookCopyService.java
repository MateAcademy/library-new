package com.example.demo.service;

import com.example.demo.model.BookCopy;
import com.example.demo.repository.book.BookCopyRepository;
import lombok.AccessLevel;
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

    public void assignBook(Long copyId, Long personId) {
        bookCopyRepository.assignBookCopy(copyId, personId);
    }


    public void unassignBook(Long copyId) {
        bookCopyRepository.unassignBookCopy(copyId);
    }
}