package com.leoholmer.AllMusic.backend.service;

import com.leoholmer.AllMusic.backend.exception.UnauthorizedException;
import com.leoholmer.AllMusic.backend.model.User;
import com.leoholmer.AllMusic.backend.repository.UserRepository;
import com.leoholmer.AllMusic.backend.util.JwtTokenUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthorizationServiceImpTest {

    @InjectMocks
    private AuthorizationServiceImp authorizationServiceImp;

    @Mock
    private JwtTokenUtil jwtTokenUtil;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthorizeValidToken() {
        String tokenHeader = "Bearer valid.token";
        String token = "valid.token"; // token sin el prefijo
        String username = "testUser";

        // Se crea un usuario de prueba
        User user = new User("testUser", "password") {
            @Override
            public boolean canCreateSongs() {
                return false;
            }
        };

        when(jwtTokenUtil.verify(token)).thenReturn(true);
        when(jwtTokenUtil.getSubject(token)).thenReturn(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = authorizationServiceImp.authorize(tokenHeader);
        assertNotNull(result);
        assertEquals("testUser", result.getUsername());
    }

    @Test
    public void testAuthorizeMissingBearer() {
        // Se envía un token que no empieza con "Bearer "
        String tokenHeader = "invalid.token";

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            authorizationServiceImp.authorize(tokenHeader);
        });
        assertEquals("Invalid token", exception.getMessage());
    }

    @Test
    public void testAuthorizeInvalidToken() {
        String tokenHeader = "Bearer invalid.token";
        String token = "invalid.token";
        when(jwtTokenUtil.verify(token)).thenReturn(false);

        UnauthorizedException exception = assertThrows(UnauthorizedException.class, () -> {
            authorizationServiceImp.authorize(tokenHeader);
        });
        assertEquals("Invalid token", exception.getMessage());
    }
}
