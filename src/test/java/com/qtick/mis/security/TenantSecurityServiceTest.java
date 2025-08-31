package com.qtick.mis.security;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for TenantSecurityService.
 */
class TenantSecurityServiceTest {

    private TenantSecurityService tenantSecurityService;
    private TenantContext testContext;

    @BeforeEach
    void setUp() {
        tenantSecurityService = new TenantSecurityService();
        testContext = new TenantContext(
            123L, 456L, Set.of(1L, 2L), Set.of("ADMIN", "MANAGER"), "UTC", "user123"
        );
        TenantContextHolder.setContext(testContext);
    }

    @AfterEach
    void cleanup() {
        TenantContextHolder.clearContext();
    }

    @Test
    void shouldReturnTrueWhenUserHasRole() {
        // Given
        Authentication auth = createMockAuthentication(true);

        // When & Then
        assertTrue(tenantSecurityService.hasRole(auth, "ADMIN"));
        assertTrue(tenantSecurityService.hasRole(auth, "MANAGER"));
        assertFalse(tenantSecurityService.hasRole(auth, "SALES_REP"));
    }

    @Test
    void shouldReturnFalseWhenAuthenticationIsNull() {
        // When & Then
        assertFalse(tenantSecurityService.hasRole(null, "ADMIN"));
    }

    @Test
    void shouldReturnFalseWhenAuthenticationIsNotAuthenticated() {
        // Given
        Authentication auth = createMockAuthentication(false);

        // When & Then
        assertFalse(tenantSecurityService.hasRole(auth, "ADMIN"));
    }

    @Test
    void shouldReturnFalseWhenTenantContextIsNull() {
        // Given
        TenantContextHolder.clearContext();
        Authentication auth = createMockAuthentication(true);

        // When & Then
        assertFalse(tenantSecurityService.hasRole(auth, "ADMIN"));
    }

    @Test
    void shouldReturnTrueWhenUserHasAccessToBusiness() {
        // Given
        Authentication auth = createMockAuthentication(true);

        // When & Then
        assertTrue(tenantSecurityService.hasAccessToBusiness(auth, 456L));
        assertFalse(tenantSecurityService.hasAccessToBusiness(auth, 999L));
    }

    @Test
    void shouldReturnTrueWhenUserHasAccessToBranch() {
        // Given
        Authentication auth = createMockAuthentication(true);

        // When & Then
        assertTrue(tenantSecurityService.hasAccessToBranch(auth, 1L));
        assertTrue(tenantSecurityService.hasAccessToBranch(auth, 2L));
        assertFalse(tenantSecurityService.hasAccessToBranch(auth, 3L));
    }

    @Test
    void shouldReturnTrueWhenCanAccessData() {
        // Given
        Authentication auth = createMockAuthentication(true);

        // When & Then
        assertTrue(tenantSecurityService.canAccessData(auth, 456L, 1L));
        assertTrue(tenantSecurityService.canAccessData(auth, 456L, 2L));
        assertTrue(tenantSecurityService.canAccessData(auth, 456L, null)); // Business-level access
        assertFalse(tenantSecurityService.canAccessData(auth, 456L, 3L)); // No branch access
        assertFalse(tenantSecurityService.canAccessData(auth, 999L, 1L)); // No business access
    }

    @Test
    void shouldReturnCurrentTenantContext() {
        // When
        TenantContext context = tenantSecurityService.getCurrentTenantContext();

        // Then
        assertSame(testContext, context);
    }

    @Test
    void shouldValidateBusinessAccessSuccessfully() {
        // When & Then
        assertDoesNotThrow(() -> tenantSecurityService.validateBusinessAccess(456L));
    }

    @Test
    void shouldThrowExceptionWhenBusinessAccessDenied() {
        // When & Then
        SecurityException exception = assertThrows(SecurityException.class, 
            () -> tenantSecurityService.validateBusinessAccess(999L));
        assertTrue(exception.getMessage().contains("Access denied to business: 999"));
    }

    @Test
    void shouldThrowExceptionWhenTenantContextIsNullForBusinessValidation() {
        // Given
        TenantContextHolder.clearContext();

        // When & Then
        assertThrows(SecurityException.class, 
            () -> tenantSecurityService.validateBusinessAccess(456L));
    }

    @Test
    void shouldValidateBranchAccessSuccessfully() {
        // When & Then
        assertDoesNotThrow(() -> tenantSecurityService.validateBranchAccess(1L));
        assertDoesNotThrow(() -> tenantSecurityService.validateBranchAccess(2L));
    }

    @Test
    void shouldThrowExceptionWhenBranchAccessDenied() {
        // When & Then
        SecurityException exception = assertThrows(SecurityException.class, 
            () -> tenantSecurityService.validateBranchAccess(3L));
        assertTrue(exception.getMessage().contains("Access denied to branch: 3"));
    }

    @Test
    void shouldThrowExceptionWhenTenantContextIsNullForBranchValidation() {
        // Given
        TenantContextHolder.clearContext();

        // When & Then
        assertThrows(SecurityException.class, 
            () -> tenantSecurityService.validateBranchAccess(1L));
    }

    private Authentication createMockAuthentication(boolean isAuthenticated) {
        Authentication auth = mock(Authentication.class);
        when(auth.isAuthenticated()).thenReturn(isAuthenticated);
        return auth;
    }
}