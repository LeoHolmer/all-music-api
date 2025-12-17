package com.leoholmer.AllMusic.backend.resource;

import com.leoholmer.AllMusic.backend.dto.PlaylistRequestDTO;
import com.leoholmer.AllMusic.backend.dto.PlaylistResponseDTO;
import com.leoholmer.AllMusic.backend.dto.SongIdDTO;
import com.leoholmer.AllMusic.backend.model.Playlist;
import com.leoholmer.AllMusic.backend.model.Song;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.service.AuthorizationService;
import com.leoholmer.AllMusic.backend.service.PlaylistService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/playlists")
public class PlaylistResource {

    @Autowired
    private AuthorizationService authorizationService;

    @Autowired
    private PlaylistService playlistService;

    @Autowired
    private ModelMapper modelMapper;

    // GET /playlists
    @GetMapping
    public ResponseEntity<?> getPlaylists(@RequestHeader("Authorization") String token) {
        try {
            authorizationService.authorize(token);
            List<Playlist> playlists = playlistService.getAllPlaylists();
            List<PlaylistResponseDTO> response = playlists.stream()
                    .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // GET /playlists/:id
    @GetMapping("/{id}")
    public ResponseEntity<?> getPlaylist(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            authorizationService.authorize(token);
            Playlist playlist = playlistService.getPlaylistById(id);
            if (playlist == null) {
                return ResponseEntity.status(404).body("Playlist not found");
            }
            return ResponseEntity.ok(modelMapper.map(playlist, PlaylistResponseDTO.class));
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    // POST /playlists
    @PostMapping
    public ResponseEntity<?> createPlaylist(@RequestBody PlaylistRequestDTO dto, @RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);
            Playlist playlist = new Playlist();
            playlist.setName(dto.getName());
            playlist.setOwner(user);
            playlistService.createPlaylist(playlist);
            return ResponseEntity.status(201).build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // PUT /playlists/:id
    @PutMapping("/{id}")
    public ResponseEntity<?> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistRequestDTO dto, @RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);
            Playlist playlist = playlistService.getPlaylistById(id);
            if (playlist == null || !playlist.getOwner().equals(user)) {
                return ResponseEntity.status(403).body("You are not authorized to update this playlist");
            }
            playlist.setName(dto.getName());
            playlistService.updatePlaylist(playlist);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /playlists/:id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePlaylist(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);
            Playlist playlist = playlistService.getPlaylistById(id);
            if (playlist == null || !playlist.getOwner().equals(user)) {
                return ResponseEntity.status(403).body("You are not authorized to delete this playlist");
            }
            playlistService.deletePlaylist(playlist);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // POST /playlists/:id/songs
    @PostMapping("/{id}/songs")
    public ResponseEntity<?> addSongToPlaylist(@PathVariable Long id, @RequestBody SongIdDTO dto, @RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);
            Playlist playlist = playlistService.getPlaylistById(id);
            if (playlist == null || !playlist.getOwner().equals(user)) {
                return ResponseEntity.status(403).body("You are not authorized to modify this playlist");
            }
            Song song = playlistService.getSongById(dto.getSongId());
            if (song == null) {
                return ResponseEntity.status(404).body("Song not found");
            }
            playlist.addSong(song);
            playlistService.updatePlaylist(playlist);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // DELETE /playlists/:id/songs/:song_id
    @DeleteMapping("/{id}/songs/{song_id}")
    public ResponseEntity<?> removeSongFromPlaylist(@PathVariable Long id, @PathVariable Long song_id, @RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);
            Playlist playlist = playlistService.getPlaylistById(id);
            if (playlist == null || !playlist.getOwner().equals(user)) {
                return ResponseEntity.status(403).body("You are not authorized to modify this playlist");
            }
            Song song = playlistService.getSongById(song_id);
            if (song == null) {
                return ResponseEntity.status(404).body("Song not found");
            }
            playlist.removeSong(song);
            playlistService.updatePlaylist(playlist);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // GET /me/playlists
    @GetMapping("/me/playlists")
    public ResponseEntity<?> getCurrentUserPlaylists(@RequestHeader("Authorization") String token) {
        try {
            User user = authorizationService.authorize(token);
            List<Playlist> playlists = playlistService.getPlaylistsByUser(user);
            List<PlaylistResponseDTO> response = playlists.stream()
                    .map(playlist -> modelMapper.map(playlist, PlaylistResponseDTO.class))
                    .collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}