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
    name = "selection_platforms",
    uniqueConstraints = @UniqueConstraint(columnNames = {"save_data_leaf_id", "platform_id"})
)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SelectionPlatform {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "save_data_leaf_id", nullable = false)
    private SaveDataLeaf saveDataLeaf;

    @ManyToOne(optional = false)
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;

    public SelectionPlatform(SaveDataLeaf saveDataLeaf, Platform platform) {
        this.saveDataLeaf = saveDataLeaf;
        this.platform = platform;
    }
}