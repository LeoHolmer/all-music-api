package com.leoholmer.AllMusic.backend.dto;

import lombok.Data;

@Data
public class PlaylistResponseDTO {
    private Long id;         // Identificador único de la playlist
    private String name;     // Nombre de la playlist
    private String ownerName; // Nombre del usuario propietario de la playlist
    private int songCount;    // Cantidad de canciones en la playlist
}
