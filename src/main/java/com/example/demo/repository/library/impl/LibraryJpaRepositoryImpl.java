package com.example.demo.repository.library.impl;

import com.example.demo.models.Library;
import com.example.demo.repository.library.LibraryRepository;
import com.example.demo.repository.library.jpa.LibraryJpaSpringDataRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@Profile("jpa")
@RequiredArgsConstructor
public class LibraryJpaRepositoryImpl implements LibraryRepository {

    private final LibraryJpaSpringDataRepository jpaRepository;

    @Override
    public List<Library> findAll() {
        return jpaRepository.findAll();
    }

    @Override
    public Optional<Library> findById(Long id) {
        return jpaRepository.findById(id);
    }
}