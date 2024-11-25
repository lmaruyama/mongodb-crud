package dev.lmaruyama.mongodb.pet;

import dev.lmaruyama.mongodb.model.Pet;
import dev.lmaruyama.mongodb.model.PetType;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/pets")
class PetController {

    private final PetService petService;

    public PetController(PetService petService) {
        this.petService = petService;
    }

    @GetMapping
    List<Pet> getAllPets() {
        return petService.findAll();
    }

    @GetMapping("/{id}")
    Pet retrievePet(@PathVariable String id) {
        return petService.findById(id);
    }

    @GetMapping("/{type}/type")
    List<Pet> retrieveAllDogs(@PathVariable PetType type) {
        return petService.findPetsByType(type);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    Pet createPet(@RequestBody @Valid Pet pet) {
        return petService.createPet(pet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updatePet(@PathVariable String id, @Valid @RequestBody Pet pet) {
        petService.updatePet(id, pet);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deletePet(@PathVariable String id) {
        petService.deletePet(id);
    }
}
