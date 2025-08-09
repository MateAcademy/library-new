package com.example.demo.repository.library;

import com.example.demo.model.Library;

import java.util.List;
import java.util.Optional;

public interface LibraryRepository {

    List<Library> findAll();

    Optional<Library> findById(Long id);

}
