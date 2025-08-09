package com.example.demo.dto.media_classification;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record PlatformOperatingSystemRequest(
        @NotNull
        Long platformId,

        @NotBlank(message = "Name is required")
        @Size(max = 128)
        String name
) {
}
