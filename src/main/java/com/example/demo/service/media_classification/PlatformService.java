package com.example.demo.service.media_classification;

import com.example.demo.exception.ClientRequestException;
import com.example.demo.exception.ExceptionMessage;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.media_classification.Platform;
import com.example.demo.repository.media_classification.PlatformRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class PlatformService {

    private final PlatformRepository platformRepository;

    @NotNull
    public Page<Platform> getByPage(@NotNull PageRequest pageRequest) {
        return platformRepository.findAll(pageRequest);
    }

    @NotNull
    public Stream<Platform> getAll() {
        return platformRepository.findAllBy();
    }

    @NotNull
    public Platform getById(@NotNull Long platformId) {
        return platformRepository.findById(platformId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.EXCEPTION_PLATFORM_NOT_FOUND_BY_ID, List.of(platformId)));
    }

    @NotNull
    public Set<Platform> getByIds(@NotNull Set<Long> platformIds) {
        return platformRepository.findAllByIdIn(platformIds);
    }

    @NotNull
    @Transactional
    public Platform create(@NotNull Platform platform) {
        this.checkForUniqueNameOnCreate(StringUtils.trim(platform.getName()));
        Platform savedPlatform = platformRepository.save(platform);
        log.info("Created platform: '{}' with id: {}", savedPlatform.getName(), savedPlatform.getId());
        return savedPlatform;
    }

    @NotNull
    @Transactional
    public Platform update(@NotNull Platform platform) {
        this.checkForUniqueNameOnUpdate(platform.getName(), platform.getId());

        final Platform updatedPlatform = this.getById(platform.getId());
        updatedPlatform.setName(StringUtils.trim(platform.getName()));

        Platform savedPlatform = platformRepository.save(updatedPlatform);
        log.info("Updated platform id: {} to name: '{}'", savedPlatform.getId(), savedPlatform.getName());
        return savedPlatform;
    }

    private void checkForUniqueNameOnCreate(@NotNull String name) {
        platformRepository.findByName(name)
                .ifPresent(existing -> {
                    throw new ClientRequestException(ExceptionMessage.EXCEPTION_DUPLICATE_PLATFORM, List.of(name));
                });
    }

    private void checkForUniqueNameOnUpdate(@NotNull String name, @NotNull Long platformId) {
        platformRepository.findByName(name)
                .filter(existing -> !existing.getId().equals(platformId))
                .ifPresent(existing -> {
                    throw new ClientRequestException(ExceptionMessage.EXCEPTION_DUPLICATE_PLATFORM, List.of(name));
                });
    }
}
