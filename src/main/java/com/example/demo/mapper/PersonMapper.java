package com.example.demo.mapper;

import com.example.demo.dto.BookResponseDTO;
import com.example.demo.dto.LibraryShortDto;
import com.example.demo.dto.PersonFormDTO;
import com.example.demo.dto.PersonResponseDTO;
import com.example.demo.models.Person;
import jakarta.validation.constraints.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class PersonMapper {

    public static List<PersonResponseDTO> mapToPersonResponseDTOList(List<Person> people) {
        if (people == null || people.isEmpty()) {
            return Collections.emptyList();
        }

        return people.stream().map(PersonMapper::mapToPersonResponseDTO).collect(Collectors.toList());
    }

    public static PersonResponseDTO mapToPersonResponseDTO(Person person) {
//        List<BookResponseDTO> books = person.getBookCopy().stream()
//                .map(bookCopy -> new BookResponseDTO(
//                        bookCopy.getId(),
//                        bookCopy.getBook().getTitle(),
//                        bookCopy.getBook().getAuthor(),
//                        bookCopy.getBook().getYear()
//                ))
//                .collect(Collectors.toList());
//
//        Set<LibraryShortDto> libraries = person.getLibraries().stream()
//                .map(lib -> new LibraryShortDto(
//                        lib.getLibraryId(),
//                        lib.getName(),
//                        lib.getAddress()
//                ))
//                .collect(Collectors.toSet());

        return new PersonResponseDTO(
                person.getId(),
                person.getMediaId(),
                person.getName(),
                person.getAge(),
                person.getEmail(),
                person.getAddress()
//                books,
//                libraries
        );
    }




    public static Person mapToPersonResponseDTO(@NotNull PersonResponseDTO dto) {
        if (dto == null) {
            return null;
        }

        final Person person = new Person();
        person.setId(dto.getId());
        person.setMediaId(dto.getMediaId());
        person.setName(dto.getName());
        person.setAge(dto.getAge());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());

        return person;
    }

    public static Person mapToPersonFromDTO(PersonFormDTO dto) {
        final Person person = new Person();
        person.setId(dto.getId());
        person.setMediaId(dto.getMediaId());
        person.setName(dto.getName());
        person.setAge(dto.getAge());
        person.setEmail(dto.getEmail());
        person.setAddress(dto.getAddress());
        person.setPassword(dto.getPassword());

        // Пароли чаще всего не редактируются через обычную форму — будь осторожен.
        // person.setPassword(dto.getPassword()); // если поле будет добавлено в DTO

        return person;
    }
}