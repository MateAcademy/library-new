package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
import java.util.Set;

@Entity
@Table(
    name = "person",
    indexes = {
        @Index(name = "idx_person_media_id", columnList = "person_media_id")
    }
)
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    Long personId;

    @Column(name = "person_media_id")
    String personMediaId;

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

    @NotEmpty(message = "Password should not be empty")
    @Column(name = "password")
    String password;

    @NotEmpty(message = "Address should not be empty")
    @Size(min = 10, max = 50, message = "Address should be between 2 and 30 characters")
    @Pattern(
        regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
        message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
    )
    @Column(name = "address")
    String address;

    @OneToMany(mappedBy = "owner")
    List<BookCopy> books;

    @ManyToMany
    @JoinTable(
        name = "person_library",
        joinColumns = @JoinColumn(name = "person_id"),
        inverseJoinColumns = @JoinColumn(name = "library_id")
    )
    Set<Library> libraries;

    public Person(String name, Integer age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(String personMediaId, String name, Integer age, String email, String address) {
        this.personMediaId = personMediaId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(Long person_id, String personMediaId, String name, Integer age, String email, String address) {
        this.personId = person_id;
        this.personMediaId = personMediaId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(Long person_id, String personMediaId, String name, Integer age, String email, String password, String address) {
        this.personId = person_id;
        this.personMediaId = personMediaId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
