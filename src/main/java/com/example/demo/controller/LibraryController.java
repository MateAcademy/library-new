package com.example.demo.controller;

import com.example.demo.models.Library;
import com.example.demo.service.LibraryService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping()
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

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

//    @GetMapping("choose-library")
//    public String chooseLibrary(Model model) {

//        return "admin/choose-library";
//    }

    // возвращаемся на главную страницу своей библиотеки
//    @GetMapping("come-back/select-library-again")
//    public String mainAdminPage(Model model) {
//                List<Library> libraries = libraryService.findAll();
//        model.addAttribute("libraries", libraries);
//        return "/admin/choose-library";
//    }
}