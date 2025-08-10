package com.example.demo.model.media_classification;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(
    name = "save_data_leaf",
    uniqueConstraints = @UniqueConstraint(columnNames = {"doc_type", "doc_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SaveDataLeaf {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "doc_type", nullable = false, length = 50)
    private DocumentLeafType docType;

    @Column(name = "doc_id", nullable = false)
    private Long docId;

    @OneToMany(mappedBy = "saveDataLeaf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SelectionPlatform> platformSelections = new HashSet<>();

    @OneToMany(mappedBy = "saveDataLeaf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SelectionPlatformOperatingSystem> platformOperatingSystemSelections = new HashSet<>();

    @OneToMany(mappedBy = "saveDataLeaf", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SelectionVersion> versionSelections = new HashSet<>();
}