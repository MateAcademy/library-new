package com.example.demo.service;

import com.example.demo.model.Library;
import com.example.demo.repository.library.LibraryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LibraryService {


    final LibraryRepository libraryRepository;

    Optional<Library> findById(Long id){
        return libraryRepository.findById(id);
    }

     public List<Library> findAll(){
        return libraryRepository.findAll();
    }
}
