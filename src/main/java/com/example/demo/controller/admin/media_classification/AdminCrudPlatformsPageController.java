package com.example.demo.controller.admin.media_classification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminCrudPlatformsPageController {

    @GetMapping("crud-platforms")
    public String crudPlatformsPage() {
        // вернёт templates/admin/crud-platforms.html
        return "admin/media_classification/crud-platforms";
    }

    @GetMapping("test-cases")
    public String testCasePage() {
        // вернёт templates/admin/crud-platforms.html
        return "admin/media_classification/test-cases";
    }
}