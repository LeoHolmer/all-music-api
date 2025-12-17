package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.model.Playlist;
import com.leoholmer.AllMusic.backend.model.Song;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.repository.PlaylistRepository;
import com.leoholmer.AllMusic.backend.repository.SongRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistServiceImp implements PlaylistService {

    @Autowired
    private PlaylistRepository playlistRepository;

    @Autowired
    private SongRepository songRepository;

    @Override
    public List<Playlist> getAllPlaylists() {
        return playlistRepository.findAll();
    }

    @Override
    public Playlist getPlaylistById(Long id) {
        return playlistRepository.findById(id).orElse(null);
    }

    @Override
    public void createPlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public void updatePlaylist(Playlist playlist) {
        playlistRepository.save(playlist);
    }

    @Override
    public void deletePlaylist(Playlist playlist) {
        playlistRepository.delete(playlist);
    }

    @Override
    public List<Playlist> getPlaylistsByUser(User user) {
        return playlistRepository.findByOwner(user);
    }

    @Override
    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }
}