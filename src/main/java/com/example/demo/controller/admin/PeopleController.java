package com.example.demo.controller.admin;

import com.example.demo.dto.PersonResponse;
import com.example.demo.models.Book;
import com.example.demo.models.Person;
import com.example.demo.service.BookService;
import com.example.demo.service.PeopleService;
import com.example.demo.utils.validators.PersonValidator;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/people")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Tag(name = "PeopleController", description = "API for managing person")
public class PeopleController {

    final PeopleService peopleService;
    final BookService bookService;
    final PersonValidator personValidator;

//    @GetMapping
//    public String index(Model model) {
//        List<PersonResponse> allPeople = peopleService.getAllPeople();
//        model.addAttribute("people", allPeople);
//        return "people/all-people";
//    }

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

    @GetMapping("{id}")   // ("{id}") позволяет делать запрос с переменными в URL и это число поместится в аргументы метода
    public String show(@PathVariable Long id, Model model) {
        Optional<Person> person = peopleService.getPersonById(id);

        if (person.isPresent()) {
            model.addAttribute("person", person.get());

            List<Book> books = bookService.getBooksByPersonId(id); // этот метод должен быть у bookService
            model.addAttribute("books", books);
            return "/people/person-details";
        } else {
            return "/admin/person-not-found";
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
    public String butch_insert1000People() {
        peopleService.butchInsert1000People();
        return "redirect:/admin/people";
    }
}
