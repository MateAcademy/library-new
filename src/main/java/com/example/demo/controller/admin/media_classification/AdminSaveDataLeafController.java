package com.example.demo.controller.admin.media_classification;

import com.example.demo.dto.media_classification.SaveDataLeafRequest;
import com.example.demo.dto.media_classification.SaveDataLeafResponse;
import com.example.demo.model.media_classification.DocumentLeafType;
import com.example.demo.service.media_classification.SaveDataLeafService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/test-cases")
@RequiredArgsConstructor
public class AdminSaveDataLeafController {

    private final SaveDataLeafService saveDataLeafService;

    @PostMapping("/apply")
    public ResponseEntity<Void> applySelection(@RequestBody SaveDataLeafRequest request) {
        saveDataLeafService.saveSelection(request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/selection")
    public SaveDataLeafResponse getSelection(@RequestParam DocumentLeafType docType,
                                             @RequestParam Long docId) {
        return saveDataLeafService.getSelection(docType, docId);
    }
}