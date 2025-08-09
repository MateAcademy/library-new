package com.example.demo.repository.library.jpa;

import com.example.demo.model.Library;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryJpaSpringDataRepository extends JpaRepository<Library, Long> {

}