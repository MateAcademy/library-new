package com.example.demo.controller.admin.media_classification;

import com.example.demo.dto.media_classification.OperatingSystemVersionRequest;
import com.example.demo.dto.media_classification.OperatingSystemVersionResponse;
import com.example.demo.dto.media_classification.PageResponse;
import com.example.demo.dto.sort.IdNameSortFieldEnum;
import com.example.demo.mapper.media_classification.OperatingSystemVersionMapper;
import com.example.demo.model.media_classification.OperatingSystemVersion;
import com.example.demo.service.media_classification.OperatingSystemVersionService;
import com.example.demo.utils.PageRequestUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
//import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RequestMapping("admin/operating-system-version")
@RestController
@Tag(name = "Admin Operating System Version")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminOperatingSystemVersionController {

    private final OperatingSystemVersionService operatingSystemVersionService;
    private final OperatingSystemVersionMapper operatingSystemVersionMapper;

    @GetMapping
    public PageResponse<OperatingSystemVersionResponse> getOperatingSystemVersionByPage(
            @RequestParam(value = "platformOperatingSystemId") Long platformOperatingSystemId,
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "sortField", defaultValue = "id") String sortField,
            @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
            @RequestParam(value = "previewIds", required = false) Set<Long> previewIds) {
        final IdNameSortFieldEnum sortFieldEnum = IdNameSortFieldEnum.valueOf(sortField.toUpperCase());
        final PageRequest pageRequest = PageRequestUtils.buildPageRequestForPg(page, pageSize, sortFieldEnum, sortDirection);

        final Page<OperatingSystemVersion> operatingSystemsVersionByPage = operatingSystemVersionService.getPageByPlatformOSId(platformOperatingSystemId, pageRequest);
        final List<OperatingSystemVersionResponse> responseList = operatingSystemsVersionByPage.stream()
                .map(operatingSystemVersionMapper::mapToOperatingSystemVersionResponse)
                .toList();

        List<OperatingSystemVersionResponse> previewList = List.of();
        if (previewIds != null && !previewIds.isEmpty()) {
            previewList = operatingSystemVersionService.getByIds(previewIds).stream()
                    .map(operatingSystemVersionMapper::mapToOperatingSystemVersionResponse)
                    .toList();
        }

        return new PageResponse<>(page, operatingSystemsVersionByPage.getTotalElements(), pageSize, sortFieldEnum.getField(), sortDirection, previewList, responseList);
    }

    @GetMapping("{operatingSystemVersionId}")
    public OperatingSystemVersionResponse getOperatingSystemById(@PathVariable Long operatingSystemVersionId) {
        final OperatingSystemVersion operatingSystem = operatingSystemVersionService.getById(operatingSystemVersionId);
        return operatingSystemVersionMapper.mapToOperatingSystemVersionResponse(operatingSystem);
    }

    @PostMapping
    public OperatingSystemVersionResponse createOperatingSystemVersion(@RequestBody @Valid OperatingSystemVersionRequest request) {
        final OperatingSystemVersion operatingSystemVersion = operatingSystemVersionService.create(request.platformOperatingSystemId(), request.name());
        return operatingSystemVersionMapper.mapToOperatingSystemVersionResponse(operatingSystemVersion);
    }

    @PutMapping("{id}")
    public OperatingSystemVersionResponse updateOperatingSystemVersion(@PathVariable Long id,
                                                                       @RequestBody @Valid OperatingSystemVersionRequest request) {
        final OperatingSystemVersion operatingSystemVersion  = operatingSystemVersionService.update(id, request.name());
        return operatingSystemVersionMapper.mapToOperatingSystemVersionResponse(operatingSystemVersion);
    }
}