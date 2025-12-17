import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import api from '../api/api';

function SongsList({ token }) {
    const [songs, setSongs] = useState([]);
    const [error, setError] = useState('');

    const fetchSongs = async () => {
        try {
            const response = await api.get('/songs');
            setSongs(response.data);
        } catch (err) {
            setError('Error al obtener canciones');
            console.error(err);
        }
    };

    useEffect(() => {
        fetchSongs();
    }, []);

    return (
        <div>
            <h2>Canciones</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <Link to="/songs/new">Agregar Nueva Canción</Link>
            <ul>
                {songs.map((song) => (
                    <li key={song.id}>
                        {song.name} - {song.genre}
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default SongsList;
