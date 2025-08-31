package com.qtick.mis.security;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for TenantContext.
 */
class TenantContextTest {

    @Test
    void shouldCreateTenantContextWithAllFields() {
        // Given
        Long userId = 123L;
        Long bizId = 456L;
        Set<Long> branchIds = Set.of(1L, 2L, 3L);
        Set<String> roles = Set.of("ADMIN", "MANAGER");
        String timezone = "Asia/Singapore";
        String subject = "user123";

        // When
        TenantContext context = new TenantContext(userId, bizId, branchIds, roles, timezone, subject);

        // Then
        assertEquals(userId, context.getUserId());
        assertEquals(bizId, context.getBizId());
        assertEquals(branchIds, context.getBranchIds());
        assertEquals(roles, context.getRoles());
        assertEquals(timezone, context.getTimezone());
        assertEquals(subject, context.getSubject());
    }

    @Test
    void shouldHandleNullValues() {
        // Given
        Long userId = 123L;
        Long bizId = 456L;

        // When
        TenantContext context = new TenantContext(userId, bizId, null, null, null, null);

        // Then
        assertEquals(userId, context.getUserId());
        assertEquals(bizId, context.getBizId());
        assertTrue(context.getBranchIds().isEmpty());
        assertTrue(context.getRoles().isEmpty());
        assertEquals("Asia/Singapore", context.getTimezone()); // Default timezone
        assertNull(context.getSubject());
    }

    @Test
    void shouldCheckRoleCorrectly() {
        // Given
        TenantContext context = new TenantContext(
            123L, 456L, Set.of(1L), Set.of("ADMIN", "MANAGER"), "UTC", "user123"
        );

        // Then
        assertTrue(context.hasRole("ADMIN"));
        assertTrue(context.hasRole("MANAGER"));
        assertFalse(context.hasRole("SALES_REP"));
        assertFalse(context.hasRole("VIEWER"));
    }

    @Test
    void shouldCheckBranchAccessCorrectly() {
        // Given
        TenantContext context = new TenantContext(
            123L, 456L, Set.of(1L, 2L), Set.of("ADMIN"), "UTC", "user123"
        );

        // Then
        assertTrue(context.hasAccessToBranch(1L));
        assertTrue(context.hasAccessToBranch(2L));
        assertFalse(context.hasAccessToBranch(3L));
    }

    @Test
    void shouldAllowAccessToAllBranchesWhenBranchIdsEmpty() {
        // Given
        TenantContext context = new TenantContext(
            123L, 456L, Set.of(), Set.of("ADMIN"), "UTC", "user123"
        );

        // Then
        assertTrue(context.hasAccessToBranch(1L));
        assertTrue(context.hasAccessToBranch(2L));
        assertTrue(context.hasAccessToBranch(999L));
    }

    @Test
    void shouldReturnImmutableCollections() {
        // Given
        Set<Long> branchIds = Set.of(1L, 2L);
        Set<String> roles = Set.of("ADMIN");
        TenantContext context = new TenantContext(123L, 456L, branchIds, roles, "UTC", "user123");

        // When & Then
        assertThrows(UnsupportedOperationException.class, () -> 
            context.getBranchIds().add(3L));
        assertThrows(UnsupportedOperationException.class, () -> 
            context.getRoles().add("MANAGER"));
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        TenantContext context = new TenantContext(
            123L, 456L, Set.of(1L), Set.of("ADMIN"), "UTC", "user123"
        );

        // When
        String toString = context.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("userId=123"));
        assertTrue(toString.contains("bizId=456"));
        assertTrue(toString.contains("branchIds=[1]"));
        assertTrue(toString.contains("roles=[ADMIN]"));
        assertTrue(toString.contains("timezone='UTC'"));
        assertTrue(toString.contains("subject='user123'"));
    }
}