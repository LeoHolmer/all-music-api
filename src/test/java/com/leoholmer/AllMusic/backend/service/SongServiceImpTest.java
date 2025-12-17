package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.model.Genre;
import com.leoholmer.AllMusic.backend.model.MusicArtistUser;
import com.leoholmer.AllMusic.backend.model.Song;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.repository.SongRepository;
import com.leoholmer.AllMusic.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SongServiceImpTest {

    @InjectMocks
    private SongServiceImp songServiceImp;

    @Mock
    private SongRepository songRepository;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateSongWithArtistUser() throws Exception {
        MusicArtistUser artistUser = new MusicArtistUser();
        artistUser.setUsername("artist");
        Song song = new Song("Test Song", artistUser, Genre.ROCK);

        when(songRepository.save(any(Song.class))).thenAnswer(invocation -> {
            Song s = invocation.getArgument(0);
            s.setId(1L);
            return s;
        });

        songServiceImp.createSong(song, artistUser);
        verify(songRepository, times(1)).save(song);
    }

    @Test
    public void testCreateSongWithNonArtistUser() {
        // Creamos un usuario que no es artista (subclase anónima de User)
        User nonArtist = new User("nonArtist", "pass") {
            @Override
            public boolean canCreateSongs() {
                return false;
            }
        };

        Song song = new Song("Test Song", nonArtist, Genre.ROCK);
        Exception exception = assertThrows(Exception.class, () -> {
            songServiceImp.createSong(song, nonArtist);
        });
        assertEquals("Only music artists can create songs.", exception.getMessage());
    }

    @Test
    public void testUpdateSongWithUnauthorizedUser() {
        MusicArtistUser artistUser = new MusicArtistUser();
        artistUser.setUsername("artist");

        MusicArtistUser anotherArtist = new MusicArtistUser();
        anotherArtist.setUsername("anotherArtist");

        Song existingSong = new Song("Old Title", artistUser, Genre.ROCK);
        existingSong.setId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.of(existingSong));

        Song updatedSong = new Song("New Title", artistUser, Genre.POP);

        Exception exception = assertThrows(Exception.class, () -> {
            songServiceImp.updateSong(1L, updatedSong, anotherArtist);
        });
        assertEquals("You can only update your own songs.", exception.getMessage());
    }

    @Test
    public void testDeleteSongWithUnauthorizedUser() {
        MusicArtistUser artistUser = new MusicArtistUser();
        artistUser.setUsername("artist");

        MusicArtistUser anotherArtist = new MusicArtistUser();
        anotherArtist.setUsername("anotherArtist");

        Song existingSong = new Song("Test Song", artistUser, Genre.ROCK);
        existingSong.setId(1L);

        when(songRepository.findById(1L)).thenReturn(Optional.of(existingSong));

        Exception exception = assertThrows(Exception.class, () -> {
            songServiceImp.deleteSong(1L, anotherArtist);
        });
        assertEquals("You can only delete your own songs.", exception.getMessage());
    }
}
