package com.qtick.mis.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.oauth2.jwt.Jwt;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TenantContextHolder.
 */
class TenantContextHolderTest {

    @AfterEach
    void cleanup() {
        TenantContextHolder.clearContext();
    }

    @Test
    void shouldSetAndGetContext() {
        // Given
        TenantContext context = new TenantContext(
            123L, 456L, Set.of(1L), Set.of("ADMIN"), "UTC", "user123"
        );

        // When
        TenantContextHolder.setContext(context);
        TenantContext retrieved = TenantContextHolder.getContext();

        // Then
        assertSame(context, retrieved);
    }

    @Test
    void shouldClearContext() {
        // Given
        TenantContext context = new TenantContext(
            123L, 456L, Set.of(1L), Set.of("ADMIN"), "UTC", "user123"
        );
        TenantContextHolder.setContext(context);

        // When
        TenantContextHolder.clearContext();

        // Then
        assertNull(TenantContextHolder.getContext());
    }

    @Test
    void shouldExtractFromJwtWithAllClaims() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "user123",
            "userId", 123L,
            "bizId", 456L,
            "branchIds", List.of(1L, 2L, 3L),
            "roles", List.of("ADMIN", "MANAGER"),
            "timezone", "Asia/Singapore"
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertEquals(123L, context.getUserId());
        assertEquals(456L, context.getBizId());
        assertEquals(Set.of(1L, 2L, 3L), context.getBranchIds());
        assertEquals(Set.of("ADMIN", "MANAGER"), context.getRoles());
        assertEquals("Asia/Singapore", context.getTimezone());
        assertEquals("user123", context.getSubject());
    }

    @Test
    void shouldExtractFromJwtWithMinimalClaims() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "user123",
            "bizId", 456L
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertNull(context.getUserId()); // No userId claim, sub is not numeric
        assertEquals(456L, context.getBizId());
        assertTrue(context.getBranchIds().isEmpty());
        assertTrue(context.getRoles().isEmpty());
        assertEquals("Asia/Singapore", context.getTimezone()); // Default
        assertEquals("user123", context.getSubject());
    }

    @Test
    void shouldExtractUserIdFromSubjectWhenNumeric() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "123",
            "bizId", 456L
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertEquals(123L, context.getUserId()); // Extracted from numeric subject
        assertEquals(456L, context.getBizId());
        assertEquals("123", context.getSubject());
    }

    @Test
    void shouldHandleStringNumberClaims() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "user123",
            "userId", "123",
            "bizId", "456",
            "branchIds", List.of("1", "2", "3"),
            "roles", List.of("ADMIN", "MANAGER")
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertEquals(123L, context.getUserId());
        assertEquals(456L, context.getBizId());
        assertEquals(Set.of(1L, 2L, 3L), context.getBranchIds());
        assertEquals(Set.of("ADMIN", "MANAGER"), context.getRoles());
    }

    @Test
    void shouldHandleSingleStringRole() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "user123",
            "bizId", 456L,
            "roles", "ADMIN"
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertEquals(Set.of("ADMIN"), context.getRoles());
    }

    @Test
    void shouldHandleInvalidNumberFormats() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "user123",
            "userId", "invalid",
            "bizId", "also-invalid",
            "branchIds", List.of("1", "invalid", "3")
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertNull(context.getUserId());
        assertNull(context.getBizId());
        assertEquals(Set.of(1L, 3L), context.getBranchIds()); // Invalid entries filtered out
    }

    @Test
    void shouldHandleMixedTypeCollections() {
        // Given
        Map<String, Object> claims = Map.of(
            "sub", "user123",
            "bizId", 456L,
            "branchIds", List.of(1L, "2", 3.0, "invalid"),
            "roles", List.of("ADMIN", 123, "MANAGER") // Non-string entries should be filtered
        );
        
        Jwt jwt = createJwt(claims);

        // When
        TenantContext context = TenantContextHolder.extractFromJwt(jwt);

        // Then
        assertEquals(Set.of(1L, 2L, 3L), context.getBranchIds());
        assertEquals(Set.of("ADMIN", "MANAGER"), context.getRoles());
    }

    private Jwt createJwt(Map<String, Object> claims) {
        return Jwt.withTokenValue("token")
            .header("alg", "RS256")
            .claims(claimsMap -> claimsMap.putAll(claims))
            .issuedAt(Instant.now())
            .expiresAt(Instant.now().plusSeconds(3600))
            .build();
    }
}