package com.example.demo.service;

import com.example.demo.dto.PersonResponse;
import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.models.Library;
import com.example.demo.models.Person;
import com.example.demo.repository.person.PersonRepository;
import com.example.demo.utils.mappers.PeopleMapper;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleService {

    final PersonRepository personRepository;
    final PeopleMapper peopleMapper;
    final LibraryService libraryService;

    public String getNextPersonMediaId() {
        Long maxId = personRepository.findMaxPersonId().orElse(0L);
        Long nextId = maxId + 1;
        return String.format("EDC%08d", nextId);
    }

    public List<PersonResponse> getAllPeople() {
        List<Person> allPeople = personRepository.findAll(Sort.by("name").ascending());
        return peopleMapper.mapToPersonResponseList(allPeople);
    }

    public Page<PersonResponse> getPeoplePageByLibrary(Long libraryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size,  Sort.by("name").ascending());
        Page<Person> personPage = personRepository.findByLibraryId(libraryId, pageable);
        List<PersonResponse> responses = peopleMapper.mapToPersonResponseList(personPage.getContent());
        return new PageImpl<>(responses, pageable, personPage.getTotalElements());
    }

    public Optional<Person> getPersonById(@NonNull Long person_id) {
        return personRepository.findByPersonId(person_id);
    }

    public void save(@NonNull Person person, HttpSession session) {
        final Long libraryId = (Long) session.getAttribute("libraryId");
        if (libraryId != null) {
            Library library = libraryService.findById(libraryId)
                    .orElseThrow(() -> new IllegalArgumentException("Library not found with id: " + libraryId));

            Set<Library> libraries = new HashSet<>();
            libraries.add(library);
            person.setLibraries(libraries);
        }

        final String nextPersonMediaId = this.getNextPersonMediaId();
        person.setPersonMediaId(nextPersonMediaId);

        personRepository.save(person);
 //     System.out.println("PeopleService.class save person to DB personId = " + person.getPersonId());
    }

    public void update(@NonNull Person updatedPerson, HttpSession session) {
        Long libraryId = (Long) session.getAttribute("libraryId");
        if (libraryId != null) {
            Library library = libraryService.findById(libraryId)
                    .orElseThrow(() -> new IllegalArgumentException("Library not found with id: " + libraryId));

            Set<Library> libraries = new HashSet<>();
            libraries.add(library);
            updatedPerson.setLibraries(libraries);
        }

        personRepository.update(updatedPerson);
    }

    public void detachPersonFromLibrary(Long personId, Long libraryId) {
        Optional<Person> personOpt = personRepository.findByPersonId(personId);

        if (personOpt.isEmpty()) {
            throw new PersonNotDeletedException(personId);
        }

        Person person = personOpt.get();

        // Удаляем только связь с этой библиотекой
        person.getLibraries().removeIf(lib -> lib.getLibraryId().equals(libraryId));

        // Если после удаления библиотек больше нет и у него нет книг — тогда можно удалить самого человека
        if (person.getLibraries().isEmpty() && person.getBooks().isEmpty()) {
            personRepository.delete(personId);
        } else {
            personRepository.save(person); // обновим связи
        }
    }

//    public void deleteIfInLibrary(@NonNull Long personId, @NonNull Long libraryId) {
//        Optional<Person> personOpt = personRepository.findByPersonId(personId);
//
//        if (personOpt.isEmpty()) {
//            throw new PersonNotDeletedException(personId);
//        }
//
//        Person person = personOpt.get();
//
//        boolean belongs = person.getLibraries().stream()
//                .anyMatch(lib -> lib.getLibraryId().equals(libraryId));
//
//        if (!belongs) {
//            throw new PersonNotDeletedException(personId); // или AccessDeniedException
//        }
//
//        // Проверка на наличие книг
//        if (!person.getBooks().isEmpty()) {
//            throw new PersonNotDeletedException(personId);
//        }
//
//        personRepository.delete(personId);
//    }

    public void insert1000People() {
        List<Person> people = this.get1000People();

        long start = System.currentTimeMillis();

        for (Person person : people) {
            personRepository.save(person);
        }

        long end = System.currentTimeMillis();
        System.out.println("⏱ Обычная вставка 1000 записей заняла: " + (end - start) + " мс");
    }

    public void butchInsert1000People() {
        List<Person> people = this.get1000People();

        long start = System.currentTimeMillis();

        personRepository.butchSaveAll(people);

        long end = System.currentTimeMillis();
        System.out.println("⏱ Batch вставка 1000 записей заняла: " + (end - start) + " мс");
    }

    private List<Person> get1000People() {
        Optional<Long> lastPersonId = personRepository.findLastPersonId();
        List<Person> people = new ArrayList<>();
        if (lastPersonId.isPresent()) {
            int lastPersonIdFromDB = lastPersonId.get().intValue();
            int newLastPersonId = lastPersonIdFromDB + 1;
            int newLastPersonIdPlus1000 = lastPersonIdFromDB + 1001;

            Long maxId = personRepository.findMaxPersonId().orElse(0L);

            for (int i = newLastPersonId; i < newLastPersonIdPlus1000; i++) {
                Long nextId = ++maxId;

                people.add(new Person(String.format("EDC%08d", nextId), "persone_" + i, 10, "persone_" + i + "@gmail.com", "city_" + i));
            }
        } else {
            Long maxId = personRepository.findMaxPersonId().orElse(0L);

            for (int i = 1; i < 1001; i++) {
                Long nextId = ++maxId;
                people.add(new Person(String.format("EDC%08d", nextId), "persone_" + i, 10, "persone_" + i + "@gmail.com", "city_" + i));
            }
        }
        return people;
    }
}
