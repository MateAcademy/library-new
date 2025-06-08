package com.example.demo.repository.book.impl;

import com.example.demo.models.Book;
import com.example.demo.models.BookCopy;
import com.example.demo.models.Person;
import com.example.demo.repository.book.BookCopyRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("jdbc-template")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyJdbcTemplateRepositoryImpl implements BookCopyRepository {

    final JdbcTemplate jdbcTemplate;

    @Override
    public Integer countByBookId(Long bookId) {
        String sql = "SELECT COUNT(*) FROM book_copy WHERE book_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, bookId);
    }

    @Override
    public Integer countByBookIdInLibrary(Long bookId, Long libraryId) {
        return 0;
    }

    @Override
    public void saveAll(List<BookCopy> copyList) {
        String sql = "INSERT INTO book_copy (book_id, person_id, is_available) VALUES (?, ?, ?)";
        jdbcTemplate.batchUpdate(sql, copyList, 1000, (ps, copy) -> {
            ps.setLong(1, copy.getBook().getBookId());
            ps.setObject(2,  null);
            ps.setBoolean(3, true);
        });
    }

    @Override
    public void assignBookCopy(Long copyId, Long personId) {
        String sql = "UPDATE book_copy SET person_id = ?, is_available = false WHERE copy_id = ?";
        jdbcTemplate.update(sql, personId, copyId);
    }

    @Override
    public void unassignBookCopy(Long copyId) {
        String sql = "UPDATE book_copy SET person_id = NULL, is_available = true WHERE copy_id = ?";
        jdbcTemplate.update(sql, copyId);
    }

    @Override
    public List<BookCopy> findAllWithOwnerByBookId(Long bookId) {
        String sql = """
            SELECT bc.copy_id, bc.book_id, bc.person_id, bc.is_available, 
                   p.person_media_id, p.person_id as p_id, p.name as p_name
            FROM book_copy bc
            LEFT JOIN person p ON bc.person_id = p.person_id
            WHERE bc.book_id = ?
            ORDER BY bc.copy_id
            """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BookCopy copy = new BookCopy();
            copy.setId(rs.getLong("copy_id"));
            copy.setAvailable(rs.getBoolean("is_available"));

            Book book = new Book();
            book.setBookId(rs.getLong("book_id"));
            copy.setBook(book);

            String mediaId = rs.getObject("media_id", String.class);
            Long personId = rs.getObject("person_id", Long.class);
            if (personId != null) {
                Person person = new Person();
                person.setMediaId(mediaId);
                //person.setPersonId(personId);
                person.setName(rs.getString("p_name")); // важно: имя вытаскиваем
                copy.setOwner(person);
            }

            return copy;
        }, bookId);
    }


    @Override
    public Optional<List<BookCopy>> findByPersonId(Long personId) {
        String sql = """
            SELECT bc.copy_id, bc.book_id, bc.person_id, bc.is_available,
                   b.title as book_title,
                   p.person_id as p_id, p.name as p_name
            FROM book_copy bc
            JOIN book b ON bc.book_id = b.book_id
            LEFT JOIN person p ON bc.person_id = p.person_id
            WHERE bc.person_id = ?
            """;

        List<BookCopy> copies = jdbcTemplate.query(sql, (rs, rowNum) -> {
            BookCopy copy = new BookCopy();
            copy.setId(rs.getLong("copy_id"));
            copy.setAvailable(rs.getBoolean("is_available"));

            Book book = new Book();
            book.setBookId(rs.getLong("book_id"));
            book.setTitle(rs.getString("book_title"));
            copy.setBook(book);

            Person person = new Person();
           // person.setPersonId(rs.getLong("p_id"));
            person.setName(rs.getString("p_name"));
            copy.setOwner(person);

            return copy;
        }, personId);

        return copies.isEmpty() ? Optional.empty() : Optional.of(copies);
    }

    @Override
    public Optional<BookCopy> findById(Long id) {
        String sql = "SELECT * FROM book_copy WHERE book_id = ?";
        List<BookCopy> result = jdbcTemplate.query(sql, rowMapper, id);
        return result.stream().findFirst();
    }


    @Override
    public List<BookCopy> findAvailableByBookId(Long bookId) {
        String sql = "SELECT * FROM book_copy WHERE book_id = ? AND is_available = true";
        return jdbcTemplate.query(sql, rowMapper, bookId);
    }

    // RowMapper для преобразования результата в объект BookCopy
    RowMapper<BookCopy> rowMapper = (rs, rowNum) -> {
        BookCopy copy = new BookCopy();
        copy.setId(rs.getLong("copy_id"));
        copy.setAvailable(rs.getBoolean("is_available"));

        Long personId = rs.getObject("person_id", Long.class);
        if (personId != null) {
            Person p = new Person();
            //p.setPersonId(personId);
            copy.setOwner(p);
        }

        Book book = new Book();
        book.setBookId(rs.getLong("book_id"));
        copy.setBook(book);

        return copy;
    };
}
