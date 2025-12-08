package com.example.demo.repository.library.impl;

import com.example.demo.model.Library;
import com.example.demo.repository.library.LibraryRepository;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static lombok.AccessLevel.PRIVATE;

@Repository
@RequiredArgsConstructor
@Profile("jdbc-template")
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class LibraryJdbcTemplateRepository implements LibraryRepository {

    final JdbcTemplate jdbcTemplate;

    private final RowMapper<Library> libraryRowMapper = (rs, rowNum) -> {
        Library lib = new Library();
        lib.setId(rs.getLong("library_id"));
        lib.setName(rs.getString("name"));
        lib.setAddress(rs.getString("address"));
        return lib;
    };

    @Override
    public List<Library> findAll() {
        String sql = "SELECT * FROM library";
        return jdbcTemplate.query(sql, libraryRowMapper);
    }

    @Override
    public Optional<Library> findById(Long id) {
        String sql = "SELECT * FROM library WHERE library_id = ?";
        List<Library> result = jdbcTemplate.query(sql, libraryRowMapper, id);
        return result.stream().findFirst();
    }
}