package com.example.demo.repository.book.impl;

import com.example.demo.dto.BookCopyDto;
import com.example.demo.dto.BookDto;
import com.example.demo.dto.PersonDto;
import com.example.demo.models.Book;
import com.example.demo.models.BookCopy;
import com.example.demo.models.Person;
import com.example.demo.repository.book.BookRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Profile("jdbc-template")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookJdbcTemplateRepositoryImpl implements BookRepository {

    final JdbcTemplate jdbcTemplate;

    @Override
    public Page<Book> findAll(Pageable pageable) {
        int pageSize = pageable.getPageSize();
        int offset = (int) pageable.getOffset();

        String sql = "SELECT * FROM book ORDER BY book_id LIMIT ? OFFSET ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, pageSize, offset);

        for (Book book : books) {
            String copySql = "SELECT * FROM book_copy WHERE book_id = ?";
            List<BookCopy> copies = jdbcTemplate.query(copySql, (rs, rowNum) -> {
                BookCopy copy = new BookCopy();
                copy.setBookCopyId(rs.getLong("copy_id"));
                copy.setAvailable(rs.getBoolean("is_available"));
                copy.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                copy.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                Long personId = rs.getLong("person_id");
                if (!rs.wasNull()) {
                    Person owner = new Person();
                    owner.setPersonId(personId);
                    copy.setOwner(owner);
                }

                return copy;
            }, book.getBookId());

            book.setCopies(copies);
        }

        String countSql = "SELECT COUNT(*) FROM book";
        int total = jdbcTemplate.queryForObject(countSql, Integer.class);

        return new PageImpl<>(books, pageable, total);
    }


    public Optional<BookDto> findBookDtoById(Long bookId) {
        String sql = """
        SELECT 
            b.book_id, b.title, b.author, b.year,
            bc.copy_id, bc.is_available, bc.created_at, bc.updated_at,
            p.person_id, p.name AS p_name, p.person_media_id
        FROM book b
        LEFT JOIN book_copy bc ON bc.book_id = b.book_id
        LEFT JOIN person p ON p.person_id = bc.person_id
        WHERE b.book_id = ?
        ORDER BY bc.copy_id
    """;

        List<BookDto> query = jdbcTemplate.query(sql, rs -> {
            BookDto bookDto = null;
            List<BookCopyDto> copies = new ArrayList<>();

            while (rs.next()) {
                if (bookDto == null) {
                    bookDto = new BookDto();
                    bookDto.setBookId(rs.getLong("book_id"));
                    bookDto.setTitle(rs.getString("title"));
                    bookDto.setAuthor(rs.getString("author"));
                    bookDto.setYear(rs.getInt("year"));
                    bookDto.setCopies(copies);
                }

                Long copyId = rs.getObject("book_copy_id", Long.class);
                if (copyId != null) {
                    BookCopyDto copyDto = new BookCopyDto();
                    copyDto.setBookCopyId(copyId);
                    copyDto.setAvailable(rs.getBoolean("is_available"));
                    copyDto.setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                    copyDto.setUpdatedAt(rs.getTimestamp("updated_at").toLocalDateTime());

                    Long personId = rs.getObject("person_id", Long.class);
                    if (personId != null) {
                        PersonDto person = new PersonDto(
                            personId,
                            rs.getString("p_name"),
                            rs.getString("person_media_id")
                        );
                        copyDto.setOwner(person);
                    }

                    copies.add(copyDto);
                }
            }

            return bookDto == null ? List.of() : List.of(bookDto);
        }, bookId);

        return query.stream().findFirst();
    }

    public Optional<Book> findById(long book_id) {
        String sql = "SELECT * FROM book WHERE book_id = ?";
        List<Book> books = jdbcTemplate.query(sql, bookRowMapper, book_id);
        return books.stream().findFirst();
    }
    
    public Optional<Book> findByTitle(String title) {
        String sql = "SELECT * FROM book WHERE title = ?";
        List<Book> people = jdbcTemplate.query(sql, bookRowMapper, title);
        return people.stream().findFirst();
    }

    public void save(Book book) {
        String sql = "INSERT INTO book(title, author, year) VALUES (?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[] { "book_id" });
            ps.setString(1, book.getTitle());
            ps.setString(2, book.getAuthor());
            ps.setInt(3, book.getYear());
            return ps;
        }, keyHolder);

        Number key = keyHolder.getKey();
        if (key != null) {
            book.setBookId(key.longValue());
        }
    }

    public void update(Book book) {
        String sql = "UPDATE book SET title = ?, author = ?, year = ? WHERE book_id = ?";
        jdbcTemplate.update(sql,
            book.getTitle(),
            book.getAuthor(),
            book.getYear(),
            book.getBookId());
    }

    public void delete(long id) {
        String sql = "DELETE FROM book WHERE book_id = ?";
        jdbcTemplate.update(sql, id);
    }

    @Override
    @Transactional
    public void butchSaveAll(List<Book> books) {
//        String sql = "INSERT INTO book(title, author, year, person_id) VALUES (?, ?, ?, ?)";
//
//        try {
//            int[][] updateCounts = jdbcTemplate.batchUpdate(sql, books, 1000, (ps, book) -> {
//                ps.setString(1, book.getTitle());
//                ps.setString(2, book.getAuthor());
//                ps.setInt(3, book.getYear());
//                ps.setObject(4, book.getOwner() != null ? book.getOwner().getPerson_id() : null);
//            });
//
//            // Подсчёт общего количества вставленных строк
//            int totalInserted = Arrays.stream(updateCounts)
//                .flatMapToInt(IntStream::of)
//                .sum();
//            System.out.println("✅ Batch вставка выполнена успешно. Вставлено строк: " + totalInserted);
//
//        } catch (DataAccessException e) {
//            System.err.println("❌ Ошибка при batch вставке. Транзакция будет откатана.");
//            throw e;
//        }
    }

    private final RowMapper<Book> bookRowMapper = (rs, rowNum) -> {
        Book book = new Book();
        book.setBookId(rs.getLong("book_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));
        return book;
    };
}

