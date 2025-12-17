package com.leoholmer.AllMusic.backend.config;

import com.leoholmer.AllMusic.backend.dto.SongResponseDTO;
import com.leoholmer.AllMusic.backend.model.Genre;
import com.leoholmer.AllMusic.backend.model.Song;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import static org.junit.jupiter.api.Assertions.*;

public class ModelMapperConfigTest {

    private ModelMapper modelMapper;

    @BeforeEach
    public void setup() {
        ModelMapperConfig config = new ModelMapperConfig();
        modelMapper = config.modelMapper();
    }

    @Test
    public void testMappingSongToDTO() {
        Song song = new Song("Test Song", null, Genre.JAZZ);
        song.setId(1L);
        song.setTitle("Test Song");

        SongResponseDTO dto = modelMapper.map(song, SongResponseDTO.class);
        assertNotNull(dto);
        assertEquals("Test Song", dto.getName());
        assertEquals(Genre.JAZZ.toString(), dto.getGenre());
    }
}
