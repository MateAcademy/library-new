package com.example.demo.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Library {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 100, message = "Name should be between 2 and 100 characters")
    @Column(name = "name", length = 100)
    String name;

    @NotEmpty(message = "Address should not be empty")
    @Size(min = 10, max = 50, message = "City should be between 2 and 30 characters")
//    @Pattern(
//        regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
//        message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
//    )
    @Column(name = "address")
    String address;

    @ManyToMany(mappedBy = "libraries")
    List<Person> personList;

    @OneToMany(mappedBy = "library", cascade = CascadeType.ALL)
    private List<BookCopy> bookCopies = new ArrayList<>();

    public Library(String name, String address) {
        this.name = name;
        this.address = address;
    }
}
