package dev.lmaruyama.mongodb.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(InvalidBreedException.class)
    public ResponseEntity<ApiError> handleException(InvalidBreedException e,
                                                    HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                httpStatus.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(PetNotFoundException.class)
    public ResponseEntity<ApiError> handleException(PetNotFoundException e,
                                                    HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.NOT_FOUND;
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                httpStatus.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleException(Exception e,
                                                    HttpServletRequest request) {
        final HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        ApiError apiError = new ApiError(
                request.getRequestURI(),
                e.getMessage(),
                httpStatus.value(),
                LocalDateTime.now()
        );

        return new ResponseEntity<>(apiError, httpStatus);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {

        final List<FieldError> fieldErrors = ex.getFieldErrors();

        final String errors = fieldErrors.stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        ErrorDetails errorDetails = new ErrorDetails(LocalDateTime.now(),
                errors, request.getDescription(false));

        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }
}
