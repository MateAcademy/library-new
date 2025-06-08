package com.example.demo.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookResponseDTO {

    Long bookId;
    String title;
    String author;
    Integer year;
    Integer totalCopies;
    Integer inLibraryCopies;

    public BookResponseDTO(Long bookId, String title, String author, Integer year) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public BookResponseDTO(Long bookId, String title, String author, Integer year, int totalCopies) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.year = year;
        this.totalCopies = totalCopies;
    }

}
