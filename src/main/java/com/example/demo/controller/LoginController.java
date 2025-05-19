package com.example.demo.controller;

import com.example.demo.models.Library;
import com.example.demo.models.Person;
import com.example.demo.repository.library.LibraryRepository;
import com.example.demo.repository.person.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final PersonRepository personRepository;
    private final LibraryRepository libraryRepository;

    @GetMapping
    public String main() {
        return "index";
    }

    @PostMapping("login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,  Model model, HttpSession session) {
        Optional<Person> userOpt = personRepository.findByEmailAndPassword(email, password);

        if (userOpt.isPresent()) {
            Person person = userOpt.get();

            Set<Library> personLibraries = person.getLibraries();

            if (personLibraries == null || personLibraries.isEmpty()) {
                model.addAttribute("error", "У вас нет доступа ни к одной библиотеке.");
                return "index"; // или вернись на login
            }

            session.setAttribute("person", person);
            session.setAttribute("email", email);
            session.setAttribute("personLibraryIds", person.getLibraries()
                .stream()
                .map(Library::getLibraryId)
                .collect(Collectors.toSet()));

            List<Library> libraries = libraryRepository.findAll();
            model.addAttribute("libraries", libraries);

            log.info("Person with email {} LOG IN", email);
            return "admin/choose-library";
        } else {
            model.addAttribute("error", "Неверный email или пароль");
            return "index";
        }
    }

    @GetMapping("admin/main-person-page")
    public String mainAdminPage(HttpSession session) {
        Long libraryId = (Long) session.getAttribute("libraryId");
        if (libraryId == 1) {
            return "library-1/main-admin-page";
        } else if (libraryId == 2) {
            return "library-2/main-admin-page";
        } else {
            return "library-3/main-admin-page";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // очищает все атрибуты сессии
        return "redirect:/";
    }
}

