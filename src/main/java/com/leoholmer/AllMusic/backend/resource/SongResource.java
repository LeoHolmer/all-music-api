package com.leoholmer.AllMusic.backend.resource;

import com.leoholmer.AllMusic.backend.dto.SongRequestDTO;
import com.leoholmer.AllMusic.backend.dto.SongResponseDTO;
import com.leoholmer.AllMusic.backend.exception.BadRequestException;
import com.leoholmer.AllMusic.backend.exception.ResourceNotFoundException;
import com.leoholmer.AllMusic.backend.exception.UnauthorizedException;
import com.leoholmer.AllMusic.backend.model.Genre;
import com.leoholmer.AllMusic.backend.model.Song;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.service.AuthorizationService;
import com.leoholmer.AllMusic.backend.service.SongService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/songs")
public class SongResource {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private SongService songService;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * Método auxiliar para asegurar que el token tenga el formato correcto.
     * Si no empieza con "Bearer ", se lo agrega.
     */
    private String extractToken(String authHeader) {
        if (authHeader == null || authHeader.trim().isEmpty()) {
            throw new UnauthorizedException("Missing Authorization header");
        }
        if (!authHeader.startsWith("Bearer ")) {
            return "Bearer " + authHeader;
        }
        return authHeader;
    }

    @PostMapping
    public ResponseEntity<?> createSong(@Valid @RequestBody SongRequestDTO dto,
                                        @RequestHeader("Authorization") String token) {
        User user = authorizationService.authorize(extractToken(token));

        Genre genre;
        try {
            genre = Genre.valueOf(dto.getGenre().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Género inválido: " + dto.getGenre());
        }

        Song song = new Song();
        song.setTitle(dto.getName());
        song.setGenre(genre);
        song.setArtist(user);

        try {
            songService.createSong(song, user);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
        return ResponseEntity.status(201).build();
    }

    @GetMapping
    public ResponseEntity<List<SongResponseDTO>> getSongs(
            @RequestParam(required = false) String artist,
            @RequestParam(required = false) String genreParam,
            @RequestHeader("Authorization") String token) {

        authorizationService.authorize(extractToken(token));

        Genre genre = null;
        if (genreParam != null) {
            try {
                genre = Genre.valueOf(genreParam.toUpperCase());
            } catch (IllegalArgumentException e) {
                throw new BadRequestException("Género inválido: " + genreParam);
            }
        }
        List<Song> songs = songService.getSongs(artist, genre);
        List<SongResponseDTO> response = songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SongResponseDTO> getSong(@PathVariable Long id,
                                                   @RequestHeader("Authorization") String token) {
        authorizationService.authorize(extractToken(token));
        Song song = songService.getSongById(id);
        if (song == null) {
            throw new ResourceNotFoundException("Canción no encontrada con ID: " + id);
        }
        SongResponseDTO dto = modelMapper.map(song, SongResponseDTO.class);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSong(@PathVariable Long id,
                                        @Valid @RequestBody SongRequestDTO dto,
                                        @RequestHeader("Authorization") String token) {
        User user = authorizationService.authorize(extractToken(token));

        Genre genre;
        try {
            genre = Genre.valueOf(dto.getGenre().toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Género inválido: " + dto.getGenre());
        }

        Song updatedSong = new Song();
        updatedSong.setTitle(dto.getName());
        updatedSong.setGenre(genre);

        try {
            songService.updateSong(id, updatedSong, user);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSong(@PathVariable Long id,
                                        @RequestHeader("Authorization") String token) {
        User user = authorizationService.authorize(extractToken(token));
        try {
            songService.deleteSong(id, user);
        } catch (Exception ex) {
            throw new BadRequestException(ex.getMessage());
        }
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<SongResponseDTO>> getCurrentUserSongs(@RequestHeader("Authorization") String token) {
        User user = authorizationService.authorize(extractToken(token));
        List<Song> songs = songService.getSongsByUser(user);
        List<SongResponseDTO> response = songs.stream()
                .map(song -> modelMapper.map(song, SongResponseDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
