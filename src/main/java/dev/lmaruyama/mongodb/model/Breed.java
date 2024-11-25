package dev.lmaruyama.mongodb.model;

public enum Breed {
    // Dog Breeds
    LABRADOR_RETRIEVER(PetType.DOG),
    GERMAN_SHEPHERD(PetType.DOG),
    GOLDEN_RETRIEVER(PetType.DOG),
    FRENCH_BULLDOG(PetType.DOG),
    BULLDOG(PetType.DOG),
    UNKNOWN_DOG(PetType.DOG),

    // Cat Breeds
    PERSIAN(PetType.CAT),
    MAINE_COON(PetType.CAT),
    SIAMESE(PetType.CAT),
    RAGDOLL(PetType.CAT),
    SPHYNX(PetType.CAT),
    UNKNOWN_CAT(PetType.CAT);

    private final PetType type;

    Breed(PetType type) {
        this.type = type;
    }

    public PetType getType() {
        return type;
    }
}
