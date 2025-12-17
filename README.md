# AllMusic

Una aplicación de música construida con Spring Boot, que permite gestionar usuarios, canciones y playlists.

## Tecnologías Utilizadas

- **Java 21** (LTS)
- **Spring Boot 3.4.2**
- **Spring Data JPA**
- **Spring Security**
- **PostgreSQL** (base de datos)
- **JWT** (autenticación)
- **Maven** (gestión de dependencias)

## Características

- Gestión de usuarios
- Catálogo de canciones
- Creación de playlists
- Autenticación JWT
- API RESTful

## Requisitos Previos

- JDK 21
- Maven 3.6+
- PostgreSQL

## Instalación y Ejecución

1. Clona el repositorio:
   ```bash
   git clone https://github.com/LeoHolmer/AllMusic.git
   cd AllMusic
   ```

2. Configura la base de datos en `src/main/resources/application.properties`

3. Ejecuta la aplicación:
   ```bash
   ./mvnw spring-boot:run
   ```

## Endpoints Principales

- `POST /api/auth/login` - Login de usuario
- `GET /api/users` - Lista de usuarios
- `POST /songs` - Crear canción
- `GET /playlists` - Lista de playlists

## Tests

Ejecuta los tests con:
```bash
./mvnw test
```

## Contribución

1. Fork el proyecto
2. Crea una rama para tu feature (`git checkout -b feature/AmazingFeature`)
3. Commit tus cambios (`git commit -m 'Add some AmazingFeature'`)
4. Push a la rama (`git push origin feature/AmazingFeature`)
5. Abre un Pull Request

## Licencia

Este proyecto está bajo la Licencia MIT.