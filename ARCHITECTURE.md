# Arquitectura de AllMusic API

## 📐 Visión General

AllMusic es una API diseñada para gestionar un ecosistema de contenido musical donde **artistas** publican **canciones** y **entusiastas** crean **playlists**. La arquitectura sigue **Clean Architecture** con énfasis en separación de responsabilidades y testabilidad.

## 🏗️ Estructura de Capas

### 1. **Presentation Layer**
- **Ubicación**: `controller/`
- **Responsabilidad**: Exposer endpoints REST
- **Componentes**:
  - `ArtistController`: Endpoints para artistas (crear, obtener, listar canciones)
  - `SongController`: CRUD de canciones (solo propietario puede modificar)
  - `PlaylistController`: CRUD de playlists + manejo de canciones en playlists
  - `AuthController`: Login para artistas y entusiastas

### 2. **Application/Service Layer**
- **Ubicación**: `service/`
- **Responsabilidad**: Lógica de negocio y orquestación
- **Features**:
  - `ArtistService`: Gestión de artistas con validaciones
  - `SongService`: CRUD con autorización (solo dueño puede editar)
  - `PlaylistService`: Gestión de playlists y canciones
  - `AuthenticationService`: Generación de JWT y validación de credenciales

### 3. **Domain Layer**
- **Ubicación**: `model/`, `dto/`
- **Responsabilidad**: Modelos de datos y DTOs
- **Entidades**:
  - `Artist`: Usuario que publica canciones
  - `Song`: Canción con metadatos (título, género, duración)
  - `Playlist`: Colección de canciones (creada por usuario)
  - `User`: Usuario del sistema (artista o entusiasta)

### 4. **Data Access Layer**
- **Ubicación**: `repository/`
- **Responsabilidad**: Persistencia en BD
- **Tecnología**: Spring Data JPA con PostgreSQL
- **Queries Personalizadas**:
  - Obtener canciones por artista
  - Búsqueda por género
  - Listar playlists de usuario

### 5. **Infrastructure Layer**
- **Ubicación**: `config/`
- **Componentes**:
  - `SecurityConfig`: JWT + roles (ARTIST, ENTHUSIAST)
  - `JwtProvider`: Generación y validación de tokens
  - `CorsConfiguration`: CORS para frontend development

## 🎵 Modelos de Datos

### Relaciones
```
User (1) ──────────────(N) Song
 ├─ Artist
 └─ Enthusiast

User (1) ──────────────(N) Playlist
 │                          │
 │                   (N)────┴─ (N)
 │                          ├─ Song
 └──────────────────(1)─────┘
                    Creador
```

### Entidades Clave

#### Song
```java
@Entity
public class Song {
    Long id;
    String title;
    String description;
    Artist artist;        // FK: quién publica
    String genre;
    Integer duration;     // en segundos
    LocalDateTime createdAt;
}
```

#### Playlist
```java
@Entity
public class Playlist {
    Long id;
    String name;
    User owner;           // FK: quién la creó
    Set<Song> songs;      // Many-to-Many
    LocalDateTime createdAt;
}
```

## 🔐 Autenticación y Autorización

### Flujo de Login
```
1. POST /artist/auth o /enthusiast/auth
2. Validar credenciales (email + password)
3. Generar JWT con claims:
   - sub: email
   - role: ARTIST o ENTHUSIAST
   - exp: 10 días
4. Retornar token al cliente
5. Cliente incluye token en Authorization header
```

### Protección de Endpoints

| Endpoint | Público | Requiere JWT | Role |
|----------|---------|--------------|------|
| `GET /songs` | ✅ | - | - |
| `POST /songs` | ❌ | ✅ | ARTIST |
| `PUT /songs/{id}` | ❌ | ✅ | ARTIST (dueño) |
| `POST /playlists` | ❌ | ✅ | ARTIST, ENTHUSIAST |
| `POST /playlists/{id}/songs` | ❌ | ✅ | ENTHUSIAST (dueño) |

## 🎯 Flujos Principales

### Crear Canción (Artista)
```
POST /songs
├─ ArtistController.createSong(SongDTO)
├─ SongService.create(dto, artistId)
│  ├─ Valida usuario es artista
│  ├─ Valida campos obligatorios
│  └─ Persiste Song con artist FK
└─ HTTP 201 + SongDTO
```

### Añadir Canción a Playlist (Entusiasta)
```
POST /playlists/{playlistId}/songs/{songId}
├─ PlaylistController.addSong()
├─ PlaylistService.addSong()
│  ├─ Valida playlist existe y es de usuario
│  ├─ Valida canción existe
│  ├─ Valida canción no esté ya en playlist
│  └─ Añade relación Many-to-Many
└─ HTTP 200
```

### Listar Canciones (Público)
```
GET /songs?genre=rock&page=0
├─ SongController.listSongs()
├─ SongRepository.findByGenre("rock", Pageable)
│  └─ Query: SELECT * FROM songs WHERE genre = ?
└─ HTTP 200 + Page<SongDTO>
```

## 🧪 Testing Strategy

### Unit Tests
- Mock de repository
- Test de lógica de negocio
- Edge cases (validaciones)

### Integration Tests
- Controladores con MockMvc
- Tests con H2 en memoria
- Autenticación JWT

### Ejemplo
```java
@SpringBootTest
class SongServiceTest {
    @Mock
    private SongRepository songRepository;
    
    @InjectMocks
    private SongService songService;
    
    @Test
    void shouldCreateSongWithArtist() {
        // Given
        Artist artist = Artist.builder().id(1L).build();
        CreateSongDTO dto = new CreateSongDTO("My Song", "Rock", 240);
        
        // When
        SongDTO result = songService.create(dto, artist.getId());
        
        // Then
        assertNotNull(result.getId());
        assertEquals("My Song", result.getTitle());
    }
}
```

## 📊 Performance Considerations

- **Índices**: En `artist_id`, `genre`, `created_at`
- **N+1 Problem**: Usar `@Query` con `@EntityGraph` para eager loading
- **Pagination**: Siempre en listados
- **Cache**: Redis ready (annotations disponibles)

## 🔄 Versionado

- URL prefix: `/api/v1/`
- Backward compatibility: 2 versiones mínimo
- Breaking changes documentadas en CHANGELOG

## 🚀 Roadmap Futuro

- [ ] Full-text search en canciones
- [ ] Recomendaciones basadas en género favorito
- [ ] Social features (seguir artistas, comentarios)
- [ ] Upload de audio a S3
- [ ] Streaming real-time
