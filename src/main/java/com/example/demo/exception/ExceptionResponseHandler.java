//package com.example.demo.exception;
//
//import com.example.demo.notification.TranslationService;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.io.FileUtils;
//import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
//import org.springframework.context.MessageSourceResolvable;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ProblemDetail;
////import org.springframework.security.access.AccessDeniedException;
////import org.springframework.security.authentication.BadCredentialsException;
////import org.springframework.security.authorization.method.AuthorizationManagerBeforeMethodInterceptor;
//import org.springframework.validation.FieldError;
//import org.springframework.validation.method.ParameterValidationResult;
//import org.springframework.web.bind.MethodArgumentNotValidException;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.bind.annotation.ResponseStatus;
//import org.springframework.web.bind.annotation.RestControllerAdvice;
//import org.springframework.web.method.annotation.HandlerMethodValidationException;
//import org.springframework.web.multipart.MaxUploadSizeExceededException;
//
//import java.nio.file.AccessDeniedException;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@RestControllerAdvice
//@Slf4j
//@RequiredArgsConstructor
//public class ExceptionResponseHandler {
//
//    private final TranslationService translationService;
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
//        Map<String, Map<String, String>> errors = ex.getFieldErrors()
//                .stream()
//                .collect(Collectors.toMap(
//                        FieldError::getField,
//                        v -> Map.of("Fields required", Objects.requireNonNull(v.getDefaultMessage())),
//                        (v1, v2) -> v1
//                ));
//        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST,
//                translationService.translate(ExceptionMessage.EXCEPTION_VALIDATION_FAILED.name()));
//        problemDetail.setProperty("errors", errors);
//        return problemDetail;
//    }
//
//    @ResponseStatus(HttpStatus.FORBIDDEN)
//    @ExceptionHandler(AccessDeniedException.class)
//    public ProblemDetail handleAccessDeniedException(AccessDeniedException ex) {
//        final String fallbackKey = ExceptionMessage.EXCEPTION_ACCESS_DENIED.name();
//        final String errorMsg = Arrays.stream(ex.getStackTrace())
//                .filter(stackTraceElement -> stackTraceElement.getClassName().equals(AuthorizationManagerBeforeMethodInterceptor.class.getName()))
//                .findFirst()
//                .map(stackTraceElement -> translationService.translate(fallbackKey))
//                .orElseGet(() -> translationService.translate(ex.getMessage()));
//
//        return ProblemDetail.forStatusAndDetail(HttpStatus.FORBIDDEN, errorMsg);
//    }
//
////    @ResponseStatus(HttpStatus.UNAUTHORIZED)
////    @ResponseBody
////    @ExceptionHandler(BadCredentialsException.class)
////    public ProblemDetail handleBadCredentialsException(BadCredentialsException ex) {
////        log.error("BadCredentials while processing request", ex);
////        return ProblemDetail.forStatusAndDetail(HttpStatus.UNAUTHORIZED,
////                translationService.translate(ExceptionMessage.EXCEPTION_UNAUTHORIZED));
////    }
//
//    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
//    @ResponseBody
//    @ExceptionHandler(OutOfMemoryError.class)
//    public ProblemDetail handleOutOfMemoryError(OutOfMemoryError ex) {
//        log.error("OutOfMemoryError while processing request", ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.PAYLOAD_TOO_LARGE, translationService.translate(ExceptionMessage.EXCEPTION_UPLOAD_TOO_LARGE));
//    }
//
//    @ResponseStatus(HttpStatus.PAYLOAD_TOO_LARGE)
//    @ResponseBody
//    @ExceptionHandler(MaxUploadSizeExceededException.class)
//    public ProblemDetail handleSizeLimitException(MaxUploadSizeExceededException ex) {
//        final long permittedSize = ((SizeLimitExceededException) ex.getCause().getCause()).getPermittedSize();
//        final String maxSize = FileUtils.byteCountToDisplaySize(permittedSize);
//        final String message = translationService.translate(ExceptionMessage.EXCEPTION_UPLOAD_MAX_SIZE, List.of(maxSize));
//        log.error(message, ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.PAYLOAD_TOO_LARGE, message);
//    }
//
////    @ResponseStatus(HttpStatus.NOT_FOUND)
////    @ResponseBody
////    @ExceptionHandler(ResourceNotFoundException.class)
////    public ProblemDetail handleResourceNotFoundException(ResourceNotFoundException ex) {
////        final String errorMsg = translationService.translate(ex.getExceptionMessage(), ex.getArgs());
////        log.error("Resource not found {}", errorMsg, ex);
////        return ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, errorMsg);
////    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(IllegalArgumentException.class)
//    public ProblemDetail handleIllegalArgumentException(IllegalArgumentException ex) {
//        log.error("Illegal argument exception", ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(IllegalStateException.class)
//    public ProblemDetail handleIllegalStateException(IllegalStateException ex) {
//        log.error("Illegal state exception", ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(ClientRequestException.class)
//    public ProblemDetail handleClientRequestException(ClientRequestException ex) {
//        log.error("ClientRequest argument error: {}", ex.getArgs(), ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, translationService.translate(ex.getExceptionMessage(), ex.getArgs()));
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(EmailSendingException.class)
//    public ProblemDetail handleEmailSendingException(EmailSendingException ex) {
//        log.error("EmailSending argument error: {}", ex.getArgs(), ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, translationService.translate(ex.getExceptionMessage(), ex.getArgs()));
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(FileSystemStorageException.class)
//    public ProblemDetail handleFileSystemStorageException(FileSystemStorageException ex) {
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.BAD_REQUEST)
//    @ResponseBody
//    @ExceptionHandler(KeycloakServiceException.class)
//    public ProblemDetail handleKeycloakServiceException(KeycloakServiceException ex) {
//        log.error("Keycloak service exception: {}", ex.getMessage(), ex);
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
//    }
//
//    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
//    @ResponseBody
//    @ExceptionHandler(HandlerMethodValidationException.class)
//    public ProblemDetail handleMethodValidationException(HandlerMethodValidationException ex) {
//        final boolean fileValidation = Optional.ofNullable(ex.getAllValidationResults())
//                .map(List::getFirst)
//                .map(ParameterValidationResult::getResolvableErrors)
//                .map(List::getFirst)
//                .map(MessageSourceResolvable::getCodes)
//                .stream()
//                .flatMap(Arrays::stream)
//                .anyMatch(b -> b.contains(MultipartFileValidator.ERROR_CODE));
//        if (fileValidation) {
//            log.error("Unsupported Media Type: {}", ex.getMessage());
//            return ProblemDetail.forStatusAndDetail(HttpStatus.UNSUPPORTED_MEDIA_TYPE, HttpStatus.UNSUPPORTED_MEDIA_TYPE.getReasonPhrase());
//        }
//        return ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.getReasonPhrase());
//    }
//}
