package com.example.demo.service.media_classification;

import com.example.demo.dto.media_classification.SaveDataLeafDTO;
import com.example.demo.model.media_classification.*;
import com.example.demo.repository.media_classification.*;
import jakarta.persistence.EntityManager;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class SaveDataLeafService {

    private final SaveDataLeafRepository saveDataLeafRepository;
    private final SelectionPlatformRepository selectionPlatformRepository;
    private final SelectionPlatformOperatingSystemRepository selectionPosRepository;
    private final SelectionVersionRepository selectionVersionRepository;
    private final EntityManager entityManager;

    @Transactional
    public SaveDataLeafDTO replaceSelectionForDocument(SaveDataLeafDTO request) {
        final DocumentLeafType docType = request.docType();
        final Long docId = request.docId();

        final Set<Long> platformIds = request.platformIds() == null ? Set.of() : Set.copyOf(request.platformIds());
        final Set<Long> posIds = request.posIds() == null ? Set.of() : Set.copyOf(request.posIds());
        final Set<Long> versionIds = request.versionIds() == null ? Set.of() : Set.copyOf(request.versionIds());

        final SaveDataLeaf leaf = saveDataLeafRepository.findByDocTypeAndDocId(docType, docId)
            .orElseGet(() -> saveDataLeafRepository.save(
                new SaveDataLeaf(null, docType, docId, Set.of(), Set.of(), Set.of())
            ));

        final Long leafId = leaf.getId();

        selectionVersionRepository.deleteByLeafId(leafId);
        selectionPosRepository.deleteByLeafId(leafId);
        selectionPlatformRepository.deleteByLeafId(leafId);

        Optional.of(platformIds)
            .filter(ids -> !ids.isEmpty())
            .map(ids -> ids.stream()
                .map(pid -> new SelectionPlatform(leaf, entityManager.getReference(Platform.class, pid)))
                .toList()
            ).ifPresent(selectionPlatformRepository::saveAll);

        Optional.of(posIds)
            .filter(ids -> !ids.isEmpty())
            .map(ids -> ids.stream()
                .map(pid -> new SelectionPlatformOperatingSystem(leaf, entityManager.getReference(PlatformOperatingSystem.class, pid)))
                .toList()).ifPresent(selectionPosRepository::saveAll);

        Optional.of(versionIds)
            .filter(ids -> !ids.isEmpty())
            .map(ids -> ids.stream()
                .map(pid -> new SelectionVersion(leaf, entityManager.getReference(OperatingSystemVersion.class, pid)))
                .toList()).ifPresent(selectionVersionRepository::saveAll);

        log.info("Replaced selection for document type: {}, docId: {} - platforms: {}, pos: {}, versions: {}",
                docType, docId, platformIds.size(), posIds.size(), versionIds.size());

        return new SaveDataLeafDTO(
            leaf.getDocType(),
            leaf.getDocId(),
            platformIds,
            posIds,
            versionIds
        );
    }

    @Transactional(readOnly = true)
    public SaveDataLeafDTO getSelection(DocumentLeafType docType, Long docId) {
        return saveDataLeafRepository.findByDocTypeAndDocId(docType, docId)
            .map(leaf -> new SaveDataLeafDTO(
                leaf.getDocType(),
                leaf.getDocId(),
                leaf.getPlatformSelections().stream()
                    .map(sp -> sp.getPlatform().getId())
                    .collect(Collectors.toSet()),
                leaf.getPlatformOperatingSystemSelections().stream()
                    .map(spo -> spo.getPlatformOperatingSystem().getId())
                    .collect(Collectors.toSet()),
                leaf.getVersionSelections().stream()
                    .map(sv -> sv.getOperatingSystemVersion().getId())
                    .collect(Collectors.toSet())
            ))
            .orElse(new SaveDataLeafDTO(docType, docId, Set.of(), Set.of(), Set.of()));
    }
}


//    @Transactional
//    public void replaceSelectionForDocument(SaveDataLeafDTO request) {
//        final DocumentLeafType docType = request.docType();
//        final Long docId = request.docId();
//
//        final Set<Long> platformIds = request.platformIds() == null ? Set.of() : Set.copyOf(request.platformIds());
//        final Set<Long> posIds = request.posIds() == null ? Set.of() : Set.copyOf(request.posIds());
//        final Set<Long> versionIds = request.versionIds() == null ? Set.of() : Set.copyOf(request.versionIds());
//
//
//        final SaveDataLeaf leaf = saveDataLeafRepository.findByDocTypeAndDocId(docType, docId)
//            .orElseGet(() -> saveDataLeafRepository.save(new SaveDataLeaf(null, docType, docId, Set.of(), Set.of(), Set.of())));
//
//        final Long leafId = leaf.getId();
//
//        selectionVersionRepository.deleteByLeafId(leafId);
//        selectionPosRepository.deleteByLeafId(leafId);
//        selectionPlatformRepository.deleteByLeafId(leafId);
//
//        if (!platformIds.isEmpty()) {
//            List<SelectionPlatform> entities = platformIds.stream()
//                .map(pid -> new SelectionPlatform(leaf, entityManager.getReference(Platform.class, pid))).toList();
//            selectionPlatformRepository.saveAll(entities);
//        }
//
//        if (!posIds.isEmpty()) {
//            List<SelectionPlatformOperatingSystem> entities = posIds.stream()
//                .map(id -> new SelectionPlatformOperatingSystem(leaf, entityManager.getReference(PlatformOperatingSystem.class, id))).toList();
//            selectionPosRepository.saveAll(entities);
//        }
//
//        if (!versionIds.isEmpty()) {
//            List<SelectionVersion> entities = versionIds.stream()
//                .map(id -> new SelectionVersion(leaf, entityManager.getReference(OperatingSystemVersion.class, id)))
//                .toList();
//            selectionVersionRepository.saveAll(entities);
//        }
//    }