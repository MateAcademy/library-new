package com.example.demo.exception;

import com.example.demo.errors.BookNotDeletedException;
import com.example.demo.errors.PersonNotDeletedException;
import com.example.demo.errors.PersonNotFoundException;
import com.example.demo.notification.ClientRequestException;
import lombok.RequiredArgsConstructor;
import com.example.demo.notification.TranslationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Arrays;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    private final TranslationService translationService;
//    @ResponseStatus(HttpStatus.NOT_FOUND)
//    @ExceptionHandler(NoSuchBucketException.class)
//    public ServiceErrorResponse exception(NoSuchBucketException exception) {
//        log.warn("NoSuchBucketException: ", exception);
//        return ServiceErrorResponse.builder()
//            .timestamp(LocalDateTime.now().toString())
//            .statusCode(HttpStatus.NOT_FOUND.value())
//            .message("No such bucket")
//            .build();
//    }


    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<String> handlePersonNotFound(PersonNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(PersonNotDeletedException.class)
    public String exception(PersonNotDeletedException exception) {
        //log.warn("NoSuchBucketException: ", exception);
        return "/admin/person-not-deleted";
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(BookNotDeletedException.class)
    public String exception(BookNotDeletedException exception, Model model) {
        model.addAttribute("errorMessage", exception.getMessage());
        return "/admin/book-not-deleted";
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalStateException(IllegalStateException ex) {
        log.error("handleIllegalStateException: ", ex);
        System.out.println("Illegal argument error: " + ex + " " + ex.getCause());
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
            translationService.translate(ex.getMessage()));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    @ExceptionHandler(ClientRequestException.class)
    public ProblemDetail handleClientRequestException(ClientRequestException ex) {
        log.error("ClientRequestException c аргументами: {}", Arrays.deepToString(ex.getArgs()), ex);
        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, translationService.translate(ex.getMessage(), ex.getArgs()));
    }

}
