package com.leoholmer.AllMusic.backend.resource;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leoholmer.AllMusic.backend.dto.SongRequestDTO;
import com.leoholmer.AllMusic.backend.repository.SongRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class SongResourceIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private ObjectMapper objectMapper;

    /**
     * En un escenario real, deberías obtener el token realizando
     * una petición de autenticación. Aquí lo simulamos.
     */
    private String obtenerTokenValido() {
        // Este token debe ser generado mediante JwtTokenUtil con la misma clave secreta configurada
        return "Bearer valid.token";
    }

    @Test
    public void testCreateSongConTokenValido() throws Exception {
        String token = obtenerTokenValido();

        SongRequestDTO dto = new SongRequestDTO();
        dto.setName("Cancion de prueba");
        dto.setGenre("ROCK");

        mockMvc.perform(post("/songs")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    public void testCreateSongConTokenInvalido() throws Exception {
        // Se simula un token inválido
        String token = "Bearer invalid.token";

        SongRequestDTO dto = new SongRequestDTO();
        dto.setName("Cancion de prueba");
        dto.setGenre("ROCK");

        mockMvc.perform(post("/songs")
                        .header("Authorization", token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }
}
