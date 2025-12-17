import React, { useState, useEffect } from 'react';
import api from '../api/api';

function PlaylistsList({ token }) {
    const [playlists, setPlaylists] = useState([]);
    const [error, setError] = useState('');

    const fetchPlaylists = async () => {
        try {
            const response = await api.get('/playlists');
            setPlaylists(response.data);
        } catch (err) {
            setError('Error al obtener playlists');
            console.error(err);
        }
    };

    useEffect(() => {
        fetchPlaylists();
    }, []);

    return (
        <div>
            <h2>Playlists</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <ul>
                {playlists.map((pl) => (
                    <li key={pl.id}>
                        {pl.name} - Propietario: {pl.ownerName} - Canciones: {pl.songCount}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default PlaylistsList;
