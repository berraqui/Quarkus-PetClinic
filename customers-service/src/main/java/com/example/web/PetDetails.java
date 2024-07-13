package com.example.web;


import com.example.model.Pet;
import com.example.model.PetType;
import com.example.model.Owner;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public record PetDetails(
        long id,
        String name,
        String owner,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,
        PetType type
) {
    public PetDetails(Pet pet) {
        this(pet.id, pet.getName(), pet.getOwner().getFirstName() + " " + pet.getOwner().getLastName(), pet.getBirthDate(), pet.getType());
    }
}
