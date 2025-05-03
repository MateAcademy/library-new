package com.example.demo.repository.book.impl;

import com.example.demo.models.Book;
import com.example.demo.models.Person;
import com.example.demo.repository.book.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class BookJdbcTemplateRepository implements BookRepository {

    final JdbcTemplate jdbcTemplate;

    public List<Book> findAll() {
        String sql = "SELECT * FROM book";
        return jdbcTemplate.query(sql, bookRowMapper);
    }

    @Override
    public Page<Book> findAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        String sql = "SELECT * FROM book ORDER BY book_id LIMIT ? OFFSET ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, pageSize, offset);

        String countSql = "SELECT COUNT(*) FROM book";
        int total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(books, pageable, total);
    }

    public Optional<Book> findById(long book_id) {
        String sql = "SELECT * FROM book WHERE book_id = ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, book_id);
        //todo: you can return an error that a person with such an ID was simply not found
        return books.stream().findFirst();
    }

    public Optional<Book> findByTitle(String title) {
        String sql = "SELECT * FROM book WHERE title = ?";
        List<Book> people = jdbcTemplate.query(sql, bookRowMapper, title);
        return people.stream().findFirst();
    }

    public void save(Book book) {
        String sql = "INSERT INTO book(title, author, year, person_id) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
            book.getTitle(),
            book.getAuthor(),
            book.getYear(),
            book.getOwner() != null ? book.getOwner().getPerson_id() : null);
    }

    public void update(Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, year = ?, person_id = ? WHERE book_id = ?";
        jdbcTemplate.update(sql,
            book.getTitle(),
            book.getAuthor(),
            book.getYear(),
            book.getOwner() != null ? book.getOwner().getPerson_id() : null,
            book.getBook_id());
    }

    public void delete(long id) {
        String sql = "DELETE FROM book WHERE book_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    @Transactional
    public void butchSaveAll(List<Book> books) {
        String sql = "INSERT INTO book(title, author, year, person_id) VALUES (?, ?, ?, ?)";

        try {
            int[][] updateCounts = jdbcTemplate.batchUpdate(sql, books, 1000, (ps, book) -> {
                ps.setString(1, book.getTitle());
                ps.setString(2, book.getAuthor());
                ps.setInt(3, book.getYear());
                ps.setObject(4, book.getOwner() != null ? book.getOwner().getPerson_id() : null);
            });

            // Подсчёт общего количества вставленных строк
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
    public List<Book> findByPersonId(Long personId) {
        String sql = "SELECT * FROM book WHERE person_id = ?";
        return jdbcTemplate.query(sql, bookRowMapper, personId);
    }

    public void unassignBook(Long bookId) {
        String sql = "UPDATE book SET person_id = NULL WHERE book_id = ?";
        jdbcTemplate.update(sql, bookId);
    }

    public void assignBook(Long bookId, Long personId) {
        String sql = "UPDATE book SET person_id = ? WHERE book_id = ?";
        jdbcTemplate.update(sql, personId, bookId);
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Person owner = null;
        Long personId = rs.getObject("person_id", Long.class);
        if (personId != null) {
            owner = new Person();
            owner.setPerson_id(personId);
        }

        return new Book(
            rs.getLong("book_id"),
            rs.getString("title"),
            rs.getString("author"),
            rs.getInt("year"),
            owner
        );
    };
}

