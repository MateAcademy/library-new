package com.example.demo.controller.admin;

import com.example.demo.dto.BookResponseDTO;
import com.example.demo.models.Book;
import com.example.demo.service.BookService;

import com.example.demo.utils.validators.BookValidator;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("admin/books")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookController {

    final BookService bookService;
    final BookValidator bookValidator;

    @GetMapping
    public String index(@RequestParam(defaultValue = "0") int page,
                        @RequestParam(defaultValue = "20") int size,
                        HttpSession session,
                        Model model) {
        final Long libraryId = (Long) session.getAttribute("libraryId");
        if (libraryId == null) {
            log.warn("libraryId not found in session BookController");
            return "redirect:/admin/choose-library";
        }

        Page<BookResponseDTO> booksPage = bookService.getBooksPage(page, size, libraryId);

        model.addAttribute("books", booksPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("hasNext", booksPage.hasNext());

        return this.getLibraryView(libraryId, "all-books");
    }

    @GetMapping("new")
    public String newBook(@ModelAttribute Book book) {
        return "/books/new-book";
    }

    @PostMapping
    public String create(@ModelAttribute @Valid Book book,
                         BindingResult bindingResult,
                         @RequestParam("copyCount") int copyCount) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/books/new-book";
        }

        bookService.saveWithCopies(book, copyCount);
        return "/admin/success-create-book-page";
    }

    @GetMapping("{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        Optional<Book> bookById = bookService.getBookById(id);
        if (bookById.isPresent()) {
            model.addAttribute("book", bookById.get());
            return "/books/edit-book";
        } else {
            return "/admin/book-not-found";
        }
    }

    @PatchMapping("{id}")
    public String update(@ModelAttribute @Valid Book book,
                         BindingResult bindingResult,
                         @PathVariable Integer id) {
        bookValidator.validate(book, bindingResult);

        if (bindingResult.hasErrors()) {
            return "/books/edit-book";
        }

        bookService.update(id, book);
        return "/admin/success-update-book-page";
    }

    @DeleteMapping("{id}")
    public String delete(@PathVariable Long id) {
        bookService.delete(id);
        return "/admin/success-delete-book-page";
    }

    @GetMapping("search")
    public String search(@RequestParam("title") String title, Model model) {
        Optional<Book> bookOpt = bookService.findByTitle(title);
        if (bookOpt.isEmpty()) {
            model.addAttribute("notFound", true);
            return "/books/search-form";
        }

        model.addAttribute("book", bookOpt.get());
        return "/books/book-details";
    }

    private String getLibraryView(Long libraryId, String viewName) {
        return "library-%d/book/%s".formatted(libraryId, viewName);
    }
}
