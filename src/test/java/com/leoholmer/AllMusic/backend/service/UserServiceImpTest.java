package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.exception.UserNotFoundException;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImpTest {

    @InjectMocks
    private UserServiceImp userServiceImp;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByUsernameFound() throws Exception {
        User user = new User("testUser", "password") {
            @Override
            public boolean canCreateSongs() {
                return false;
            }
        };
        when(userRepository.findByUsername("testUser")).thenReturn(Optional.of(user));

        User result = userServiceImp.findByUsername("testUser");
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void testFindByUsernameNotFound() {
        when(userRepository.findByUsername("nonexistent")).thenReturn(Optional.empty());
        Exception exception = assertThrows(UserNotFoundException.class, () -> {
            userServiceImp.findByUsername("nonexistent");
        });
        assertEquals("User not found with username: nonexistent", exception.getMessage());
    }
}
