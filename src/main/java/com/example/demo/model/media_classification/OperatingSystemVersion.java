package com.example.demo.model.media_classification;

import com.example.demo.model.AuthorEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "operating_system_versions", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"platform_operating_system_id", "name"})
})
public class OperatingSystemVersion extends AuthorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "platform_operating_system_id", nullable = false)
    private PlatformOperatingSystem platformOperatingSystem;

    public OperatingSystemVersion(@NotNull PlatformOperatingSystem platformOperatingSystem, @NotNull String name) {
        this.platformOperatingSystem = platformOperatingSystem;
        this.name = name;
    }

    public Platform getPlatform() {
        return platformOperatingSystem.getPlatform();
    }
}