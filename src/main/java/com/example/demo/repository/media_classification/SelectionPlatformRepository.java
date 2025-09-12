package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.SelectionPlatform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionPlatformRepository extends JpaRepository<SelectionPlatform, Long> {

    @Modifying
    @Query("delete from SelectionPlatform sp where sp.saveDataLeaf.id = :leafId")
    void deleteByLeafId(@Param("leafId") Long leafId);
}