import axios from 'axios';

const API_URL = 'http://localhost:8080'; // Ajusta esta URL a tu backend

// Crea una instancia de axios
const api = axios.create({
    baseURL: API_URL,
});

// Interceptor para agregar el token a cada petición
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if (token) {
        config.headers.Authorization = token;
    }
    return config;
});

export default api;
