package dev.lmaruyama.mongodb.pet;

import dev.lmaruyama.mongodb.model.Pet;
import dev.lmaruyama.mongodb.model.PetType;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends MongoRepository<Pet, String> {
    List<Pet> findByType(PetType type);
}
