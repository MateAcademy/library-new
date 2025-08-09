//package com.example.demo.repository.person.impl;
//
//import com.example.demo.model.Person;
//import com.example.demo.repository.person.PersonRepository;
//import lombok.AccessLevel;
//import lombok.experimental.FieldDefaults;
//import org.springframework.context.annotation.Profile;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//@Component
//@Profile("arrayList")
//@FieldDefaults(level = AccessLevel.PRIVATE)
//public class PersonArrayListRepository implements PersonRepository {
//
//    static Long PEOPLE_COUNT;
//
//    private List<Person> people = new ArrayList<>() {{
//        add(new Person(++PEOPLE_COUNT, "Alla", 10, "alla@gmail.com", "Kiev"));
//        add(new Person(++PEOPLE_COUNT, "Tom", 20, "tom@gmail.com", "Kiev"));
//        add(new Person(++PEOPLE_COUNT, "Bob", 30, "bob@gmail.com", "Lviv"));
//    }};
//
//
//    @Override
//    public Optional<Long> findMaxPersonId() {
//        return Optional.empty();
//    }
//
//    public List<Person> findAll() {
//        return people;
//    }
//
//    @Override
//    public Page<Person> findAll(Pageable pageable) {
//        return null;
//    }
//
//    @Override
//    public Optional<Person> findByPersonId(Long id) {
//        return Optional.empty();
//    }
//
//    public Optional<Person> findById(Long id) {
//        return people.stream().filter(person -> person.getPersonId() == id).findFirst();
//    }
//
//    @Override
//    public Optional<Person> findByEmail(String email) {
//        return Optional.empty();
//    }
//
//    public void save(Person person) {
//        if (person.getPersonId() == null) {
//            person.setPersonId(++PEOPLE_COUNT);
//            people.add(person);
//        }
//    }
//
//    public void update(Person updatedPerson) {
//        if (updatedPerson.getPersonId() == null) {
//            throw new RuntimeException("id is null");
//        } else  {
//            Person personFromDB = this.findById(updatedPerson.getPersonId()).get();
//            personFromDB.setName(updatedPerson.getName());
//            personFromDB.setAge(updatedPerson.getAge());
//            personFromDB.setEmail(updatedPerson.getEmail());
//            personFromDB.setAddress(updatedPerson.getAddress());
//        }
//    }
//
//    public void delete(Long id) {
//        people.removeIf(person -> person.getPersonId() == id);
//    }
//
//    @Override
//    public void butchSaveAll(List<Person> people) {
//        //todo: метод здесь не работает
//    }
//
//    @Override
//    public Optional<Long> findLastPersonId() {
//        return Optional.empty();
//    }
//}
