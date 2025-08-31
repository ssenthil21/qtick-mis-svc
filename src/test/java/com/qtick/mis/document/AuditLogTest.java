package com.qtick.mis.document;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for AuditLog document.
 */
class AuditLogTest {

    @Test
    void shouldCreateAuditLogWithRequiredFields() {
        // Given
        Long bizId = 123L;
        String action = "CREATE";
        String entityType = "ENQUIRY";
        Long entityId = 456L;
        Long userId = 789L;
        String userName = "Agent Smith";
        String severity = "INFO";
        String description = "New enquiry created";

        // When
        AuditLog auditLog = new AuditLog(bizId, action, entityType, entityId, 
                                       userId, userName, severity, description);

        // Then
        assertEquals(bizId, auditLog.getBizId());
        assertEquals(action, auditLog.getAction());
        assertEquals(entityType, auditLog.getEntityType());
        assertEquals(entityId, auditLog.getEntityId());
        assertEquals(userId, auditLog.getUserId());
        assertEquals(userName, auditLog.getUserName());
        assertEquals(severity, auditLog.getSeverity());
        assertEquals(description, auditLog.getDescription());
        assertTrue(auditLog.getSuccessful());
        assertNotNull(auditLog.getOldValues());
        assertNotNull(auditLog.getNewValues());
        assertNotNull(auditLog.getMetadata());
        assertTrue(auditLog.getOldValues().isEmpty());
        assertTrue(auditLog.getNewValues().isEmpty());
        assertTrue(auditLog.getMetadata().isEmpty());
    }

    @Test
    void shouldAddOldAndNewValues() {
        // Given
        AuditLog auditLog = new AuditLog(123L, "UPDATE", "CLIENT", 456L, 
                                       789L, "Agent", "INFO", "Client updated");

        // When
        auditLog.addOldValue("name", "John Doe");
        auditLog.addOldValue("phone", "9876543210");
        auditLog.addNewValue("name", "John Smith");
        auditLog.addNewValue("phone", "9876543211");

        // Then
        assertEquals(2, auditLog.getOldValues().size());
        assertEquals(2, auditLog.getNewValues().size());
        assertEquals("John Doe", auditLog.getOldValues().get("name"));
        assertEquals("9876543210", auditLog.getOldValues().get("phone"));
        assertEquals("John Smith", auditLog.getNewValues().get("name"));
        assertEquals("9876543211", auditLog.getNewValues().get("phone"));
    }

    @Test
    void shouldAddMetadata() {
        // Given
        AuditLog auditLog = new AuditLog(123L, "DELETE", "BILL", 456L, 
                                       789L, "Manager", "WARN", "Bill deleted");

        // When
        auditLog.addMetadata("reason", "Duplicate entry");
        auditLog.addMetadata("approvedBy", "Supervisor");
        auditLog.addMetadata("billAmount", 1500.00);

        // Then
        assertEquals(3, auditLog.getMetadata().size());
        assertEquals("Duplicate entry", auditLog.getMetadata().get("reason"));
        assertEquals("Supervisor", auditLog.getMetadata().get("approvedBy"));
        assertEquals(1500.00, auditLog.getMetadata().get("billAmount"));
    }

    @Test
    void shouldMarkAsError() {
        // Given
        AuditLog auditLog = new AuditLog(123L, "CREATE", "APPOINTMENT", 456L, 
                                       789L, "Staff", "INFO", "Appointment creation");
        String errorMessage = "Database connection failed";
        String stackTrace = "java.sql.SQLException: Connection timeout";

        // When
        auditLog.markAsError(errorMessage, stackTrace);

        // Then
        assertFalse(auditLog.getSuccessful());
        assertEquals(errorMessage, auditLog.getErrorMessage());
        assertEquals(stackTrace, auditLog.getStackTrace());
        assertEquals("ERROR", auditLog.getSeverity());
    }

    @Test
    void shouldIdentifySecurityEvents() {
        // Given
        AuditLog securityEvent1 = new AuditLog();
        securityEvent1.setEntityType("SECURITY");

        AuditLog securityEvent2 = new AuditLog();
        securityEvent2.setAction("LOGIN");

        AuditLog securityEvent3 = new AuditLog();
        securityEvent3.setAction("LOGOUT");

        AuditLog securityEvent4 = new AuditLog();
        securityEvent4.setAction("ACCESS_DENIED");

        AuditLog securityEvent5 = new AuditLog();
        securityEvent5.setAction("PERMISSION_VIOLATION");

        AuditLog regularEvent = new AuditLog();
        regularEvent.setEntityType("ENQUIRY");
        regularEvent.setAction("CREATE");

        // When & Then
        assertTrue(securityEvent1.isSecurityEvent());
        assertTrue(securityEvent2.isSecurityEvent());
        assertTrue(securityEvent3.isSecurityEvent());
        assertTrue(securityEvent4.isSecurityEvent());
        assertTrue(securityEvent5.isSecurityEvent());
        assertFalse(regularEvent.isSecurityEvent());
    }

