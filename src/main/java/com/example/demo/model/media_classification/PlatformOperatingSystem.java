package com.example.demo.model.media_classification;

import com.example.demo.model.AuthorEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "platform_operating_systems", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"platform_id", "name"})
})
public class PlatformOperatingSystem extends AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "platform_id", nullable = false)
    private Platform platform;

    @OneToMany(mappedBy = "platformOperatingSystem")
    private final Set<OperatingSystemVersion> operatingSystemVersions = new HashSet<>();
}