package com.example.demo.controller.admin.media_classification;

import com.example.demo.model.media_classification.DocumentLeafType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin")
public class AdminTestCasesController {

    @GetMapping("test-cases")
    public String testCasePage(@RequestParam DocumentLeafType docType,
                               @RequestParam Long docId,
                               Model model) {
        model.addAttribute("docType", docType);
        model.addAttribute("docId", docId);
        return "admin/media_classification/test-cases";
    }

}
