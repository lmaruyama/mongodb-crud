package dev.lmaruyama.mongodb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PetNotFoundException extends RuntimeException {

    public PetNotFoundException() {
        super("Pet not found for id provided");
    }
}
