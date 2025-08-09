package com.example.demo.mapper.media_classification;

import com.example.demo.dto.media_classification.PlatformRequest;
import com.example.demo.dto.media_classification.PlatformResponse;
import com.example.demo.model.media_classification.Platform;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class PlatformMapper {

    @NotNull
    public PlatformResponse mapToPlatformResponse(@NotNull Platform platform) {
        return new PlatformResponse(
                platform.getId(),
                platform.getName()
        );
    }

    @NotNull
    public Platform mapToPlatform(@Nullable Long platformId, @NotNull PlatformRequest request) {
        final Platform platform = new Platform();
        platform.setName(StringUtils.trim(request.name()));
        Optional.ofNullable(platformId).ifPresent(platform::setId);

        return platform;
    }
}