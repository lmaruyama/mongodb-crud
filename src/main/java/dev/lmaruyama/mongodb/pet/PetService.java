package dev.lmaruyama.mongodb.pet;

import dev.lmaruyama.mongodb.model.Pet;
import dev.lmaruyama.mongodb.model.PetType;
import dev.lmaruyama.mongodb.exception.InvalidBreedException;
import dev.lmaruyama.mongodb.exception.PetNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PetService.class);

    private final PetRepository repository;
    public PetService(PetRepository repository) {
        this.repository = repository;
    }

    public List<Pet> findAll() {
        return repository.findAll();
    }

    public Pet findById(String id) {
        return repository.findById(id).orElseThrow(PetNotFoundException::new);
    }

    public List<Pet> findPetsByType(PetType type) {
        return repository.findByType(type);
    }

    public Pet createPet(Pet pet) {
        validateBreed(pet);
        return repository.save(pet);
    }

    public void updatePet(String id, Pet pet) {
        validateBreed(pet);
        Pet originalPet = repository.findById(id).orElseThrow(PetNotFoundException::new);
        Pet updatedPet =
                new Pet(originalPet.id(),
                        pet.type(),
                        pet.breed(),
                        pet.name(),
                        pet.dob(),
                        pet.ownerName(),
                        pet.chipNumber(),
                        pet.neutered());

       repository.save(updatedPet);
    }

    private static void validateBreed(Pet pet) {
        if (!pet.breed().getType().equals(pet.type())) {
            LOGGER.error("The breed is not correctly associated with the pet type. [{} is a {} breed], pet type provided: {}",
                    pet.breed(), pet.breed().getType(), pet.type());
            throw new InvalidBreedException("Invalid breed for pet type: " + pet.type());
        }
    }

    public void deletePet(String id) {
        Pet pet = repository.findById(id).orElseThrow(PetNotFoundException::new);
        repository.delete(pet);
    }
}
