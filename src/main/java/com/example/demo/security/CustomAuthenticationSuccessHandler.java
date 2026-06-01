package com.example.demo.security;

import com.example.demo.model.Library;
import com.example.demo.model.Person;
import com.example.demo.repository.person.PersonRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
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

    @Override
    public void onAuthenticationSuccess(@NonNull HttpServletRequest req,
                                        @NonNull HttpServletResponse resp,
                                        Authentication authentication) throws IOException {
        final String email = authentication.getName();

        final Person person = personRepository.findByEmail(email)
                .flatMap(p -> personRepository.findByIdWithBooksAndLibraries(p.getId()))
                .orElseThrow(() -> new IllegalStateException("User not found after authentication: " + email));

        final Set<Library> personLibraries = person.getLibraries();

        if (personLibraries == null || personLibraries.isEmpty()) {
            log.warn("User {} logged in but has no access to any library", email);
            resp.sendRedirect(req.getContextPath() + "/?error=no-library");
            return;
        }

        final HttpSession session = req.getSession();
        session.setAttribute("person", person);
        session.setAttribute("email", email);
        session.setAttribute("personLibraryIds", personLibraries.stream()
                .map(Library::getId)
                .collect(Collectors.toSet()));

        log.info("User {} successfully logged in", email);
        resp.sendRedirect(req.getContextPath() + "/admin/choose-library");
    }
}
