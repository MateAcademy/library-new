package com.example.demo.controller.admin.media_classification;

import com.example.demo.dto.media_classification.PageResponse;
import com.example.demo.dto.media_classification.PlatformOperatingSystemRequest;
import com.example.demo.dto.media_classification.PlatformOperatingSystemResponse;
import com.example.demo.dto.sort.IdNameSortFieldEnum;
import com.example.demo.mapper.media_classification.OperatingSystemMapper;
import com.example.demo.model.media_classification.PlatformOperatingSystem;
import com.example.demo.service.media_classification.PlatformOperatingSystemService;
import com.example.demo.utils.PageRequestUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RequestMapping("admin/platform-operating-system")
@RestController
@Tag(name = "Admin Platform - Operating System")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminPlatformOperatingSystemController {

    private final PlatformOperatingSystemService platformOperatingSystemService;
    private final OperatingSystemMapper operatingSystemMapper;

    @GetMapping
    public PageResponse<PlatformOperatingSystemResponse> getPlatformOperatingSystemByPage(@RequestParam(value = "platformId") Long platformId,
                                                                                          @RequestParam(value = "page", defaultValue = "1") Integer page,
                                                                                          @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                                                          @RequestParam(value = "sortField", defaultValue = "id") String sortField,
                                                                                          @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                                                                          @RequestParam(value = "previewIds", required = false) Set<Long> previewIds) {
        final IdNameSortFieldEnum sortFieldEnum = IdNameSortFieldEnum.valueOf(sortField.toUpperCase());
        final PageRequest pageRequest = PageRequestUtils.buildPageRequestForPg(page, pageSize, sortFieldEnum, sortDirection);

        final Page<PlatformOperatingSystem> operatingSystemsByPage = platformOperatingSystemService.getPageByPlatformId(platformId, pageRequest);
        final List<PlatformOperatingSystemResponse> responseList = operatingSystemsByPage.stream()
                .map(operatingSystemMapper::mapToOperatingSystemResponse)
                .toList();

        List<PlatformOperatingSystemResponse> previewList = List.of();
        if (previewIds != null && !previewIds.isEmpty()) {
            previewList = platformOperatingSystemService.getByIds(previewIds).stream()
                    .map(operatingSystemMapper::mapToOperatingSystemResponse)
                    .toList();
        }

        return new PageResponse<>(page, operatingSystemsByPage.getTotalElements(), pageSize, sortFieldEnum.getField(), sortDirection, previewList, responseList);
    }

    @GetMapping("{platformOperatingSystemId}")
    public PlatformOperatingSystemResponse getPlatformOperatingSystemById(@PathVariable Long platformOperatingSystemId) {
        return operatingSystemMapper.mapToOperatingSystemResponse(platformOperatingSystemService.getById(platformOperatingSystemId));
    }

    @PostMapping
    public PlatformOperatingSystemResponse createPlatformOperatingSystem(@RequestBody @Valid PlatformOperatingSystemRequest request) {
        final PlatformOperatingSystem platformOperatingSystem = platformOperatingSystemService.create(request.platformId(), request.name());
        return operatingSystemMapper.mapToOperatingSystemResponse(platformOperatingSystem);
    }

    @PutMapping("{id}")
    public PlatformOperatingSystemResponse updatePlatformOperatingSystem(@PathVariable Long id,
                                                                         @RequestBody @Valid PlatformOperatingSystemRequest request) {
        final PlatformOperatingSystem platformOperatingSystem = platformOperatingSystemService.update(id, request.platformId(), request.name());
        return operatingSystemMapper.mapToOperatingSystemResponse(platformOperatingSystem);
    }
}
