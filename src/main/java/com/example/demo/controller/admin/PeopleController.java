package com.example.demo.controller.admin;

import com.example.demo.dto.PersonResponse;
import com.example.demo.models.Person;
import com.example.demo.service.PeopleService;
import com.example.demo.utils.validators.PersonValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin/people")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "PeopleController", description = "API for managing person")
public class PeopleController {

    final PeopleService peopleService;
    final PersonValidator personValidator;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        HttpSession session,
                        Model model) {
        Long libraryId = (Long) session.getAttribute("libraryId");
        Page<PersonResponse> peoplePage = peopleService.getPeoplePageByLibrary(libraryId, page, size);
        model.addAttribute("people", peoplePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", peoplePage.hasNext());

        if (libraryId == 1) {
            return "library-1/all-people";
        } else if (libraryId == 2) {
            return "library-2/all-people";
        } else {
            return "library-3/all-people";
        }
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id,
                       HttpSession session,
                       Model model) {
        Long libraryId = (Long) session.getAttribute("libraryId");
        Optional<Person> person = peopleService.getPersonById(id);

        if (person.isEmpty()) {
            return "/admin/person-not-found";
        }

        Person personById = person.get();

        model.addAttribute("person", personById);
        model.addAttribute("books", personById.getBooks());

        if (libraryId == 1L) {
            return "library-1/person-details";
        } else if (libraryId == 2L) {
            return "library-2/person-details";
        } else {
            return "library-3/person-details";
        }
    }

    @GetMapping("new")
    public String newPerson(@ModelAttribute Person person) {
        return "people/new-person";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult) {  // @RequestParam(required = false) String name
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/new-person";
        }

        peopleService.save(person);
        return "/admin/success-create-person-page";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, HttpSession session, Model model) {
        Optional<Person> personById = peopleService.getPersonById(id);
        if (personById.isPresent()) {
            model.addAttribute("person", personById.get());

            Long libraryId = (Long) session.getAttribute("libraryId");
            log.info("Edit Person with email: {} and go to the LibraryId: {}", personById.get().getEmail(), libraryId);
            if (libraryId == 1L) {
                return "library-1/edit-person";
            } else if (libraryId == 2L) {
                return "library-2/edit-person";
            } else {
                return "library-3/edit-person";
            }
        } else {
            return "/admin/person-not-found";
        }
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable Long id,
                         HttpSession session) {


        personValidator.validate(person, bindingResult);
        Long libraryId = (Long) session.getAttribute("libraryId");

        if (bindingResult.hasErrors()) {
            if (libraryId == 1L) {
                return "library-1/edit-person";
            } else if (libraryId == 2L) {
                return "library-2/edit-person";
            } else {
                return "library-3/edit-person";
            }
        }

        peopleService.update(person);
        return "/admin/success-update-person-page";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        peopleService.delete(id);
        return "/admin/success-delete-person-page";
    }

    @GetMapping("insert1000People")
    public String insert1000People() {
        peopleService.insert1000People();
        return "redirect:/admin/people";
    }

    @GetMapping("butch_insert1000People")
    public String butchInsert1000People() {
        peopleService.butchInsert1000People();
        return "redirect:/admin/people";
    }
}
