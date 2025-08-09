package com.example.demo.service.media_classification;

import com.example.demo.exception.ClientRequestException;
import com.example.demo.exception.ExceptionMessage;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.media_classification.OperatingSystemVersion;
import com.example.demo.model.media_classification.PlatformOperatingSystem;
import com.example.demo.repository.media_classification.OperatingSystemVersionRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class OperatingSystemVersionService {

    private final PlatformOperatingSystemService platformOperatingSystemService;
    private final OperatingSystemVersionRepository operatingSystemVersionRepository;

    @NotNull
    public Page<OperatingSystemVersion> getPageByPlatformOSId(@NotNull Long platformOperatingSystemId,
                                                              @NotNull Pageable pageable) {
        return operatingSystemVersionRepository.findAllByPlatformOperatingSystem_Id(platformOperatingSystemId, pageable);
    }

    @NotNull
    public Stream<OperatingSystemVersion> getAll() {
        return operatingSystemVersionRepository.findAllBy();
    }

    @NotNull
    public List<OperatingSystemVersion> getAllByPlatformOSId(@NotNull Long platformOperatingSystemId) {
        return operatingSystemVersionRepository.findAllByPlatformOperatingSystem_Id(platformOperatingSystemId);
    }

    @NotNull
    public Set<OperatingSystemVersion> getByIds(@NotNull Set<Long> ids) {
        return operatingSystemVersionRepository.findAllByIdIn(ids);
    }

    @NotNull
    @Transactional
    public List<Long> getIdsByPlatformOSIds(@NotNull Set<Long> platformOperatingSystemIds) {
        if (platformOperatingSystemIds.isEmpty()) {
            return List.of();
        }

        Stream<OperatingSystemVersion> allByPlatformOperatingSystemIds = operatingSystemVersionRepository.findAllByPlatformOperatingSystemIdIn(platformOperatingSystemIds);
        return allByPlatformOperatingSystemIds.map(OperatingSystemVersion::getId).toList();
    }

    @NotNull
    public OperatingSystemVersion getById(@NotNull Long operatingSystemVersionId) {
        return operatingSystemVersionRepository.findById(operatingSystemVersionId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.EXCEPTION_OS_VERSION_NOT_FOUND_BY_ID, List.of(operatingSystemVersionId)));
    }

    @NotNull
    @Transactional
    public OperatingSystemVersion create(@NotNull Long platformOperatingSystemId, @NotNull String name) {
        final PlatformOperatingSystem platformOperatingSystem = platformOperatingSystemService.getById(platformOperatingSystemId);
        this.checkForUniqueNameOnCreate(platformOperatingSystemId, name);

        final OperatingSystemVersion operatingSystemVersion = new OperatingSystemVersion(platformOperatingSystem, StringUtils.trim(name));

        return operatingSystemVersionRepository.save(operatingSystemVersion);
    }

    @NotNull
    @Transactional
    public OperatingSystemVersion update(@NotNull Long id, @NotNull String name) {
        final OperatingSystemVersion operatingSystemVersion = this.getById(id);

        if (!operatingSystemVersion.getName().equalsIgnoreCase(name)) {
            Long platformOperatingSystemId = operatingSystemVersion.getPlatformOperatingSystem().getId();
            this.checkForUniqueNameOnUpdate(platformOperatingSystemId, name, id);
        }

        operatingSystemVersion.setName(StringUtils.trim(name));
        return operatingSystemVersionRepository.save(operatingSystemVersion);
    }

    private void checkForUniqueNameOnCreate(@NotNull Long platformOperatingSystemId, @NotNull String name) {
        operatingSystemVersionRepository.findByPlatformOperatingSystemIdAndName(platformOperatingSystemId, name)
                .ifPresent(existing -> {
                    throw new ClientRequestException(
                            ExceptionMessage.EXCEPTION_DUPLICATE_OPERATING_SYSTEM_VERSION,
                            List.of(platformOperatingSystemId, name)
                    );
                });
    }

    private void checkForUniqueNameOnUpdate(@NotNull Long platformOperatingSystemId, @NotNull String name, @NotNull Long osvId) {
        operatingSystemVersionRepository.findByPlatformOperatingSystemIdAndName(platformOperatingSystemId, name)
                .filter(existing -> !existing.getId().equals(osvId))
                .ifPresent(existing -> {
                    throw new ClientRequestException(
                            ExceptionMessage.EXCEPTION_DUPLICATE_OPERATING_SYSTEM_VERSION,
                            List.of(platformOperatingSystemId, name)
                    );
                });
    }
}