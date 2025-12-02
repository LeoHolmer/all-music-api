import React, { useState } from 'react';
import { Routes, Route, Navigate, useNavigate } from 'react-router-dom';
import LoginForm from './components/LoginForm';
import SongsList from './components/SongsList';
import SongForm from './components/SongForm';
import PlaylistsList from './components/PlaylistsList';

function App() {
  const [token, setToken] = useState(localStorage.getItem('token') || null);
  const navigate = useNavigate();

  const handleLogin = (newToken) => {
    localStorage.setItem('token', newToken);
    setToken(newToken);
    navigate('/songs');
  };

  const handleLogout = () => {
    localStorage.removeItem('token');
    setToken(null);
    navigate('/login');
  };

  return (
      <div>
        <header>
          <h1>AllMusic Frontend</h1>
          {token && <button onClick={handleLogout}>Logout</button>}
        </header>
        <Routes>
          <Route path="/login" element={<LoginForm onLogin={handleLogin} />} />
          <Route path="/songs" element={token ? <SongsList token={token} /> : <Navigate to="/login" />} />
          <Route path="/songs/new" element={token ? <SongForm token={token} /> : <Navigate to="/login" />} />
          <Route path="/playlists" element={token ? <PlaylistsList token={token} /> : <Navigate to="/login" />} />
          <Route path="*" element={<Navigate to={token ? "/songs" : "/login"} />} />
        </Routes>
      </div>
  );
}

export default App;
