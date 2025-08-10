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
    name = "selection_pos",
    uniqueConstraints = @UniqueConstraint(columnNames = {"save_data_leaf_id", "platform_operating_system_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectionPlatformOperatingSystem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "save_data_leaf_id")
    private SaveDataLeaf saveDataLeaf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "platform_operating_system_id")
    private PlatformOperatingSystem platformOperatingSystem;

    public SelectionPlatformOperatingSystem(SaveDataLeaf saveDataLeaf, PlatformOperatingSystem platformOperatingSystem) {
        this.saveDataLeaf = saveDataLeaf;
        this.platformOperatingSystem = platformOperatingSystem;
    }
}