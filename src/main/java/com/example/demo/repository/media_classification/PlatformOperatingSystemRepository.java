package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.Platform;
import com.example.demo.model.media_classification.PlatformOperatingSystem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface PlatformOperatingSystemRepository extends JpaRepository<PlatformOperatingSystem, Long> {

    Set<PlatformOperatingSystem> findAllByPlatformIn(List<Platform> platform);

    Set<PlatformOperatingSystem> findAllByIdIn(Set<Long> platformOperatingSystemIds);

    Optional<PlatformOperatingSystem> findByPlatform_IdAndNameIgnoreCase(Long platformId, String name);

    Page<PlatformOperatingSystem> findAllByPlatform_Id(Long platformId, Pageable pageRequest);

    Stream<PlatformOperatingSystem> findAllByPlatform_Id(Long platformId);

}