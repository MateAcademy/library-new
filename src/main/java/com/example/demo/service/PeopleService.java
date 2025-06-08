package com.example.demo.service;

import com.example.demo.dto.PersonResponseDTO;
import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.models.Library;
import com.example.demo.models.Person;
import com.example.demo.repository.person.PersonRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Log4j2
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleService {

    final PersonRepository personRepository;

    public Optional<PersonResponseDTO> login(String email, String password) {
        return personRepository.findByEmailAndPassword(email, password)
                .map(PersonMapper::mapToPersonResponseDTO);
    }

    public String getNextPersonMediaId() {
        Long maxId = personRepository.findMaxPersonId().orElse(0L);
        Long nextId = maxId + 1;
        return String.format("EDC%08d", nextId);
    }

    public List<PersonResponseDTO> getAllPeople() {
        List<Person> allPeople = personRepository.findAll(Sort.by("name").ascending());
        return PersonMapper.mapToPersonResponseDTOList(allPeople);
    }

    public Page<PersonResponseDTO> getPeoplePageByLibrary(Long libraryId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.ASC, "id"));
        Page<Person> personPage = personRepository.findByLibraryId(libraryId, pageable);
        List<PersonResponseDTO> responses = PersonMapper.mapToPersonResponseDTOList(personPage.getContent());
        return new PageImpl<>(responses, pageable, personPage.getTotalElements());
    }

    @NonNull
    public Optional<Person> getPersonById(@NonNull Long id) {
        return personRepository.findByIdWithBooksAndLibraries(id);
    }

    public void save(@NonNull Person person, HttpSession session) {
        this.setCurrentLibrary(person, session);

        final String nextPersonMediaId = this.getNextPersonMediaId();
        person.setMediaId(nextPersonMediaId);

        personRepository.save(person);
        //     System.out.println("PeopleService.class save person to DB personId = " + person.getPersonId());
    }

    public void update(@NonNull Person updatedPerson, HttpSession session) {
        this.setCurrentLibrary(updatedPerson, session);
        personRepository.update(updatedPerson);
    }

    public boolean delete(@NonNull Long personId, @NonNull HttpSession session) {
        final Long libraryId = (Long) session.getAttribute("libraryId");
        final String email = (String) session.getAttribute("email");

        final Person person = personRepository.findByIdWithBooksAndLibraries(personId).orElseThrow(() -> new PersonNotDeletedException(personId));
        String personEmail = person.getEmail();
        this.deletePersonFromLibrary(person, libraryId, personEmail);

        if (Objects.equals(email, personEmail)) {
            session.invalidate();
            return true;
        }
        return false;
    }


    protected void deletePersonFromLibrary(@NonNull Person person, @NonNull Long libraryId, @NonNull String personEmail) {
//todo: проверить когда будут книги смогу ли удалить человека
        if (!person.getBookCopy().isEmpty()) {
            throw new PersonNotDeletedException(person.getId());
        }

        person.getLibraries().removeIf(lib -> lib.getLibraryId().equals(libraryId));

        if (person.getLibraries().isEmpty() && person.getBookCopy().isEmpty()) {
            personRepository.delete(person.getId());
        } else {
            personRepository.save(person); // обновим связи
        }

    }

    public void insert1000People(HttpSession session) {
        List<Person> people = this.get1000People(session);

        long start = System.currentTimeMillis();

        for (Person person : people) {
            personRepository.save(person);
        }

        long end = System.currentTimeMillis();
        System.out.println("⏱ Обычная вставка 1000 записей заняла: " + (end - start) + " мс");
    }

    public void butchInsert1000People(HttpSession session) {
        List<Person> people = this.get1000People(session);
        long start = System.currentTimeMillis();
        personRepository.butchSaveAll(people);
        long end = System.currentTimeMillis();
        System.out.println("⏱ Batch вставка 1000 записей заняла: " + (end - start) + " мс");
    }

    private void setCurrentLibrary(Person person, HttpSession session) {
        Long libraryId = (Long) session.getAttribute("libraryId");
        if (libraryId != null) {
//            Library library = libraryService.findById(libraryId)
//                    .orElseThrow(() -> new IllegalArgumentException("Library not found with id: " + libraryId));
//
            Library library = new Library();
            library.setLibraryId(libraryId);
            Set<Library> libraries = new HashSet<>();
            libraries.add(library);
            person.setLibraries(libraries);
        }
    }

    @NonNull
    private List<Person> get1000People(@NonNull HttpSession session) {
        final List<Person> people = new ArrayList<>();

        for (long i = 0; i < 1000; i++) {
            Person person = new Person(String.format("EDC%08d", i), "persone_" + i, 10, "persone_" + i + "@gmail.com", "123", "Ukraine, Kiev, 123456");
            this.setCurrentLibrary(person, session);
            people.add(person);
        }
        return people;
    }
}
