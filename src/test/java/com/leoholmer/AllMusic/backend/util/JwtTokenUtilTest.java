package com.leoholmer.AllMusic.backend.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class JwtTokenUtilTest {

    private JwtTokenUtil jwtTokenUtil;

    @BeforeEach
    public void setup() throws Exception {
        jwtTokenUtil = new JwtTokenUtil();
        // Inyectamos la clave secreta de test mediante reflexión
        Field secretField = JwtTokenUtil.class.getDeclaredField("secretKey");
        secretField.setAccessible(true);
        secretField.set(jwtTokenUtil, "TestSecretKey1234567890");
    }

    @Test
    public void testGenerateAndVerifyToken() {
        String subject = "testUser";
        String token = jwtTokenUtil.generateToken(subject);

        assertNotNull(token, "El token generado no debe ser nulo");
        assertTrue(jwtTokenUtil.verify(token), "El token debe ser válido");
        assertEquals(subject, jwtTokenUtil.getSubject(token), "El subject del token debe coincidir");
    }

    @Test
    public void testInvalidToken() {
        String invalidToken = "Bearer invalid.token.here";
        assertFalse(jwtTokenUtil.verify(invalidToken), "El token inválido no debe verificarse");
    }
}
