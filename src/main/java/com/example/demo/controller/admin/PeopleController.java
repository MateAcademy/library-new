package com.example.demo.controller.admin;

import com.example.demo.dto.PersonFormDTO;
import com.example.demo.dto.PersonResponseDTO;
import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.errors.PersonNotFoundException;
import com.example.demo.mapper.PersonMapper;
import com.example.demo.models.Person;
import com.example.demo.service.PeopleService;
import com.example.demo.utils.validators.PersonFormDTOValidator;
import com.example.demo.utils.validators.PersonValidator;
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
public class PeopleController {

    final PeopleService peopleService;
    final PersonValidator personValidator;
    final PersonFormDTOValidator personFormDTOValidator;

    @GetMapping
    public String show(@RequestParam(defaultValue = "0") Integer page,
                        @RequestParam(defaultValue = "20") Integer pageSize,
                        HttpSession session,
                        Model model) {
        final Long libraryId = (Long) session.getAttribute("libraryId");
        if (libraryId == null) {
            log.warn("libraryId not found in session PeopleController");
            return "redirect:/admin/choose-library";
        }

        Page<PersonResponseDTO> peoplePage = peopleService.getPeoplePageByLibrary(libraryId, page, pageSize);

        model.addAttribute("people", peoplePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", peoplePage.hasNext());
        model.addAttribute("hasPrevious", peoplePage.hasPrevious());

        log.info("people count: {}", peoplePage.getTotalElements());
        return this.getLibraryView(libraryId, "all-people");
    }

    @GetMapping("{id}")
    public String show(@PathVariable Long id,
                       HttpSession session,
                       Model model) {
        final Long libraryId = (Long) session.getAttribute("libraryId");
        final Optional<Person> person = peopleService.getPersonById(id);

        if (person.isEmpty()) {
            return "/admin/person-not-found";
        }

        final Person personById = person.get();

        final PersonResponseDTO personResponseDTO = PersonMapper.mapToPersonResponseDTO(personById);

        model.addAttribute("person", personResponseDTO);
        model.addAttribute("books", personById.getBookCopy());

        return this.getLibraryView(libraryId, "person-details");
    }

    @GetMapping("new")
    public String newPerson(@ModelAttribute PersonFormDTO personFormDTO) {
        return "people/new-person";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid PersonFormDTO personFormDTO,
                         BindingResult bindingResult, HttpSession session) {  // @RequestParam(required = false) String name
        personFormDTOValidator.validate(personFormDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/people/new-person";
        }

        final Person person = PersonMapper.mapToPersonFromDTO(personFormDTO);

        peopleService.save(person, session);
        return "/admin/success-create-person-page";
    }

    @GetMapping("{id}/edit")
    public String showEditForm(@PathVariable Long id, HttpSession session, Model model) {
        final Person person = peopleService.getPersonById(id).orElseThrow(()->new PersonNotFoundException(id));
        final PersonResponseDTO personResponseDTO = PersonMapper.mapToPersonResponseDTO(person);
        final Long libraryId = (Long) session.getAttribute("libraryId");

        model.addAttribute("person", personResponseDTO);

        return this.getLibraryView(libraryId, "edit-person");
    }

    @PatchMapping
    public String update(@ModelAttribute("person") @Valid PersonResponseDTO person,
                         BindingResult bindingResult,
                         HttpSession session) {
        personValidator.validate(person, bindingResult);

        if (bindingResult.hasErrors()) {

            System.out.println("Ошибки валидации:");
            bindingResult.getAllErrors().forEach(System.out::println);

            long libraryId = (long) session.getAttribute("libraryId");
            return this.getLibraryView(libraryId, "edit-person");
        }

        final Person updatePerson = PersonMapper.mapToPersonResponseDTO(person);
        final Person personFromDB = peopleService.getPersonById(person.getId()).orElseThrow();
        updatePerson.setPassword(personFromDB.getPassword());

        peopleService.update(updatePerson, session);
        return "/admin/success-update-person-page";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id, HttpSession session) {
//todo: переделать
        try {
            boolean isSelfDeleted =  peopleService.delete(id, session);
            if (isSelfDeleted) {
                return "redirect:/logout";
            }
            return "/admin/success-delete-person-page";
        } catch (PersonNotDeletedException e) {
            return "/admin/person-not-found";
        }
    }

    @GetMapping("insert1000People")
    public String insert1000People(HttpSession session) {
        peopleService.insert1000People(session);
        return "redirect:/admin/people";
    }

    @GetMapping("butch_insert1000People")
    public String butchInsert1000People(HttpSession session) {
        peopleService.butchInsert1000People(session);
        return "redirect:/admin/people";
    }

    private String getLibraryView(Long libraryId, String viewName) {
        return "library-%d/%s".formatted(libraryId, viewName);
    }
}
