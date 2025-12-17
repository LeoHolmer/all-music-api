package com.leoholmer.AllMusic.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SongRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @NotBlank(message = "El género es obligatorio")
    private String genre;
}
