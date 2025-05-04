package com.example.demo.utils.validators;

import com.example.demo.models.Book;
import com.example.demo.repository.book.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Objects;

@Component
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class BookValidator implements Validator {

    final BookRepository bookRepository ;

    @Override
    public boolean supports(Class<?> clazz) {
        return Book.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Book book = (Book) target;

        if (book.getBookId() == null) {
            if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
                errors.rejectValue("title", "400 Error", "Book with this title already exists");
            }
        } else {
            bookRepository.findById(book.getBookId()).ifPresent(existingBook -> {
                if (!Objects.equals(existingBook.getTitle(), book.getTitle())) {
                    if (bookRepository.findByTitle(book.getTitle()).isPresent()) {
                        errors.rejectValue("title", "400 Error", "Book with this title already exists");
                    }
                }
            });
        }
    }

}
