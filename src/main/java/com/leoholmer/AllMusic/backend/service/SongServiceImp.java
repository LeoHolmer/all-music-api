package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.model.Genre;
import com.leoholmer.AllMusic.backend.model.MusicArtistUser;
import com.leoholmer.AllMusic.backend.model.Song;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.repository.SongRepository;
import com.leoholmer.AllMusic.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImp implements SongService {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<Song> getSongs(String artist, Genre genre) {
        if (artist != null && genre != null) {
            return songRepository.findByArtistUsernameAndGenre(artist, genre);
        } else if (artist != null) {
            return songRepository.findByArtistUsername(artist);
        } else if (genre != null) {
            return songRepository.findByGenre(genre);
        }
        return songRepository.findAll();
    }

    @Override
    public Song getSongById(Long id) {
        return songRepository.findById(id).orElse(null);
    }

    @Override
    public void createSong(Song song, User user) throws Exception {
        if (!(user instanceof MusicArtistUser)) {
            throw new Exception("Only music artists can create songs.");
        }
        song.setArtist(user); // user es un MusicArtistUser
        songRepository.save(song);
    }

    @Override
    public void updateSong(Long id, Song updatedSong, User user) throws Exception {
        Song song = songRepository.findById(id).orElseThrow(() -> new Exception("Song not found"));
        if (!song.getArtist().equals(user)) {
            throw new Exception("You can only update your own songs.");
        }
        song.setTitle(updatedSong.getTitle());
        song.setGenre(updatedSong.getGenre());
        songRepository.save(song);
    }

    @Override
    public void deleteSong(Long id, User user) throws Exception {
        Song song = songRepository.findById(id).orElseThrow(() -> new Exception("Song not found"));
        if (!song.getArtist().equals(user)) {
            throw new Exception("You can only delete your own songs.");
        }
        songRepository.delete(song);
    }

    @Override
    public List<Song> getSongsByUser(User user) {
        if (user instanceof MusicArtistUser) {
            return songRepository.findByArtistUsername(user.getUsername());
        }
        return new ArrayList<>();
    }
}
