package com.example.demo.service;

import com.example.demo.dto.PersonResponse;
import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.models.Person;
import com.example.demo.repository.person.PersonRepository;
import com.example.demo.utils.mappers.PeopleMapper;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PeopleService {

    final PersonRepository personRepository;
    final PeopleMapper peopleMapper;

    public String getNextPersonMediaId() {
        Long maxId = personRepository.findMaxPersonId().orElse(0L);
        Long nextId = maxId + 1;
        return String.format("EDC%08d", nextId);
    }

    public List<PersonResponse> getAllPeople() {
        List<Person> allPeople = personRepository.findAll();
        return peopleMapper.mapToPersonResponseList(allPeople);
    }

    public Page<PersonResponse> getPeoplePage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Person> personPage = personRepository.findAll(pageable);
        List<PersonResponse> responses = peopleMapper.mapToPersonResponseList(personPage.getContent());
        return new PageImpl<>(responses, pageable, personPage.getTotalElements());
    }

    public Optional<Person> getPersonById(@NonNull Long person_id) {
        return personRepository.findByPersonId(person_id);
    }

    public void save(@NonNull Person person) {
        final String nextPersonMediaId = this.getNextPersonMediaId();
        person.setPersonMediaId(nextPersonMediaId);

        personRepository.save(person);
        System.out.println("PeopleService.class save person to DB personId = " + person.getPersonId());
    }

    public void update(@NonNull Integer id, @NonNull Person updatedPerson) {
        personRepository.update(updatedPerson);
    }

    public void delete(@NonNull Long id) {
        Optional<Person> personById = personRepository.findByPersonId(id);

        if (personById.isPresent() && personById.get().getBooks().isEmpty()) {
            personRepository.delete(id);
        } else {
            throw new PersonNotDeletedException(id);
        }
    }

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
