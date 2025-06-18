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
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
@Table(
    name = "person",
    indexes = {
        @Index(name = "idx_person_media_id", columnList = "person_media_id")
    }
)
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
    @Column(name = "id", columnDefinition = "bigint default nextval('person_id_seq')")
    Long id;

    @Column(name = "media_id")
    @NotBlank(message = "MediaId should not be empty")
    String mediaId;

    @Column(name = "name")
    @NotBlank(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Name should be between 2 and 30 characters")
    String name;

    @Column(name = "age")
    @Min(value = 0, message = "Age should be greater then 0")
    @Max(value = 120, message = "Age should be less then 120")
    Integer age;

    @Column(name = "email", unique = true)
    @NotBlank(message = "Email should not be empty")
    @Email
    String email;

    @NotBlank(message = "Password should not be empty")
    @Column(name = "password")
    String password;

    @NotBlank(message = "Address should not be empty")
    @Size(min = 10, max = 50, message = "Address should be between 2 and 30 characters")
    @Pattern(
        regexp = "^[\\p{L}\\-]+, [\\p{L}\\- ]+, \\d{6}$",
        message = "Адрес должен быть в формате: 'Страна, Город, 123456'"
    )
    @Column(name = "address")
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

    public Person(String mediaId, String name, Integer age, String email, String password, String address ) {
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
