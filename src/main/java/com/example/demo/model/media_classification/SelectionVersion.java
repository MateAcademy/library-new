package com.example.demo.model.media_classification;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
    name = "selection_versions",
    uniqueConstraints = @UniqueConstraint(columnNames = {"save_data_leaf_id", "operating_system_version_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectionVersion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "save_data_leaf_id")
    private SaveDataLeaf saveDataLeaf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "operating_system_version_id")
    private OperatingSystemVersion operatingSystemVersion;

    public SelectionVersion(SaveDataLeaf saveDataLeaf, OperatingSystemVersion operatingSystemVersion) {
        this.saveDataLeaf = saveDataLeaf;
        this.operatingSystemVersion = operatingSystemVersion;
    }
}