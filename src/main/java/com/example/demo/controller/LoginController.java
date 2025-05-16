package com.example.demo.controller;

import com.example.demo.models.Library;
import com.example.demo.models.Person;
import com.example.demo.repository.library.LibraryRepository;
import com.example.demo.repository.person.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public String login(@RequestParam("email") String email, @RequestParam("password") String password,   Model model, HttpSession session) {
        Optional<Person> userOpt = personRepository.findByEmailAndPassword(email, password);

        if (userOpt.isPresent()) {
            Person person = userOpt.get();

            Set<Library> personLibraries = person.getLibraries();

            if (personLibraries == null || personLibraries.isEmpty()) {
                model.addAttribute("error", "У вас нет доступа ни к одной библиотеке.");
                return "index"; // или вернись на login
            }

            // Сохраняем текущего пользователя в сессию
            session.setAttribute("person", person);
            session.setAttribute("email", person.getEmail());
            session.setAttribute("personLibraryIds", person.getLibraries()
                .stream()
                .map(Library::getLibraryId)
                .collect(Collectors.toSet()));

            List<Library> libraries = libraryRepository.findAll();
            model.addAttribute("libraries", libraries);

            return "admin/choose-library"; // а не редирект
        } else {
            model.addAttribute("error", "Неверный email или пароль");
            return "index";
        }
    }

    @GetMapping("admin/main-person-page")
    public String main_admin_page() {
        return "admin/main-person-page";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // очищает все атрибуты сессии
        return "redirect:/";
    }
}

