package com.example.demo.service;

import com.example.demo.dto.BookResponse;
import com.example.demo.errors.BookNotDeletedException;
import com.example.demo.errors.BookNotFoundException;
import com.example.demo.models.Book;
import com.example.demo.models.BookCopy;
import com.example.demo.notification.ClientRequestException;
import com.example.demo.notification.ExceptionMessage;
import com.example.demo.repository.book.BookCopyRepository;
import com.example.demo.repository.book.BookRepository;
import com.example.demo.utils.mappers.BookMapper;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BookService {

    final BookRepository bookRepository;
    final BookCopyRepository bookCopyRepository;
    final BookMapper bookMapper;


    public Page<BookResponse> getBooksPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Book> books = bookRepository.findAll(pageable);

        List<BookResponse> responses = books.getContent().stream()
            .map(book -> {
                int copies = bookCopyRepository.countByBookId(book.getBookId());
                return new BookResponse(
                    book.getBookId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getYear(),
                    copies
                );
            })
            .toList();

        return new PageImpl<>(responses, books.getPageable(), books.getTotalElements());
    }

    public Optional<Book> getBookById(@NonNull Long id) {
        return bookRepository.findById(id);
    }

    @Transactional
    public void saveWithCopies(Book book, int copyCount) {
        bookRepository.save(book);

        List<BookCopy> copies = IntStream.range(0, copyCount)
            .mapToObj(i -> {
                BookCopy copy = new BookCopy();
                copy.setBook(book);
                return copy;
            })
            .collect(Collectors.toList());

        bookCopyRepository.saveAll(copies);
    }

    public void update(@NonNull Integer id, @NonNull Book updatedBook) {
        bookRepository.update(updatedBook);
    }

    public void delete(@NonNull Long id) {
        Optional<Book> bookById = bookRepository.findById(id);

        if (bookById.isEmpty()) {
            //throw new BookNotDeletedException("There is no such book in the database with id= " + id);
            try{
                throw new IOException("test");
            } catch (IOException ex) {
                throw new ClientRequestException(ExceptionMessage.EXCEPTION_IMPORT_FILE_READ_FAILED, ex, 3L);
                //throw new IllegalStateException(ExceptionMessage.EXCEPTION_IMPORT_FILE_READ_FAILED.name(), ex);
            }

        }

        List<BookCopy> allCopies = bookCopyRepository.findAllWithOwnerByBookId(id);

        boolean anyAssigned = allCopies.stream()
            .anyMatch(copy -> copy.getOwner() != null);

        if (anyAssigned) {
            throw new BookNotDeletedException("Unable to delete book: there are checked out copies");
        }

        bookRepository.delete(id);
    }

    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    public void insert1000Books() {
        List<Book> books = get1000Books();

        long start = System.currentTimeMillis();
        for (Book book : books) {
            bookRepository.save(book);
        }
        long end = System.currentTimeMillis();
        System.out.println("⏱ Обычная вставка 1000 книг заняла: " + (end - start) + " мс");
    }

    public void batchInsert1000Books() {
        List<Book> books = get1000Books();

        long start = System.currentTimeMillis();
        bookRepository.butchSaveAll(books);
        long end = System.currentTimeMillis();
        System.out.println("⏱ Batch вставка 1000 книг заняла: " + (end - start) + " мс");
    }

    private List<Book> get1000Books() {
        List<Book> books = new ArrayList<>();
//        for (int i = 1; i <= 1000; i++) {
//            books.add(new Book("book_" + i, "author_" + i, 2000));
//        }
        return books;
    }
}
