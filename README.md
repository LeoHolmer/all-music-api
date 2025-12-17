# 🎵 All Music – RESTful Backend

**All Music** is a backend developed in **Java with Spring Boot**, implementing a RESTful API to manage a music ecosystem where **artists** and **enthusiasts** coexist. Artists can create and manage their own songs, while all users can create and manage playlists containing songs from any artist.

This project is part of the **Object-Oriented Programming (2024)** course at the **National University of the Northwest of the Province of Buenos Aires (UNNOBA)**.

---

## 📌 Key Features

- ✅ **Authentication and authorization** based on **JWT** (JSON Web Token).
- 🎤 **Song management**: only artists can create, update, or delete their own songs.
- 📋 **Playlist management**: any user can create playlists and add songs from other artists.
- 🔒 **Access control**: only the owners of resources can modify or delete them.
- 🎭 **Two user types**:
  - **Music Artist**: can create songs.
  - **Music Enthusiast**: can only consume and organize content.

---

## 🛠️ Technologies Used

- **Language**: Java 17+
- **Framework**: Spring Boot 3.x
- **Persistence**: Spring Data JPA + Hibernate
- **Database**: H2 (in-memory) or any JPA-compatible database
- **Security**: JWT + Bcrypt (via Password4j)
- **DTO Mapping**: ModelMapper
- **Validation**: Bean Validation (built into Spring)
- **Key Dependencies**:
  - `com.auth0:java-jwt`
  - `com.password4j:password4j`

## 🚀 How to Run the Project

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/all-music.git
   cd all-music
   ```

2. **Build with Maven**
   ```bash
   ./mvnw clean install
   ```

3. **Start the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   The server will run at `http://localhost:8080`.

4. **(Optional)** Modify `application.properties` to use a different database.

---

## 📝 License

This project is for educational purposes only and is not intended for commercial use.  
© 2024 – Object-Oriented Programming Chair, UNNOBA.

Ready to rock? 🎸  
Happy coding! 🎶
