package dev.lmaruyama.mongodb.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;

@Document("pets")
public record Pet(
        @MongoId
        String id,
        @NotNull(message = "A type must be provided")
        PetType type,
        @NotNull(message = "A breed must be provided")
        Breed breed,
        @NotBlank(message = "The pet's name must be provided")
        String name,
        LocalDate dob,
        @NotBlank(message = "The owner's name must be provided")
        String ownerName,
        @NotBlank(message = "If unknown, inform \"UNKNOWN\"")
        String chipNumber,
        boolean neutered) {
}
