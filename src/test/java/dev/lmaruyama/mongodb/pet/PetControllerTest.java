package dev.lmaruyama.mongodb.pet;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.lmaruyama.mongodb.model.Breed;
import dev.lmaruyama.mongodb.model.Pet;
import dev.lmaruyama.mongodb.model.PetType;
import dev.lmaruyama.mongodb.exception.PetNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest({PetController.class, PetService.class})
@AutoConfigureMockMvc
class PetControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private PetRepository petRepository;

    List<Pet> pets = new ArrayList<>();

    @BeforeEach
    void setUp() {
        pets = List.of(
                new Pet("1", PetType.DOG, Breed.LABRADOR_RETRIEVER,
                        "Thor",
                        LocalDate.of(2020, Month.MAY, 2),
                        "Jimmy", "123B", true),

                new Pet("2", PetType.CAT, Breed.SPHYNX,
                        "Santa",
                        LocalDate.of(2022, Month.DECEMBER, 25),
                        "Jimmy", "456A", false),

                new Pet("3", PetType.DOG, Breed.BULLDOG, "Buddy",
                        LocalDate.of(2022, Month.DECEMBER, 25),
                        "Jimmy","00025X", false)
        );
    }

    @Test
    void shouldReturnListOfPets() throws Exception {
        when(petRepository.findAll()).thenReturn(pets);
        mockMvc.perform(get("/api/v1/pets"))
                .andExpect(content().json(objectMapper.writeValueAsString(pets)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnAPet() throws Exception {
        when(petRepository.findById(any())).thenReturn(Optional.of(pets.getFirst()));

        var content = """
                {
                  "id": "1",
                  "name": "Thor",
                  "type": "DOG",
                  "breed": "LABRADOR_RETRIEVER",
                  "dob": "2020-05-02",
                  "ownerName": "Jimmy",
                  "chipNumber": "123B",
                  "neutered": true
                }
                """;

        mockMvc.perform(get("/api/v1/pets/1"))
                .andExpect(content().json(content))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturnNotFoundForInvalidId() throws Exception {
        when(petRepository.findById(any())).thenThrow(PetNotFoundException.class);
        mockMvc.perform(get("/api/v1/pets/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldReturnAllDogs() throws Exception {
        final List<Pet> dogs = pets.stream().filter(pet -> PetType.DOG.equals(pet.type())).toList();
        when(petRepository.findByType(PetType.DOG)).thenReturn(dogs);
        mockMvc.perform(get("/api/v1/pets/DOG/type"))
                .andExpect(content().json(objectMapper.writeValueAsString(dogs)))
                .andExpect(status().isOk());
    }

    @Test
    void shouldCreatePet() throws Exception {
        when(petRepository.save(any())).thenReturn(pets.getFirst());

        var content = """
                {
                  "id":"1",
                  "name": "Thor",
                  "type": "DOG",
                  "breed": "LABRADOR_RETRIEVER",
                  "dob": "2020-05-02",
                  "ownerName": "Jimmy",
                  "chipNumber": "123B",
                  "neutered": true
                }
                """;

        mockMvc.perform(
                post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isCreated());
    }

    @Test
    void shouldNotCreatePetWithWrongBreed() throws Exception {
        var content = """
                {
                  "id":"1",
                  "name": "Thor",
                  "type": "CAT",
                  "breed": "LABRADOR_RETRIEVER",
                  "dob": "2020-05-02",
                  "ownerName": "Jimmy Doe",
                  "chipNumber": "123B",
                  "neutered": true
                }
                """;

        mockMvc.perform(
                post("/api/v1/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldUpdatePet() throws Exception {
        when(petRepository.findById(any())).thenReturn(Optional.of(pets.getFirst()));
        when(petRepository.save(any())).thenReturn(any());
        var content = """
                {
                  "id":"1",
                  "name": "Thor",
                  "type": "DOG",
                  "breed": "LABRADOR_RETRIEVER",
                  "dob": "2020-05-02",
                  "ownerName": "Jimmy Doe",
                  "chipNumber": "123B",
                  "neutered": true
                }
                """;

        mockMvc.perform(
                put("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isNoContent());
    }

    @Test
    void shouldNotUpdatePetWhenTheIdIsInvalid() throws Exception {
        when(petRepository.findById(any())).thenThrow(PetNotFoundException.class);
        var content = """
                {
                  "id":"1",
                      "name": "Thor",
                      "type": "DOG",
                      "breed": "LABRADOR_RETRIEVER",
                      "dob": "2020-05-02",
                      "ownerName": "Jimmy Doe",
                      "chipNumber": "123B",
                      "neutered": true
                }
                """;

        mockMvc.perform(
                put("/api/v1/pets/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isNotFound());
    }

    @Test
    void shouldNotUpdatePetWithWrongBreed() throws Exception {
        var content = """
                {
                  "id":"1",
                  "name": "Thor",
                  "type": "CAT",
                  "breed": "LABRADOR_RETRIEVER",
                  "dob": "2020-05-02",
                  "ownerName": "Jimmy Doe",
                  "chipNumber": "123B",
                  "neutered": true
                }
                """;

        mockMvc.perform(
                put("/api/v1/pets/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void shouldNotDeletePet() throws Exception {
        when(petRepository.findById(any())).thenReturn(Optional.of(pets.getFirst()));
        doNothing().when(petRepository).delete(any());

        mockMvc.perform(
                delete("/api/v1/pets/1")).andExpect(status().isNoContent());

        verify(petRepository, times(1)).delete(any());
    }

    @Test
    void shouldNotDeletePetIfIdIsInvalid() throws Exception {
        when(petRepository.findById(any())).thenThrow(PetNotFoundException.class);

        mockMvc.perform(
                delete("/api/v1/pets/999")).andExpect(status().isNotFound());

        verify(petRepository, times(0)).delete(any());
    }
}
