package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.model.Genre;
import com.leoholmer.AllMusic.backend.model.Song;
import com.leoholmer.AllMusic.backend.model.User;

import java.util.List;

public interface SongService {
    List<Song> getSongs(String artist, Genre genre);
    Song getSongById(Long id);
    void createSong(Song song, User user) throws Exception;
    void updateSong(Long id, Song updatedSong, User user) throws Exception;
    void deleteSong(Long id, User user) throws Exception;
    List<Song> getSongsByUser(User user);
}
