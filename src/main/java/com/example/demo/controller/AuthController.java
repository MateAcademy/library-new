package com.example.demo.controller;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    @GetMapping
    public String main() {
        return "index";
    }

    @PostMapping("login")
    public String login(
        @RequestParam("email") String email,
        @RequestParam("password") String password,
        Model model,
        HttpSession session
    ) {
        // "admin@example.com".equals(email) && "password123".equals(password)
        // Здесь можно вставить свою логику аутентификации (например, через UserService)
        if ("ayabarto@gmail.com".equals(email) && "123".equals(password)) {
            // Успешный вход — редирект на главную

            session.setAttribute("username", "Админ");
            model.addAttribute("username", session.getAttribute("username"));
            return "admin/main-person-page";
        } else {
            // Ошибка входа — возвращаем на страницу логина с сообщением
            model.addAttribute("error", "Неверный email или пароль");
            return "index"; // login.html
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

