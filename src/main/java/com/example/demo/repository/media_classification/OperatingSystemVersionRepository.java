package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.OperatingSystemVersion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

@Repository
public interface OperatingSystemVersionRepository extends JpaRepository<OperatingSystemVersion, Long> {

    Optional<OperatingSystemVersion> findByPlatformOperatingSystemIdAndName(Long platformOperatingSystemId, String name);

    Set<OperatingSystemVersion> findAllByIdIn(Set<Long> ids);

    Stream<OperatingSystemVersion> findAllByPlatformOperatingSystemIdIn(Set<Long> ids);

    Stream<OperatingSystemVersion> findAllBy();

    List<OperatingSystemVersion> findAllByPlatformOperatingSystem_Id(Long platformOperatingSystemId);

    Page<OperatingSystemVersion> findAllByPlatformOperatingSystem_Id(Long platformOperatingSystemId, Pageable pageable);

}