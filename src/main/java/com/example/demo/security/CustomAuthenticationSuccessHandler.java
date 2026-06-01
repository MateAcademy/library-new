package com.example.demo.security;

import com.example.demo.model.Library;
import com.example.demo.model.Person;
import com.example.demo.repository.library.LibraryRepository;
import com.example.demo.repository.person.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    final PersonRepository personRepository;
    final LibraryRepository libraryRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        final String email = authentication.getName();

        final Person person = personRepository.findByEmail(email)
                .flatMap(p -> personRepository.findByIdWithBooksAndLibraries(p.getId()))
                .orElseThrow(() -> new IllegalStateException("Пользователь не найден после аутентификации: " + email));

        final Set<Library> personLibraries = person.getLibraries();

        if (personLibraries == null || personLibraries.isEmpty()) {
            log.warn("Пользователь {} вошёл но не имеет доступа ни к одной библиотеке", email);
            response.sendRedirect(request.getContextPath() + "/?error=no-library");
            return;
        }

        final HttpSession session = request.getSession();
        session.setAttribute("person", person);
        session.setAttribute("email", email);
        session.setAttribute("personLibraryIds", personLibraries.stream()
                .map(Library::getId)
                .collect(Collectors.toSet()));

        log.info("Пользователь {} успешно вошёл в систему", email);
        response.sendRedirect(request.getContextPath() + "/admin/choose-library");
    }
}
