package com.example.demo.repository.person.impl;

import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.models.Book;
import com.example.demo.models.BookCopy;
import com.example.demo.models.Library;
import com.example.demo.models.Person;
import com.example.demo.repository.person.PersonRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.sql.PreparedStatement;
import java.util.*;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
@Profile("jdbc-template")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PersonJdbcTemplateRepository implements PersonRepository {

    final JdbcTemplate jdbcTemplate;

    @Override
    public Optional<Person> findByEmailAndPassword(String email, String password) {
        String sql = """
        SELECT 
            p.person_id,
            p.person_media_id,
            p.name,
            p.age,
            p.email,
            p.password,
            p.address,
            
            l.library_id,
            l.name AS library_name,
            l.address AS library_address

        FROM person p
        LEFT JOIN person_library pl ON p.person_id = pl.person_id
        LEFT JOIN library l ON pl.library_id = l.library_id
        WHERE p.email = ? AND p.password = ?
        """;

        List<Person> result = jdbcTemplate.query(sql, rs -> {
            Person person = null;
            Set<Library> libraries = new HashSet<>();

            while (rs.next()) {
                if (person == null) {
                    person = new Person();
                    person.setPersonId(rs.getLong("person_id"));
                    person.setPersonMediaId(rs.getString("person_media_id"));
                    person.setName(rs.getString("name"));
                    person.setAge(rs.getInt("age"));
                    person.setEmail(rs.getString("email"));
                    person.setPassword(rs.getString("password"));
                    person.setAddress(rs.getString("address"));
                }

                Long libraryId = rs.getObject("library_id", Long.class);
                if (libraryId != null) {
                    Library library = new Library();
                    library.setLibraryId(libraryId);
                    library.setName(rs.getString("library_name"));
                    library.setAddress(rs.getString("library_address"));
                    libraries.add(library);
                }
            }

            if (person != null) {
                person.setLibraries(libraries);
            }

            return person == null ? List.of() : List.of(person);
        }, email, password);

        return result.stream().findFirst();
    }

    @Override
    public Optional<Long> findMaxPersonId() {
        String sql = "SELECT MAX(person_id) FROM person";
        Long result = jdbcTemplate.queryForObject(sql, Long.class);
        return Optional.ofNullable(result);
    }

        public List<Person> findAll() {
        System.out.println("findAll in jdbc-template");
        String sql = "SELECT * FROM person";
        return jdbcTemplate.query(sql, personRowMapper);
    }

    @Override
    public Page<Person> findAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        String sql = "SELECT * FROM person ORDER BY person_id LIMIT ? OFFSET ?";
        List<Person> people = jdbcTemplate.query(sql, personRowMapper, pageSize, offset);

        String countSql = "SELECT COUNT(*) FROM person";
        int total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(people, pageable, total);
    }

    @Override
    public Optional<Person> findByPersonId(Long person_id) {
        String sql = """
        SELECT 
            p.person_id, p.person_media_id, p.name, p.age, p.email, p.address,

            bc.copy_id, bc.is_available, bc.created_at, bc.updated_at,
            b.book_id, b.title AS book_title, b.author AS book_author, b.year AS book_year

        FROM person p
        LEFT JOIN book_copy bc ON bc.person_id = p.person_id
        LEFT JOIN book b ON bc.book_id = b.book_id

        WHERE p.person_id = ?
        """;

        List<Person> result = jdbcTemplate.query(sql, rs -> {
            Person person = null;
            List<BookCopy> books = new ArrayList<>();

            while (rs.next()) {
                if (person == null) {
                    person = new Person();
                    person.setPersonId(rs.getLong("person_id"));
                    person.setPersonMediaId(rs.getString("person_media_id"));
                    person.setName(rs.getString("name"));
                    person.setAge(rs.getInt("age"));
                    person.setEmail(rs.getString("email"));
                    person.setAddress(rs.getString("address"));
                }

                Long copyId = rs.getObject("copy_id", Long.class);
                if (copyId != null) {
                    BookCopy copy = new BookCopy();
                    copy.setBookCopyId(copyId);
                    copy.setAvailable(rs.getBoolean("is_available"));
                    copy.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    copy.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                    Book book = new Book();
                    book.setBookId(rs.getLong("book_id"));
                    book.setTitle(rs.getString("book_title"));
                    book.setAuthor(rs.getString("book_author"));
                    book.setYear(rs.getInt("book_year"));
                    copy.setBook(book);

                    books.add(copy);
                }
            }

            if (person != null) {
                person.setBooks(books);
            }

            return person == null ? List.of() : List.of(person);
        }, person_id);

        return result.stream().findFirst();
    }


    public Optional<Person> findByEmail(String email) {
        String sql = "SELECT * FROM person WHERE email = ?";
        List<Person> people = jdbcTemplate.query(sql, personRowMapper, email);
        return people.stream().findFirst();
    }

    public void save(Person person) {
        String sql = "INSERT INTO person(person_media_id, name, age, email, address) VALUES (?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "person_id" });
            ps.setString(1, person.getPersonMediaId());
            ps.setString(2, person.getName());
            ps.setInt(3, person.getAge());
            ps.setString(4, person.getEmail());
            ps.setString(5, person.getAddress());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            person.setPersonId(key.longValue());
        }
    }

    public void update(Person person) {
        String sql = "UPDATE person SET name = ?, age = ?, email = ?, address = ? WHERE person_id = ?";
        jdbcTemplate.update(sql,
            person.getName(),
            person.getAge(),
            person.getEmail(),
            person.getAddress(),
            person.getPersonId());
    }

    public void delete(Long id) {
        try {
            String sql = "DELETE FROM person WHERE person_id = ?";
            jdbcTemplate.update(sql, id);
        } catch (Exception e) {
            throw new PersonNotDeletedException(id);
        }
    }

    @Override
    @Transactional
    public void butchSaveAll(List<Person> people) {
        String sql = "INSERT INTO person(person_media_id, name, age, email, address) VALUES (?, ?, ?, ?, ?)";

        try {
            int[][] updateCounts = jdbcTemplate.batchUpdate(sql, people, 1000, (ps, person) -> {
                ps.setString(1, person.getPersonMediaId());
                ps.setString(2, person.getName());
                ps.setInt(3, person.getAge());
                ps.setString(4, person.getEmail());
                ps.setString(5, person.getAddress());
            });

            int totalInserted = Arrays.stream(updateCounts)
                .flatMapToInt(IntStream::of)
                .sum();
            System.out.println("✅ Batch вставка выполнена успешно. Вставлено строк: " + totalInserted);

        } catch (DataAccessException e) {
            System.err.println("❌ Ошибка при batch вставке. Транзакция будет откатана.");
            throw e;
        }
    }

    @Override
    public Optional<Long> findLastPersonId() {
        String sql = "SELECT MAX(person_id) FROM person";
        Long lastId = jdbcTemplate.queryForObject(sql, Long.class);
        return Optional.ofNullable(lastId);
    }

    // This is an object that maps rows from the table to our Person entity.
    private final RowMapper<Person> personRowMapper = (rs, rowNum) -> new Person(
        rs.getLong("person_id"),
        rs.getString("person_media_id"),
        rs.getString("name"),
        rs.getInt("age"),
        rs.getString("email"),
        rs.getString("address")
    );
}
