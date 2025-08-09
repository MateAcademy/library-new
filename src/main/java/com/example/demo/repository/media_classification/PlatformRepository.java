package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, Long> {

    Optional<Platform> findByName(String name);

    Set<Platform> findAllByIdIn(Set<Long> platformIds);

    Stream<Platform> findAllBy();

}