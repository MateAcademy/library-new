package com.example.demo.repository.person.impl;

import com.example.demo.model.Person;
import com.example.demo.repository.person.PersonRepository;
import com.example.demo.utils.PostgresConnector;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
@Profile("jdbc")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonJdbcRepository implements PersonRepository {

    final PostgresConnector postgresConnector;

    @Override
    public Optional<Person> findByEmailAndPassword(String email, String password) {
        return Optional.empty();
    }

    @Override
    public Optional<Long> findMaxPersonId() {
        return Optional.empty();
    }

    public List<Person> findAll() {
        System.out.println("findAll in jdbc");
        List<Person> people = new ArrayList<>();
        String sql = "SELECT * FROM person";

        try (Connection connection = postgresConnector.connect();
             Statement statement = connection.createStatement();  // Это тот обьект который содержит в себе запрос к БД
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                people.add(mapRow(resultSet));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return people;
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public List<Person> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Person> findByLibraryId(Long libraryId, Pageable pageable) {
        return null;
    }

    @Override
    public Optional<Person> findByPersonId(Long id) {
        return Optional.empty();
    }

    public Optional<Person> findById(Long id) {
        String sql = "SELECT * FROM person WHERE id = ?";
        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return Optional.of(mapRow(rs));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional<Person> findByEmail(String email) {
        return Optional.empty();
    }

    public void save(Person person) {
        String sql = "INSERT INTO person(name, age, email, address) VALUES (?, ?, ?, ?)";

        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, person.getName());
            ps.setInt(2, person.getAge());
            ps.setString(3, person.getEmail());
            ps.setString(4, person.getAddress());

            // никакие данные не возвращает с БД
            ps.executeUpdate();

            ResultSet generatedKeys = ps.getGeneratedKeys();
            if (generatedKeys.next()) {
                //person.setPersonId(generatedKeys.getLong(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(Person updatedPerson) {
        String sql = "UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE person_id = ?";

        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, updatedPerson.getName());
            ps.setInt(2, updatedPerson.getAge());
            ps.setString(3, updatedPerson.getEmail());
            ps.setString(4, updatedPerson.getAddress());
            ps.setLong(5, updatedPerson.getId());

            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void delete(Long person_id) {
        String sql = "DELETE FROM person WHERE person_id = ?";

        try (Connection connection = postgresConnector.connect();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setLong(1, person_id);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void butchSaveAll(List<Person> people) {
        // todo: метод здесь не работает
    }

    @Override
    public Optional<Long> findLastPersonId() {
        return Optional.empty();
    }

    @Override
    public Optional<Person> findByIdWithBooksAndLibraries(Long id) {
        return Optional.empty();
    }

    private Person mapRow(ResultSet rs) throws SQLException {
        return new Person(
            rs.getString("person_media_id"),
            rs.getString("name"),
            rs.getInt("age"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("address")
        );
    }
}
