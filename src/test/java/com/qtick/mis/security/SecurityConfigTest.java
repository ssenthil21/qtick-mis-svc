package com.qtick.mis.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for SecurityConfig.
 */
@SpringBootTest
@AutoConfigureWebMvc
@ActiveProfiles("test")
class SecurityConfigTest {

    @Autowired
    private JwtDecoder jwtDecoder;

    @Autowired
    private JwtAuthenticationConverter jwtAuthenticationConverter;

    @Autowired
    private SecurityFilterChain securityFilterChain;

    @Test
    void shouldCreateJwtDecoder() {
        assertNotNull(jwtDecoder);
    }

    @Test
    void shouldCreateJwtAuthenticationConverter() {
        assertNotNull(jwtAuthenticationConverter);
    }

    @Test
    void shouldCreateSecurityFilterChain() {
        assertNotNull(securityFilterChain);
    }
}