    @Test
    void shouldSetAllFields() {
        // Given
        AuditLog auditLog = new AuditLog();

        // When
        auditLog.setId("audit123");
        auditLog.setBizId(123L);
        auditLog.setBranchId(456L);
        auditLog.setAction("UPDATE");
        auditLog.setEntityType("CLIENT");
        auditLog.setEntityId(789L);
        auditLog.setEntityName("John Doe");
        auditLog.setUserId(101L);
        auditLog.setUserName("Agent Smith");
        auditLog.setUserRole("SALES_REP");
        auditLog.setSeverity("INFO");
        auditLog.setDescription("Client profile updated");
        auditLog.setIpAddress("192.168.1.100");
        auditLog.setUserAgent("Mozilla/5.0");
        auditLog.setSessionId("session123");
        auditLog.setCorrelationId("corr456");
        auditLog.setRequestPath("/api/v1/clients/789");
        auditLog.setHttpMethod("PATCH");
        auditLog.setJwtSubject("user101");
        auditLog.setTenantContext("biz123");
        auditLog.setSuccessful(true);

        LocalDateTime now = LocalDateTime.now();
        auditLog.setCreatedAt(now);

        Map<String, Object> oldValues = new HashMap<>();
        oldValues.put("phone", "9876543210");
        auditLog.setOldValues(oldValues);

        Map<String, Object> newValues = new HashMap<>();
        newValues.put("phone", "9876543211");
        auditLog.setNewValues(newValues);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("source", "web");
        auditLog.setMetadata(metadata);

        // Then
        assertEquals("audit123", auditLog.getId());
        assertEquals(123L, auditLog.getBizId());
        assertEquals(456L, auditLog.getBranchId());
        assertEquals("UPDATE", auditLog.getAction());
        assertEquals("CLIENT", auditLog.getEntityType());
        assertEquals(789L, auditLog.getEntityId());
        assertEquals("John Doe", auditLog.getEntityName());
        assertEquals(101L, auditLog.getUserId());
        assertEquals("Agent Smith", auditLog.getUserName());
        assertEquals("SALES_REP", auditLog.getUserRole());
        assertEquals("INFO", auditLog.getSeverity());
        assertEquals("Client profile updated", auditLog.getDescription());
        assertEquals("192.168.1.100", auditLog.getIpAddress());
        assertEquals("Mozilla/5.0", auditLog.getUserAgent());
        assertEquals("session123", auditLog.getSessionId());
        assertEquals("corr456", auditLog.getCorrelationId());
        assertEquals("/api/v1/clients/789", auditLog.getRequestPath());
        assertEquals("PATCH", auditLog.getHttpMethod());
        assertEquals("user101", auditLog.getJwtSubject());
        assertEquals("biz123", auditLog.getTenantContext());
        assertTrue(auditLog.getSuccessful());
        assertEquals(now, auditLog.getCreatedAt());
        assertEquals(1, auditLog.getOldValues().size());
        assertEquals(1, auditLog.getNewValues().size());
        assertEquals(1, auditLog.getMetadata().size());
    }

    @Test
    void shouldHandleNullCollections() {
        // Given
        AuditLog auditLog = new AuditLog();

        // When
        auditLog.setOldValues(null);
        auditLog.setNewValues(null);
        auditLog.setMetadata(null);

        // Then
        assertNotNull(auditLog.getOldValues());
        assertNotNull(auditLog.getNewValues());
        assertNotNull(auditLog.getMetadata());
        assertTrue(auditLog.getOldValues().isEmpty());
        assertTrue(auditLog.getNewValues().isEmpty());
        assertTrue(auditLog.getMetadata().isEmpty());
    }

    @Test
    void shouldAddToNullCollections() {
        // Given
        AuditLog auditLog = new AuditLog();
        auditLog.setOldValues(null);
        auditLog.setNewValues(null);
        auditLog.setMetadata(null);

        // When
        auditLog.addOldValue("key1", "value1");
        auditLog.addNewValue("key2", "value2");
        auditLog.addMetadata("key3", "value3");

        // Then
        assertEquals(1, auditLog.getOldValues().size());
        assertEquals(1, auditLog.getNewValues().size());
        assertEquals(1, auditLog.getMetadata().size());
        assertEquals("value1", auditLog.getOldValues().get("key1"));
        assertEquals("value2", auditLog.getNewValues().get("key2"));
        assertEquals("value3", auditLog.getMetadata().get("key3"));
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        AuditLog auditLog = new AuditLog(123L, "CREATE", "ENQUIRY", 456L, 
                                       789L, "Agent", "INFO", "Enquiry created");
        auditLog.setId("audit123");
        auditLog.setCreatedAt(LocalDateTime.now());

        // When
        String toString = auditLog.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id='audit123'"));
        assertTrue(toString.contains("bizId=123"));
        assertTrue(toString.contains("action='CREATE'"));
        assertTrue(toString.contains("entityType='ENQUIRY'"));
        assertTrue(toString.contains("entityId=456"));
        assertTrue(toString.contains("userId=789"));
        assertTrue(toString.contains("severity='INFO'"));
        assertTrue(toString.contains("successful=true"));
    }

    @Test
    void shouldDefaultToSuccessfulTrue() {
        // Given & When
        AuditLog auditLog = new AuditLog();

        // Then
        assertTrue(auditLog.getSuccessful());
    }
}