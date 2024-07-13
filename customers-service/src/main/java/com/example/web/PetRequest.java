package com.example.web;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Size;
import java.util.Date;

public record PetRequest(
        int id,
        @JsonFormat(pattern = "yyyy-MM-dd")
        Date birthDate,
        @Size(min = 1)
        String name,
        int typeId
) {}
