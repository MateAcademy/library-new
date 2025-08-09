package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book_copy")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @JsonIgnoreProperties("copies")
    Book book;

    @Column(name = "is_available")
    boolean isAvailable;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    Person owner;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "library_id")
    private Library library;

    @CreatedDate
    private LocalDateTime createdAt;

    @LastModifiedDate
    private LocalDateTime updatedAt;
}
