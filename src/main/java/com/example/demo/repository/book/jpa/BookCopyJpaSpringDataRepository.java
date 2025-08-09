package com.example.demo.repository.book.jpa;

import com.example.demo.model.BookCopy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookCopyJpaSpringDataRepository extends JpaRepository<BookCopy, Long> {

    int countByBook_BookId(Long bookId);

    int countByBook_BookIdAndLibrary_LibraryId(Long bookId, Long libraryId);

    List<BookCopy> findByBook_BookIdAndIsAvailableTrue(Long bookId);

    List<BookCopy> findByBook_BookId(Long bookId);

    List<BookCopy> findByOwner_Id(Long personId);

    @Modifying
    @Query("UPDATE BookCopy bc SET bc.owner.id = :personId, bc.isAvailable = false WHERE bc.id = :copyId")
    void assignBookCopy(Long copyId, Long personId);

    @Modifying
    @Query("UPDATE BookCopy bc SET bc.owner = null, bc.isAvailable = true WHERE bc.id = :copyId")
    void unassignBookCopy(Long copyId);
}