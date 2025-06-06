package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Slf4j
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class LibraryController {

    @PostMapping("admin/main-page")
    public String selectLibrary(@RequestParam Long libraryId, HttpSession session, RedirectAttributes redirectAttributes) {
        Set<Long> allowedLibraryIds = (Set<Long>) session.getAttribute("personLibraryIds");

        if (allowedLibraryIds == null || !allowedLibraryIds.contains(libraryId)) {
            redirectAttributes.addFlashAttribute("errorLibraryId", libraryId);
            redirectAttributes.addFlashAttribute("error", "У вас нет доступа к выбранной библиотеке.");
            return "redirect:choose-library";
        }

        String email = (String) session.getAttribute("email");

        session.setAttribute("libraryId", libraryId);
        log.info("Person with email {} go to the Library: {}", email, libraryId);
        return "library-" + libraryId + "/main-admin-page";
    }
}