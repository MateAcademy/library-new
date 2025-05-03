package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@Entity
@Table(name = "Book")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    Long book_id;

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

    @ManyToOne
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    Person owner;

    public Book(String title, String author, Integer year, Person owner) {
        this.title = title;
        this.author = author;
        this.year = year;
        this.owner = owner;
    }
}
