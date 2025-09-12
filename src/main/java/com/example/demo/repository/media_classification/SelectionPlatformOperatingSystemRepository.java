package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.SelectionPlatformOperatingSystem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionPlatformOperatingSystemRepository extends JpaRepository<SelectionPlatformOperatingSystem, Long> {
    @Modifying
    @Query("delete from SelectionPlatformOperatingSystem sp where sp.saveDataLeaf.id = :leafId")
    void deleteByLeafId(@Param("leafId") Long leafId);
}