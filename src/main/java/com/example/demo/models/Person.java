package com.example.demo.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.BatchSize;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "person")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@BatchSize(size = 100)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq")
    @SequenceGenerator(name = "person_seq", sequenceName = "person_id_seq", allocationSize = 100)
    @Column(name = "id")
    Long id;

    @Column(name = "media_id", unique = true, nullable = false)
    String mediaId;

    @Column(name = "name", length = 50, nullable = false)
    String name;

    @Column(name = "age", nullable = false)
    @Min(1)
    @Max(120)
    Integer age;

    @Email
    @Column(name = "email", unique = true, nullable = false)
    String email;

    @Column(name = "password", nullable = false)
    String password;

    @Column(name = "address", length = 100, nullable = false)
    String address;

    @ManyToMany
    @JoinTable(
        name = "person_library",
        joinColumns = @JoinColumn(name = "id"),
        inverseJoinColumns = @JoinColumn(name = "library_id")
    )
    Set<Library> libraries;

    @OneToMany(mappedBy = "owner")
    List<BookCopy> bookCopy;

    public Person(String mediaId, String name, Integer age, String email, String password, String address) {
        this.mediaId = mediaId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    public Person(Long id, String mediaId, String name, Integer age, String email, String password, String address) {
        this.id = id;
        this.mediaId = mediaId;
        this.name = name;
        this.age = age;
        this.email = email;
        this.password = password;
        this.address = address;
    }
}
