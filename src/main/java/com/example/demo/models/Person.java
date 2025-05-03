package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Entity
@Table(name = "person")
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    Long person_id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    @Column(name = "name")
    String name;

    @Min(value = 0, message = "Age should be greater then 0")
    @Max(value = 120, message = "Age should be less then 120")
    @Column(name = "age")
    Integer age;

    @NotEmpty(message = "Email should not be empty")
    @Email
    @Column(name = "email")
    String email;

    @NotEmpty(message = "City should not be empty")
    @Size(min = 10, max = 50, message = "City should be between 2 and 30 characters")
    @Pattern(
        regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
        message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
    )
    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "owner")
    List<Book> books;

    public Person(Long person_id, String name, Integer age, String email, String address) {
        this.person_id = person_id;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(String name, Integer age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(String name, Integer age, String email, String address, List<Book> books) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
        this.books = books;
    }
}
