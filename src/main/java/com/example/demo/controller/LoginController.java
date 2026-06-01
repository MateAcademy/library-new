package com.example.demo.controller;

import com.example.demo.model.Library;
import com.example.demo.repository.library.LibraryRepository;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginController {

    final LibraryRepository libraryRepository;

//    @PostMapping("login")
//    public String login(@RequestParam("email") String email, @RequestParam("password") String password,  Model model, HttpSession session) {
//        log.info("Login attempt - Email: '{}' (length: {}), Password: '{}' (length: {})",
//                 email, email.length(), password, password.length());
//        Optional<Person> userOpt = personRepository.findByEmailAndPassword(email, password);
//
//        if (userOpt.isPresent()) {
//            Person person = userOpt.get();
//
//            final Set<Library> personLibraries = person.getLibraries();
//
//            if (personLibraries == null || personLibraries.isEmpty()) {
//                log.warn("Login attempt by user {} with no library access", email);
//                model.addAttribute("error", "You do not have access to any library.");
//                return "index";
//            }
//
//            session.setAttribute("person", person);
//            session.setAttribute("email", email);
//            session.setAttribute("personLibraryIds", person.getLibraries()
//                .stream()
//                .map(Library::getId)
//                .collect(Collectors.toSet()));
//
//            final List<Library> libraries = libraryRepository.findAll();
//            model.addAttribute("libraries", libraries);
//
//            log.info("Person with email {} LOG IN to site", email);
//            return "admin/choose-library";
//        } else {
//            log.warn("Failed login attempt for email: {}", email);
//            model.addAttribute("error", "Invalid email or password");
//            return "index";
//        }
//    }

    @GetMapping("logout")
    public String logout(HttpSession session) {
        final String email = (String) session.getAttribute("email");
        log.info("Person with email {} LOG OUT from site", email);
        session.invalidate();
        return "redirect:/";
    }

    @GetMapping("admin/choose-library")
    public String returnToChooseLibrary(Model model) {
        List<Library> libraries = libraryRepository.findAll();
        model.addAttribute("libraries", libraries);
        return "/admin/choose-library";
    }

    @GetMapping("admin/main-page")
    @SuppressWarnings("SpringMVCViewInspection")
    public String chooseLibrary(HttpSession session) {
        final Long libraryId = (Long) session.getAttribute("libraryId");

        if (libraryId == null) {
            return "redirect:/admin/choose-library";
        }

        return "library-%s/main-admin-page".formatted(libraryId);
    }

}

