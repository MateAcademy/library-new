package com.example.demo.controller.admin.media_classification;

import com.example.demo.dto.media_classification.PageResponse;
import com.example.demo.dto.media_classification.PlatformRequest;
import com.example.demo.dto.media_classification.PlatformResponse;
import com.example.demo.dto.sort.PlatformSortFieldEnum;
import com.example.demo.mapper.media_classification.PlatformMapper;
import com.example.demo.model.media_classification.Platform;
import com.example.demo.service.media_classification.PlatformService;
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

@RequestMapping("admin/platform")
@RestController
@Tag(name = "Admin Platform")
//@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequiredArgsConstructor
public class AdminPlatformController {

    private final PlatformService platformService;
    private final PlatformMapper platformMapper;

    @GetMapping
    public PageResponse<PlatformResponse> getPlatformsByPage(@RequestParam(value = "page", defaultValue = "1") Integer page,
                                                             @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
                                                             @RequestParam(value = "sortField", defaultValue = "name") String sortField,
                                                             @RequestParam(value = "sortDirection", defaultValue = "asc") String sortDirection,
                                                             @RequestParam(value = "previewIds", required = false) Set<Long> previewIds) {
        final PlatformSortFieldEnum sortFieldEnum = PlatformSortFieldEnum.valueOf(sortField.toUpperCase());
        final PageRequest pageRequest = PageRequestUtils.buildPageRequestForPg(page, pageSize, sortFieldEnum, sortDirection);

        final Page<Platform> platformsByPage = platformService.getByPage(pageRequest);
        final List<PlatformResponse> responseList = platformsByPage.stream()
                .map(platformMapper::mapToPlatformResponse)
                .toList();

        List<PlatformResponse> previewList = List.of();
        if (previewIds != null && !previewIds.isEmpty()) {
            previewList = platformService.getByIds(previewIds).stream()
                    .map(platformMapper::mapToPlatformResponse)
                    .toList();
        }

        return new PageResponse<>(page, platformsByPage.getTotalElements(), pageSize, sortFieldEnum.getField(), sortDirection, previewList, responseList);
    }

    @GetMapping("{platformId}")
    public PlatformResponse getPlatformById(@PathVariable Long platformId) {
        final Platform getPlatformById = platformService.getById(platformId);
        return platformMapper.mapToPlatformResponse(getPlatformById);
    }

    @PostMapping
    public PlatformResponse createPlatform(@RequestBody @Valid PlatformRequest request) {
        final Platform platform = platformMapper.mapToPlatform(null, request);
        return platformMapper.mapToPlatformResponse(platformService.create(platform));
    }

    @PutMapping("{id}")
    public PlatformResponse updatePlatform(@PathVariable Long id,
                                           @RequestBody @Valid PlatformRequest request) {
        final Platform platform = platformMapper.mapToPlatform(id, request);
        return platformMapper.mapToPlatformResponse(platformService.update(platform));
    }
}
