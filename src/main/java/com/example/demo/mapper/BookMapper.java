package com.example.demo.mapper;

import com.example.demo.dto.BookResponseDTO;
import com.example.demo.models.Book;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookMapper {

    public List<BookResponseDTO> mapToBookResponseList(List<Book> books) {
        return books.stream()
            .map(book -> new BookResponseDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear() ))
            .collect(Collectors.toList());
    }

    public BookResponseDTO mapToBookResponse(Book book) {
        return new BookResponseDTO(book.getBookId(), book.getTitle(), book.getAuthor(), book.getYear());
    }
}