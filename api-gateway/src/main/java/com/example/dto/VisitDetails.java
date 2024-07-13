package com.example.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VisitDetails {

    private Integer id;
    private Integer petId;
    private String date;
    private String description;
}
