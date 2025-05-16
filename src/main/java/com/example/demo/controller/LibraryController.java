package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;

@Controller
@RequiredArgsConstructor
public class LibraryController {

    @PostMapping("/select-library")
    public String selectLibrary(@RequestParam Long libraryId, HttpSession session, RedirectAttributes redirectAttributes) {
        Set<Long> allowedLibraryIds = (Set<Long>) session.getAttribute("personLibraryIds");

        if (allowedLibraryIds == null || !allowedLibraryIds.contains(libraryId)) {
            redirectAttributes.addFlashAttribute("errorLibraryId", libraryId);
            redirectAttributes.addFlashAttribute("errorMessage", "У вас нет доступа к выбранной библиотеке.");
            return "redirect:/choose-library";
        }

        // Сохраняем выбранную библиотеку в сессию
        session.setAttribute("selectedLibraryId", libraryId);
        return "redirect:/library/" + libraryId + "/main"; // куда хочешь редиректить
    }

}
