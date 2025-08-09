package com.example.demo.service.media_classification;

import com.example.demo.exception.ClientRequestException;
import com.example.demo.exception.ExceptionMessage;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.media_classification.Platform;
import com.example.demo.model.media_classification.PlatformOperatingSystem;
import com.example.demo.repository.media_classification.PlatformOperatingSystemRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class PlatformOperatingSystemService {

    private final PlatformOperatingSystemRepository platformOperatingSystemRepository;
    private final PlatformService platformService;

    @NotNull
    public Page<PlatformOperatingSystem> getPageByPlatformId(@NotNull Long platformId, @NotNull PageRequest request) {
        return platformOperatingSystemRepository.findAllByPlatform_Id(platformId, request);
    }

    @NotNull
    public Stream<PlatformOperatingSystem> getAllByPlatformId(@NotNull Long platformId) {
        return platformOperatingSystemRepository.findAllByPlatform_Id(platformId);
    }

    @NotNull
    public List<PlatformOperatingSystem> getByIds(@NotNull Set<Long> ids) {
        if (ids.isEmpty()) {
            return List.of();
        }
        return platformOperatingSystemRepository.findAllById(ids);
    }

    @NotNull
    public PlatformOperatingSystem getById(@NotNull Long platformOperatingSystemId) {
        return platformOperatingSystemRepository.findById(platformOperatingSystemId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.EXCEPTION_PLATFORM_OS_NOT_FOUND_BY_ID, List.of(platformOperatingSystemId)));
    }

    @NotNull
    @Transactional
    public PlatformOperatingSystem create(@NotNull Long platformId, @NotNull String name) {
        final Platform platform = platformService.getById(platformId);
        this.checkForUniqueNameOnCreate(platform.getId(), name);

        final PlatformOperatingSystem platformOperatingSystem = new PlatformOperatingSystem();
        platformOperatingSystem.setName(StringUtils.trim(name));
        platformOperatingSystem.setPlatform(platform);

        return platformOperatingSystemRepository.save(platformOperatingSystem);
    }

    @NotNull
    @Transactional
    public PlatformOperatingSystem update(@NotNull Long osId, @NotNull Long platformId, @NotNull String name) {
        final Platform platform = platformService.getById(platformId);
        this.checkForUniqueNameOnUpdate(platform.getId(), name, osId);

        final PlatformOperatingSystem platformOperatingSystem = this.getById(osId);
        platformOperatingSystem.setName(StringUtils.trim(name));

        return platformOperatingSystemRepository.save(platformOperatingSystem);
    }

    private void checkForUniqueNameOnCreate(@NotNull Long platformId, @NotNull String name) {
        platformOperatingSystemRepository.findByPlatform_IdAndNameIgnoreCase(platformId, name)
                .ifPresent(existing -> {
                    throw new ClientRequestException(
                            ExceptionMessage.EXCEPTION_DUPLICATE_PLATFORM_OPERATING_SYSTEM,
                            List.of(platformId, name));
                });
    }

    private void checkForUniqueNameOnUpdate(@NotNull Long platformId, @NotNull String name, @NotNull Long osId) {
        platformOperatingSystemRepository.findByPlatform_IdAndNameIgnoreCase(platformId, name)
                .filter(existing -> !existing.getId().equals(osId))
                .ifPresent(existing -> {
                    throw new ClientRequestException(
                            ExceptionMessage.EXCEPTION_DUPLICATE_PLATFORM_OPERATING_SYSTEM,
                            List.of(platformId, name)
                    );
                });
    }
}