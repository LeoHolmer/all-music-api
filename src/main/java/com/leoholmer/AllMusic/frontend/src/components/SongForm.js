import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import api from '../api/api';

function SongForm({ token }) {
    const [name, setName] = useState('');
    const [genre, setGenre] = useState('');
    const [error, setError] = useState('');
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await api.post('/songs', { name, genre });
            navigate('/songs');
        } catch (err) {
            setError('Error al crear la canción');
            console.error(err);
        }
    };

    return (
        <div>
            <h2>Nueva Canción</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Nombre de la canción:</label>
                    <input type="text" value={name} onChange={(e) => setName(e.target.value)} required />
                </div>
                <div>
                    <label>Género:</label>
                    <input type="text" value={genre} onChange={(e) => setGenre(e.target.value)} required />
                </div>
                <button type="submit">Crear Canción</button>
            </form>
        </div>
    );
}

export default SongForm;
