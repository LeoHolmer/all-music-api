# 🤝 Contribution Guide for AllMusic

Thank you for your interest in contributing to **AllMusic**! 🎵  
While this is an academic and educational project, we truly value all contributions that help improve the code, documentation, and overall developer experience.

## 📋 Table of Contents

- [🚀 Getting Started](#-getting-started)  
- [🐛 Reporting Bugs](#-reporting-bugs)  
- [✨ Suggesting Features](#-suggesting-features)  
- [🔧 Environment Setup](#-environment-setup)  
- [💻 Development](#-development)  
- [✅ Pull Requests](#-pull-requests)  
- [🎯 Code Standards](#-code-standards)  
- [🧪 Testing](#-testing)  
- [📚 Documentation](#-documentation)

---

## 🚀 Getting Started

### 1. Fork and Clone

```bash
# Fork the repository on GitHub
# Then clone your fork
git clone https://github.com/YOUR_USERNAME/AllMusic.git
cd AllMusic
```

### 2. Set Up Upstream

```bash
# Add the original repository as upstream
git remote add upstream https://github/LeoHolmer/AllMusic.git

# Verify your remotes
git remote -v
```

### 3. Create a Branch

```bash
# Create and switch to a new feature branch
git checkout -b feature/new-feature

# Or for bug fixes
git checkout -b bugfix/fix-issue
```

---

## 🐛 Reporting Bugs

If you find a bug, please:

1. **Check** that it hasn’t already been reported  
2. **Use the bug report template** available in GitHub Issues  
3. **Provide** all relevant details:
   - Steps to reproduce  
   - Expected vs. actual behavior  
   - Error logs  
   - Environment (OS, Java version, etc.)

---

## ✨ Suggesting Features

For new features:

1. **Review** existing discussions to avoid duplicates  
2. **Use the feature request template**  
3. **Clearly describe**:
   - The problem it solves  
   - Your proposed solution  
   - Its potential impact on the system

---

## 🔧 Environment Setup

### Prerequisites

- **Java 21** or higher  
- **Maven 3.9+**  
- **PostgreSQL 18.1**  
- **Git**

### Installation

1. **Install Java 21:**
   ```bash
   # Ubuntu/Debian
   sudo apt update
   sudo apt install openjdk-21-jdk

   # macOS (with Homebrew)
   brew install openjdk@21

   # Windows: Download from oracle.com
   ```

2. **Install Maven:**
   ```bash
   # Ubuntu/Debian
   sudo apt install maven

   # macOS
   brew install maven

   # Windows: Download from maven.apache.org
   ```

3. **Install PostgreSQL:**
   ```bash
   # Ubuntu/Debian
   sudo apt install postgresql postgresql-contrib

   # macOS
   brew install postgresql

   # Windows: Download from postgresql.org
   ```

### Database Setup

```sql
-- Create database
CREATE DATABASE allmusic_db;

-- Create user
CREATE USER allmusic_user WITH PASSWORD 'your_secure_password';

-- Grant privileges
GRANT ALL PRIVILEGES ON DATABASE allmusic_db TO allmusic_user;
```

### Environment Variables

Create a `.env` file in the project root:

```env
DB_URL=jdbc:postgresql://localhost:5432/allmusic_db
DB_USERNAME=allmusic_user
DB_PASSWORD=your_secure_password
JWT_SECRET=your_very_secure_jwt_secret_here
```

---

## 💻 Development

### Project Structure

```
AllMusic/
├── src/
│   ├── main/
│   │   ├── java/com/leoholmer/AllMusic/backend/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── model/           # JPA entities
│   │   │   ├── repository/      # Data repositories
│   │   │   ├── service/         # Business logic
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   └── exception/       # Exception handling
│   │   └── resources/           # application.properties, etc.
│   └── test/                    # Tests
├── .github/                     # GitHub Actions & issue/PR templates
├── target/                      # Compiled files (ignored)
└── pom.xml                      # Maven dependencies
```

### Useful Commands

```bash
# Compile
./mvnw clean compile

# Run tests
./mvnw test

# Run the application
./mvnw spring-boot:run

# Full build
./mvnw clean package

# Run with a specific profile
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

---

## ✅ Pull Requests

### Before Submitting a PR

- [ ] **All tests pass**  
- [ ] **Code compiles** without warnings  
- [ ] **Documentation is updated** (README, Swagger)  
- [ ] **Commits follow** Conventional Commits  
- [ ] **Branch is up to date** with `main`

### PR Workflow

1. **Push your branch:**
   ```bash
   git push origin feature/new-feature
   ```

2. **Open a Pull Request** on GitHub using the provided template  

3. **Wait for review** from a maintainer  

4. **Address feedback** if requested  

5. **Merge** once approved

### Conventional Commits

We use Conventional Commits to maintain a clear history:

```bash
# Features
feat: add user authentication

# Bug fixes
fix: resolve null pointer in song service

# Documentation
docs: update API documentation

# Tests
test: add unit tests for playlist service

# Refactoring
refactor: simplify song validation logic
```

---

## 🎯 Code Standards

### Java

- **Version:** Java 21 LTS  
- **Indentation:** 4 spaces  
- **Naming:** camelCase for variables/methods, PascalCase for classes  
- **Lombok:** Use `@Data`, `@Builder`, etc., as appropriate  
- **Validation:** Apply Bean Validation to DTOs

### Code Example

```java
@RestController
@RequestMapping("/songs")
@Tag(name = "Songs", description = "API for managing songs")
@SecurityRequirement(name = "bearerAuth")
public class SongController {

    @Autowired
    private SongService songService;

    @GetMapping
    @Operation(summary = "List all songs")
    public ResponseEntity<List<SongDTO>> getAllSongs() {
        List<SongDTO> songs = songService.getAllSongs();
        return ResponseEntity.ok(songs);
    }
}
```

### DTOs

```java
@Data
@Builder
public class SongDTO {
    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Artist is required")
    private String artist;

    @Min(value = 1, message = "Duration must be greater than 0")
    private Integer duration;
}
```

---

## 🧪 Testing

### Types of Tests

- **Unit Tests:** For isolated business logic  
- **Integration Tests:** For REST endpoints and database interactions  
- **Security Tests:** For authentication and authorization flows

### Running Tests

```bash
# All tests
./mvnw test

# Specific test class
./mvnw test -Dtest=SongServiceTest

# With coverage report
./mvnw test jacoco:report
```

### Writing Tests

```java
@SpringBootTest
class SongServiceTest {

    @Autowired
    private SongService songService;

    @Test
    void shouldCreateSongSuccessfully() {
        // Given
        SongDTO songDTO = SongDTO.builder()
                .title("Test Song")
                .artist("Test Artist")
                .duration(180)
                .build();

        // When
        SongDTO created = songService.createSong(songDTO);

        // Then
        assertThat(created.getId()).isNotNull();
        assertThat(created.getTitle()).isEqualTo("Test Song");
    }
}
```

---

## 📚 Documentation

### Swagger/OpenAPI

All endpoints must be documented using OpenAPI annotations:

```java
@Operation(summary = "Create a new song",
           description = "Allows artists to create a new song")
@ApiResponses(value = {
    @ApiResponse(responseCode = "201", description = "Song created successfully"),
    @ApiResponse(responseCode = "400", description = "Invalid input data"),
    @ApiResponse(responseCode = "401", description = "Unauthorized")
})
@PostMapping
public ResponseEntity<SongDTO> createSong(@Valid @RequestBody SongDTO songDTO) {
    // implementation
}
```

### README

- Keep the main README up to date  
- Document significant changes  
- Include usage examples

---

## 🎓 Additional Resources

### Official Documentation

- [Spring Boot Documentation](https://spring.io/projects/spring-boot)  
- [Spring Security](https://spring.io/projects/spring-security)  
- [JWT.io](https://jwt.io/)  
- [Swagger/OpenAPI](https://swagger.io/)

### Community

- [Stack Overflow - Spring Boot](https://stackoverflow.com/questions/tagged/spring-boot)  
- [Reddit - Java](https://www.reddit.com/r/java/)  
- [Discord - Spring Community](https://discord.gg/spring)

---

## 📞 Support

Need help? Feel free to:

- 📧 **Email:** leonardoholmer@gmail.com  
- 💬 **Issues:** For bugs and feature requests  
- 📖 **Discussions:** For general questions

---

**Thank you for contributing to AllMusic! Your help makes this academic project better for everyone. 🎵✨**

*Remember: This is an educational project. Let’s learn together!* 🎓