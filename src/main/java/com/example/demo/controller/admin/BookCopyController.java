package com.example.demo.controller.admin;

import com.example.demo.model.Book;
import com.example.demo.model.BookCopy;
import com.example.demo.service.BookCopyService;
import com.example.demo.service.BookService;
import com.example.demo.service.PeopleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("admin/book-copy")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookCopyController {

    final BookService bookService;
    final BookCopyService bookCopyService;
    final PeopleService peopleService;

    @GetMapping("{bookId}")
    public String showBookCopyById(@PathVariable Long bookId, Model model) {
        Optional<Book> bookOpt = bookService.getBookById(bookId);

        if (bookOpt.isEmpty()) {
            return "/admin/book-not-found";
        }

        Book book = bookOpt.get();
        List<BookCopy> copies = bookCopyService.getCopiesByBookId(book.getBookId());

        model.addAttribute("book", book);
        model.addAttribute("copies", copies);
        model.addAttribute("people", peopleService.getAllPeople());

        return "/books/book-details";
    }

    @PostMapping("/assign")
    public String assignBook(@RequestParam Long copyId, @RequestParam Long bookId, @RequestParam Long personId) {
        bookCopyService.assignBook(copyId, personId);
        return "redirect:/admin/book-copy/" + bookId;
    }


    @PostMapping("/unassign")
    public String unassignBook(@RequestParam Long copyId, @RequestParam("bookId") Long bookId) {
        bookCopyService.unassignBook(copyId);
        return "redirect:/admin/book-copy/" + bookId;
    }
}
