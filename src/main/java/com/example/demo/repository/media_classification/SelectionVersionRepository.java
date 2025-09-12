package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.SelectionVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface SelectionVersionRepository extends JpaRepository<SelectionVersion, Long> {
    @Modifying
    @Query("delete from SelectionVersion sv where sv.saveDataLeaf.id = :leafId")
    void deleteByLeafId(@Param("leafId") Long leafId);
}