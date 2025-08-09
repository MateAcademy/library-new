package com.example.demo.model.media_classification;

import com.example.demo.model.AuthorEntity;
import com.fasterxml.jackson.annotation.JsonCreator;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.PersistenceCreator;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor(onConstructor = @__({@JsonCreator(mode = JsonCreator.Mode.PROPERTIES), @PersistenceCreator}))
@Entity
@Table(name = "platforms")
public class Platform extends AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",  nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "platform")
    private Set<PlatformOperatingSystem> platformOperatingSystems = new HashSet<>();

    public Platform(String name) {
        this.name = name;
    }
}