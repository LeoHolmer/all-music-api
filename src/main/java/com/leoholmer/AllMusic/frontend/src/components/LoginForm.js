import React, { useState } from 'react';
import axios from 'axios';

function LoginForm({ onLogin }) {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [error, setError] = useState('');

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            // Llamada al endpoint de autenticación (puedes cambiar entre /artist/auth o /enthusiast/auth)
            const response = await axios.post('http://localhost:8080/artist/auth', { username, password });
            const data = response.data;
            if (data.token) {
                onLogin(data.token);
            } else {
                setError('No se recibió token');
            }
        } catch (err) {
            setError('Error en el login');
            console.error(err);
        }
    };

    return (
        <div>
            <h2>Login</h2>
            {error && <p style={{ color: 'red' }}>{error}</p>}
            <form onSubmit={handleSubmit}>
                <div>
                    <label>Usuario:</label>
                    <input type="text" value={username} onChange={(e) => setUsername(e.target.value)} required />
                </div>
                <div>
                    <label>Contraseña:</label>
                    <input type="password" value={password} onChange={(e) => setPassword(e.target.value)} required />
                </div>
                <button type="submit">Ingresar</button>
            </form>
        </div>
    );
}

export default LoginForm;
