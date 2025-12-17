# 🎵 AllMusic - REST API

<div align="center">

![Java](https://img.shields.io/badge/Java-21-orange?style=for-the-badge&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.4.2-green?style=for-the-badge&logo=spring-boot)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-18.1-blue?style=for-the-badge&logo=postgresql)
![JWT](https://img.shields.io/badge/JWT-4.5.0-red?style=for-the-badge&logo=json-web-tokens)
![Swagger](https://img.shields.io/badge/Swagger-2.8.0-brightgreen?style=for-the-badge&logo=swagger)

**A RESTful API for music management with JWT authentication**

*Academic Project – Object-Oriented Programming 2024 – UNNOBA*

[📖 API Documentation](#-api-documentation) • [🚀 Quick Start](#-installation-and-setup) • [🔧 Technologies](#-technologies-used) • [📋 Endpoints](#-main-endpoints)

</div>

---

## 📋 Project Description

**AllMusic** is a RESTful API built with **Java and Spring Boot**, implementing a musical ecosystem where **musical artists** and **music enthusiasts** coexist. Artists can create and manage their own songs, while all users can build playlists using songs from any artist.

### 🎭 User Roles

- **🎤 Musical Artist**: Can create, update, and delete their own songs  
- **🎧 Music Enthusiast**: Can consume content and organize playlists

### ✨ Key Features

- 🔐 Secure **JWT-based authentication** with user roles  
- 🎵 Full **song management** (CRUD operations)  
- 📋 **Collaborative playlist system**  
- 🔒 **Resource ownership-based access control**  
- 📖 **Interactive API documentation** via Swagger UI  
- 🌐 **CORS configuration** ready for frontend development  
- ✅ Comprehensive **input validation**

---

## 🛠️ Technologies Used

### Core Backend
- **Java** 21 LTS  
- **Spring Boot** 3.4.2  
- **Spring Security** 6.4.2  
- **Spring Data JPA** 3.4.2  

### Database & Persistence
- **PostgreSQL** 18.1  
- **Hibernate** 6.6.5 (ORM)  
- **HikariCP** (Connection Pool)  

### Security & Authentication
- **JWT (JSON Web Token)** 4.5.0  
- **Password4j** 1.8.4 (Secure password hashing)  
- **Auth0 Java SDK** 2.26.0  

### Documentation & Utilities
- **SpringDoc OpenAPI** 2.8.0 (Swagger integration)  
- **ModelMapper** 3.2.6  
- **Bean Validation** (Jakarta)  
- **Kotlin Runtime** 1.9.25 (required by Auth0)  

### Development Tools
- **Maven** 3.9+  
- **JUnit 5** (Testing)  
- **Lombok** (Boilerplate reduction)  

---

## 🚀 Installation and Setup

### Prerequisites

- **Java 21** or higher  
- **Maven 3.9+**  
- **PostgreSQL 18.1**  
- **Git**

### 1. Clone the Repository

```bash
git clone https://github.com/LeoHolmer/AllMusic.git
cd AllMusic
```

### 2. Configure the Database

Create a PostgreSQL database and update the configuration in `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/allmusic_db
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA Configuration
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

### 3. Build the Project

```bash
# Using Maven Wrapper (recommended)
./mvnw clean compile

# Or with installed Maven
mvn clean compile
```

### 4. Run the Application

```bash
# Using Maven Wrapper
./mvnw spring-boot:run

# Or with installed Maven
mvn spring-boot:run

# Or run the JAR directly
java -jar target/AllMusic-0.0.1-SNAPSHOT.jar
```

### 5. Verify the Installation

The application will be available at: **http://localhost:8080**

**Verification Endpoints:**
- **Health Check**: `http://localhost:8080/actuator/health`  
- **Swagger UI**: `http://localhost:8080/swagger-ui.html`

---

## 📖 API Documentation

### Swagger UI (Interactive Interface)

Access the complete, interactive documentation at:  
**http://localhost:8080/swagger-ui.html**

### OpenAPI Specification

Download the OpenAPI spec (JSON format) at:  
**http://localhost:8080/v3/api-docs**

### Authentication

To access protected endpoints, include the JWT token in the `Authorization` header:

```
Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...
```

#### Obtain a JWT Token

```bash
# Artist Login
curl -X POST http://localhost:8080/artist/auth \
  -H "Content-Type: application/json" \
  -d '{"username": "artista1", "password": "password123"}'

# Enthusiast Login
curl -X POST http://localhost:8080/enthusiast/auth \
  -H "Content-Type: application/json" \
  -d '{"username": "entusiasta1", "password": "password123"}'
```

---

## 📋 Main Endpoints

### 🔐 Authentication

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `POST` | `/artist/auth` | Artist login | Public |
| `POST` | `/enthusiast/auth` | Enthusiast login | Public |

### 🎵 Song Management (Artists Only)

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| `GET` | `/songs` | List all songs | JWT |
| `GET` | `/songs/{id}` | Get song by ID | JWT |
| `POST` | `/songs` | Create a new song | JWT (Artist) |
| `PUT` | `/songs/{id}` | Update a song | JWT (Owner) |
| `DELETE` | `/songs/{id}` | Delete a song | JWT (Owner) |

### 📋 Playlist Management (All Users)

| Method | Endpoint | Description | Authentication |
|--------|----------|-------------|----------------|
| `GET` | `/playlists` | List all playlists | JWT |
| `GET` | `/playlists/{id}` | Get playlist by ID | JWT |
| `POST` | `/playlists` | Create a new playlist | JWT |
| `PUT` | `/playlists/{id}` | Update a playlist | JWT (Owner) |
| `DELETE` | `/playlists/{id}` | Delete a playlist | JWT (Owner) |
| `POST` | `/playlists/{id}/songs` | Add song to playlist | JWT (Owner) |
| `DELETE` | `/playlists/{id}/songs/{songId}` | Remove song from playlist | JWT (Owner) |

### 🔧 Monitoring

| Method | Endpoint | Description | Access |
|--------|----------|-------------|--------|
| `GET` | `/actuator/health` | Application health status | Public |
| `GET` | `/actuator/info` | Application info | Public |

---

## 🧪 Testing

### Run Tests

```bash
# Run all tests
./mvnw test

# Run tests with coverage report
./mvnw test jacoco:report
```

### Included Tests

- ✅ **Unit Tests**: Business logic and utilities  
- ✅ **Integration Tests**: REST endpoints and database interactions  
- ✅ **Security Tests**: Authentication and authorization flows  

---

## 🤝 Contribution

1. **Fork** the repository  
2. Create a **feature branch** (`git checkout -b feature/AmazingFeature`)  
3. **Commit** your changes (`git commit -m 'Add some AmazingFeature'`)  
4. **Push** to the branch (`git push origin feature/AmazingFeature`)  
5. Open a **Pull Request**

### Code Standards

- Follow standard **Java conventions**  
- Use **Lombok** to reduce boilerplate  
- Write **tests** for new functionality  
- Update **documentation** when necessary  

---

## 📝 License

This project is for **educational purposes only** and is part of the **Object-Oriented Programming 2024** course at the **National University of the Northwest of the Province of Buenos Aires (UNNOBA)**.

**Not intended for commercial use.**

---

## 👨‍💻 Author

**Leonardo Holmer**  
- 📧 Email: leonardoholmer1@gmail.com  
- 🎓 UNNOBA Student – Object-Oriented Programming 2024  
- 📍 Buenos Aires Province, Argentina  

---

<div align="center">

**Ready to make music! 🎸🎶**

⭐ If you like this project, give it a star!

*Developed with ❤️ in Java and Spring Boot*

</div>