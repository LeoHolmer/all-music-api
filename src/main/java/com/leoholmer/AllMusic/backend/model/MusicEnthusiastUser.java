package com.leoholmer.AllMusic.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "music_enthusiast_users")
public class MusicEnthusiastUser extends User {
    @Override
    public boolean canCreateSongs() {
        return false;
    }
}