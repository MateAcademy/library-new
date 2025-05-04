package com.example.demo.controller.admin;

import com.example.demo.service.BookCopyService;
import com.example.demo.service.BookService;
import com.example.demo.service.PeopleService;
import com.example.demo.utils.validators.BookValidator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("admin/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyController {

    final BookService bookService;
    final BookCopyService bookCopyService;
    final PeopleService peopleService;
    final BookValidator bookValidator;

    @PostMapping("/unassign")
    public String unassignBook(@RequestParam("bookId") Long bookId) {
        bookService.unassignBook(bookId);
        return "redirect:/admin/books/" + bookId;
    }

    @PostMapping("/assign")
    public String assignBook(@RequestParam("bookId") Long bookId,
                             @RequestParam("personId") Long personId) {
        bookService.assignBook(bookId, personId);
        return "redirect:/admin/books/" + bookId;
    }


}
