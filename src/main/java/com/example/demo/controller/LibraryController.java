package com.example.demo.controller;

import com.example.demo.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping("library")
@RequiredArgsConstructor
public class LibraryController {

    @PostMapping("select-library")
    public String selectLibrary(@RequestParam Long libraryId, HttpSession session, RedirectAttributes redirectAttributes) {
        Set<Long> allowedLibraryIds = (Set<Long>) session.getAttribute("personLibraryIds");
        String email = (String) session.getAttribute("email");

        if (allowedLibraryIds == null || !allowedLibraryIds.contains(libraryId)) {
            redirectAttributes.addFlashAttribute("errorLibraryId", libraryId);
            redirectAttributes.addFlashAttribute("error", "У вас нет доступа к выбранной библиотеке.");
            return "redirect:choose-library";
        }

        session.setAttribute("libraryId", libraryId);
        log.info("Person with email {} go to the Library: {}", email, libraryId);
        return "library-" + libraryId + "/main-admin-page";
    }

    @GetMapping("choose-library")
    public String showLibrarySelectionPage() {
        return "admin/choose-library";
    }
}