package com.example.demo.service.media_classification;

import com.example.demo.dto.media_classification.SaveDataLeafRequest;
import com.example.demo.dto.media_classification.SaveDataLeafResponse;
import com.example.demo.model.media_classification.*;
import com.example.demo.repository.media_classification.OperatingSystemVersionRepository;
import com.example.demo.repository.media_classification.PlatformOperatingSystemRepository;
import com.example.demo.repository.media_classification.PlatformRepository;
import com.example.demo.repository.media_classification.SaveDataLeafRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaveDataLeafService {

    private final SaveDataLeafRepository saveDataLeafRepository;
    private final PlatformRepository platformRepository;
    private final PlatformOperatingSystemRepository posRepository;
    private final OperatingSystemVersionRepository versionRepository;

    @Transactional
    public void saveSelection(SaveDataLeafRequest request) {
        SaveDataLeaf leaf = saveDataLeafRepository
                .findByDocTypeAndDocId(request.docType(), request.docId())
                .orElseGet(() -> {
                    SaveDataLeaf newLeaf = new SaveDataLeaf();
                    newLeaf.setDocType(request.docType());
                    newLeaf.setDocId(request.docId());
                    return newLeaf;
                });

        // Очищаем старые выборки
        leaf.getPlatformSelections().clear();
        leaf.getPlatformOperatingSystemSelections().clear();
        leaf.getVersionSelections().clear();

        // Добавляем новые платформы
        platformRepository.findAllById(request.platformIds())
                .forEach(p -> leaf.getPlatformSelections().add(new SelectionPlatform(leaf, p)));

        // POS
        posRepository.findAllById(request.posIds())
                .forEach(pos -> leaf.getPlatformOperatingSystemSelections().add(new SelectionPlatformOperatingSystem(leaf, pos)));

        // Версии
        versionRepository.findAllById(request.versionIds())
                .forEach(ver -> leaf.getVersionSelections().add(new SelectionVersion(leaf, ver)));

        saveDataLeafRepository.save(leaf);
    }

    @Transactional(readOnly = true)
    public SaveDataLeafResponse getSelection(DocumentLeafType docType, Long docId) {
        return saveDataLeafRepository.findByDocTypeAndDocId(docType, docId)
                .map(leaf -> new SaveDataLeafResponse(
                        leaf.getDocType(),
                        leaf.getDocId(),
                        leaf.getPlatformSelections().stream().map(sp -> sp.getPlatform().getId()).collect(Collectors.toSet()),
                        leaf.getPlatformOperatingSystemSelections().stream().map(spo -> spo.getPlatformOperatingSystem().getId()).collect(Collectors.toSet()),
                        leaf.getVersionSelections().stream().map(sv -> sv.getOperatingSystemVersion().getId()).collect(Collectors.toSet())
                ))
                .orElse(new SaveDataLeafResponse(docType, docId, Set.of(), Set.of(), Set.of()));
    }
}