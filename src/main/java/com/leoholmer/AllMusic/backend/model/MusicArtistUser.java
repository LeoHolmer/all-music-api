package com.leoholmer.AllMusic.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "music_artist_users")
public class MusicArtistUser extends User {
    @Override
    public boolean canCreateSongs() {
        return true;
    }
}