package com.example.demo.repository.book.impl;

import com.example.demo.models.Book;
import com.example.demo.models.BookCopy;
import com.example.demo.models.Person;
import com.example.demo.repository.book.BookCopyRepository;
import com.example.demo.repository.book.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;

@Repository
@RequiredArgsConstructor
@Profile("jdbc-template")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyJdbcTemplateRepository implements BookCopyRepository {

    final JdbcTemplate jdbcTemplate;

    @Override
    public void save(BookCopy copy) {
//        String sql = "INSERT INTO book_copy (book_id, person_id, is_available) VALUES (?, ?, ?)";
//        jdbcTemplate.update(sql,
//            copy.getBook().getId(),
//            copy.getOwner() != null ? copy.getOwner().getPerson_id() : null,
//            copy.isAvailable());
    }

    @Override
    public void saveAll(List<BookCopy> copies) {
//        String sql = "INSERT INTO book_copy (book_id, person_id, is_available) VALUES (?, ?, ?)";
//        jdbcTemplate.batchUpdate(sql, copies, 1000, (ps, copy) -> {
//            ps.setLong(1, copy.getBook().getId());
//            ps.setObject(2, copy.getOwner() != null ? copy.getOwner().getPerson_id() : null);
//            ps.setBoolean(3, copy.isAvailable());
//        });
    }

    @Override
    public List<BookCopy> findAllWithOwnerByBookId(Long bookId) {
        String sql = """
        SELECT bc.copy_id, bc.book_id, bc.person_id, bc.is_available,
               p.person_id as p_id, p.name as p_name
        FROM book_copy bc
        LEFT JOIN person p ON bc.person_id = p.person_id
        WHERE bc.book_id = ?
        """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            BookCopy copy = new BookCopy();
            copy.setBookCopyId(rs.getLong("copy_id"));
            copy.setAvailable(rs.getBoolean("is_available"));

            Book book = new Book();
            book.setBookId(rs.getLong("book_id"));
            copy.setBook(book);

            Long personId = rs.getObject("person_id", Long.class);
            if (personId != null) {
                Person person = new Person();
                person.setPersonId(personId);
                person.setName(rs.getString("p_name")); // важно: имя вытаскиваем
                copy.setOwner(person);
            }

            return copy;
        }, bookId);
    }

    //    @Override
//    public List<Book> findByPersonId(Long personId) {
//        String sql = "SELECT * FROM book_copy WHERE book_copy.person_id = ?";
//        return jdbcTemplate.query(sql, bookRowMapper, personId);
//    }

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
            copy.setBookCopyId(rs.getLong("copy_id"));
            copy.setAvailable(rs.getBoolean("is_available"));

            Book book = new Book();
            book.setBookId(rs.getLong("book_id"));
            book.setTitle(rs.getString("book_title"));
            copy.setBook(book);

            Person person = new Person();
            person.setPersonId(rs.getLong("p_id"));
            person.setName(rs.getString("p_name"));
            copy.setOwner(person);

            return copy;
        }, personId);

        return copies.isEmpty() ? Optional.empty() : Optional.of(copies);
    }



    @Override
    public void assignCopy(Long copyId, Long personId) {
        String sql = "UPDATE book_copy SET person_id = ?, is_available = false WHERE id = ?";
        jdbcTemplate.update(sql, personId, copyId);
    }

    @Override
    public void unassignCopy(Long copyId) {
        String sql = "UPDATE book_copy SET person_id = NULL, is_available = true WHERE id = ?";
        jdbcTemplate.update(sql, copyId);
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
        copy.setBookCopyId(rs.getLong("copy_id"));
        copy.setAvailable(rs.getBoolean("is_available"));

        Long personId = rs.getObject("person_id", Long.class);
        if (personId != null) {
            Person p = new Person();
            p.setPersonId(personId);
            copy.setOwner(p);
        }

        Book book = new Book();
        book.setBookId(rs.getLong("book_id"));
        copy.setBook(book);

        return copy;
    };


}
