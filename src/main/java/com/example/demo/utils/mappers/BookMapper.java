package com.example.demo.utils.mappers;

import com.example.demo.dto.BookResponse;
import com.example.demo.models.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public List<BookResponse> mapToBookResponseList(List<Book> books) {
        return books.stream()
            .map(book -> new BookResponse(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear() ))
            .collect(Collectors.toList());
    }

    public BookResponse mapToBookResponse(Book book) {
        return new BookResponse(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }
}