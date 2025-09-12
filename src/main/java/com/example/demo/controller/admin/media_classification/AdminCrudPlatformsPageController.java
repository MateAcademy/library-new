package com.example.demo.controller.admin.media_classification;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class AdminCrudPlatformsPageController {

    @GetMapping("crud-platforms")
    public String crudPlatformsPage() {
        return "admin/media_classification/crud-platforms";
    }

}