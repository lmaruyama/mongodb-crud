package dev.lmaruyama.mongodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidBreedException extends RuntimeException {
    public InvalidBreedException(String message) {
        super(message);
    }
}
