package com.leoholmer.AllMusic.backend.util;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PasswordEncoderTest {

    private final PasswordEncoder passwordEncoder = new PasswordEncoder();

    @Test
    public void testEncodeAndVerify() {
        String rawPassword = "mySecretPassword";
        String encoded = passwordEncoder.encode(rawPassword);

        assertNotNull(encoded, "El hash generado no debe ser nulo");
        assertTrue(passwordEncoder.verify(rawPassword, encoded), "La contraseña debe verificar correctamente");
        assertFalse(passwordEncoder.verify("wrongPassword", encoded), "Una contraseña incorrecta no debe verificar");
    }
}
