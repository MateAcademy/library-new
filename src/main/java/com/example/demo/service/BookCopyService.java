package com.example.demo.service;

import com.example.demo.model.BookCopy;
import com.example.demo.repository.book.BookCopyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
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
        log.info("Assigned book copy id: {} to person id: {}", copyId, personId);
    }


    public void unassignBook(Long copyId) {
        bookCopyRepository.unassignBookCopy(copyId);
        log.info("Unassigned book copy id: {}", copyId);
    }
}