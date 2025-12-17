package com.leoholmer.AllMusic.backend.repository;

import com.leoholmer.AllMusic.backend.model.Playlist;
import com.leoholmer.AllMusic.backend.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long> {

    // Buscar playlists por propietario (owner)
    List<Playlist> findByOwner(User owner);

    // Buscar una playlist por ID y propietario
    Playlist findByIdAndOwner(Long id, User owner);
}
