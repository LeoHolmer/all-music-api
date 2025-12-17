package com.leoholmer.AllMusic.backend.dto;

import lombok.Data;

@Data
public class SongResponseDTO {
    private Long id;
    private String name;
    private String description;
    private String genre;  // Ahora es String
    private Artist artist;

    @Data
    public static class Artist {
        private Long id;
        private String name;
    }
}
