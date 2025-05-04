package com.example.demo.controller.admin;

import com.example.demo.dto.PersonResponse;
import com.example.demo.models.Person;
import com.example.demo.service.BookCopyService;
import com.example.demo.service.PeopleService;
import com.example.demo.utils.validators.PersonValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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
                        Model model) {
        Page<PersonResponse> peoplePage = peopleService.getPeoplePage(page, size);
        model.addAttribute("people", peoplePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", peoplePage.hasNext());
        return "/people/all-people";
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id, Model model) {
        Optional<Person> person = peopleService.getPersonById(id);

        if (person.isEmpty()) {
            return "/admin/person-not-found";
        }

        Person personById = person.get();

        model.addAttribute("person", personById);
        model.addAttribute("books", personById.getBooks());

        return "/people/person-details";
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
    public String edit(@PathVariable Long id, Model model) {
        Optional<Person> personById = peopleService.getPersonById(id);
        if (personById.isPresent()) {
            model.addAttribute("person", personById.get());
            return "/people/edit-person";
        } else {
            return "/admin/person-not-found";
        }
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute @Valid Person person,
                         BindingResult bindingResult,
                         @PathVariable Integer id) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/edit-person";
        }

        peopleService.update(id, person);
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
