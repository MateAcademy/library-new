package com.example.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "Book")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Long bookId;

    @NotEmpty(message = "Title should not be empty")
    @Size(min = 1, max = 50, message = "Title should be between 1 and 50 characters")
    String title;

    @NotEmpty(message = "Author should not be empty")
    @Size(min = 1, max = 50, message = "Author should be between 1 and 50 characters")
    String author;

    @NotNull(message = "Year is required")
    @Min(value = 1800, message = "Year should be greater then 1800")
    @Max(value = 2026, message = "Age should be less then 2026")
    Integer year;

    @OneToMany(mappedBy = "book")
    List<BookCopy> copies;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
