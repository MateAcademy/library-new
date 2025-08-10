package com.example.demo.repository.media_classification;

import com.example.demo.model.media_classification.DocumentLeafType;
import com.example.demo.model.media_classification.SaveDataLeaf;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SaveDataLeafRepository extends JpaRepository<SaveDataLeaf, Long> {

    Optional<SaveDataLeaf> findByDocTypeAndDocId(DocumentLeafType docType, Long docId);

}