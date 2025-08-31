package com.qtick.mis.document;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ActivityEvent document.
 */
class ActivityEventTest {

    @Test
    void shouldCreateActivityEventWithRequiredFields() {
        // Given
        Long bizId = 123L;
        String eventType = "ENQUIRY_CREATED";
        String entityType = "ENQUIRY";
        Long entityId = 456L;
        String title = "New enquiry created";
        String description = "John Doe submitted a new enquiry for haircut service";
        Long userId = 789L;
        String userName = "Agent Smith";

        // When
        ActivityEvent event = new ActivityEvent(bizId, eventType, entityType, entityId, 
                                              title, description, userId, userName);

        // Then
        assertEquals(bizId, event.getBizId());
        assertEquals(eventType, event.getEventType());
        assertEquals(entityType, event.getEntityType());
        assertEquals(entityId, event.getEntityId());
        assertEquals(title, event.getTitle());
        assertEquals(description, event.getDescription());
        assertEquals(userId, event.getUserId());
        assertEquals(userName, event.getUserName());
        assertNotNull(event.getMetadata());
        assertTrue(event.getMetadata().isEmpty());
    }

    @Test
    void shouldAddMetadataToEvent() {
        // Given
        ActivityEvent event = new ActivityEvent(123L, "BILL_CREATED", "BILL", 456L, 
                                              "Bill created", "New bill generated", 789L, "Staff Member");

        // When
        event.addMetadata("billAmount", 1500.00);
        event.addMetadata("paymentMode", "CASH");
        event.addMetadata("clientName", "John Doe");

        // Then
        assertEquals(3, event.getMetadata().size());
        assertEquals(1500.00, event.getMetadata("billAmount"));
        assertEquals("CASH", event.getMetadata("paymentMode"));
        assertEquals("John Doe", event.getMetadata("clientName"));
    }

    @Test
    void shouldGetMetadataValue() {
        // Given
        ActivityEvent event = new ActivityEvent();
        event.addMetadata("testKey", "testValue");

        // When
        Object value = event.getMetadata("testKey");
        Object nonExistentValue = event.getMetadata("nonExistent");

        // Then
        assertEquals("testValue", value);
        assertNull(nonExistentValue);
    }

    @Test
    void shouldHandleNullMetadata() {
        // Given
        ActivityEvent event = new ActivityEvent();
        event.setMetadata(null);

        // When
        event.addMetadata("key", "value");

        // Then
        assertNotNull(event.getMetadata());
        assertEquals(1, event.getMetadata().size());
        assertEquals("value", event.getMetadata("key"));
    }

    @Test
    void shouldSetAllFields() {
        // Given
        ActivityEvent event = new ActivityEvent();

        // When
        event.setId("event123");
        event.setBizId(123L);
        event.setBranchId(456L);
        event.setEventType("APPOINTMENT_SCHEDULED");
        event.setEntityType("APPOINTMENT");
        event.setEntityId(789L);
        event.setTitle("Appointment scheduled");
        event.setDescription("Haircut appointment scheduled for tomorrow");
        event.setUserId(101L);
        event.setUserName("Receptionist");
        event.setUserRole("STAFF");
        event.setClientName("Jane Doe");
        event.setClientPhone("9876543210");
        event.setServiceName("Haircut");
        event.setStaffName("Stylist John");
        event.setSource("Website");
        event.setChannel("Online");

        LocalDateTime now = LocalDateTime.now();
        event.setCreatedAt(now);

        Map<String, Object> metadata = new HashMap<>();
        metadata.put("appointmentDate", "2024-01-15T10:00:00");
        metadata.put("duration", 60);
        event.setMetadata(metadata);

        // Then
        assertEquals("event123", event.getId());
        assertEquals(123L, event.getBizId());
        assertEquals(456L, event.getBranchId());
        assertEquals("APPOINTMENT_SCHEDULED", event.getEventType());
        assertEquals("APPOINTMENT", event.getEntityType());
        assertEquals(789L, event.getEntityId());
        assertEquals("Appointment scheduled", event.getTitle());
        assertEquals("Haircut appointment scheduled for tomorrow", event.getDescription());
        assertEquals(101L, event.getUserId());
        assertEquals("Receptionist", event.getUserName());
        assertEquals("STAFF", event.getUserRole());
        assertEquals("Jane Doe", event.getClientName());
        assertEquals("9876543210", event.getClientPhone());
        assertEquals("Haircut", event.getServiceName());
        assertEquals("Stylist John", event.getStaffName());
        assertEquals("Website", event.getSource());
        assertEquals("Online", event.getChannel());
        assertEquals(now, event.getCreatedAt());
        assertEquals(2, event.getMetadata().size());
        assertEquals("2024-01-15T10:00:00", event.getMetadata("appointmentDate"));
        assertEquals(60, event.getMetadata("duration"));
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        ActivityEvent event = new ActivityEvent(123L, "ENQUIRY_CREATED", "ENQUIRY", 456L, 
                                              "New enquiry", "Description", 789L, "Agent");
        event.setId("event123");
        event.setCreatedAt(LocalDateTime.now());

        // When
        String toString = event.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id='event123'"));
        assertTrue(toString.contains("bizId=123"));
        assertTrue(toString.contains("eventType='ENQUIRY_CREATED'"));
        assertTrue(toString.contains("entityType='ENQUIRY'"));
        assertTrue(toString.contains("entityId=456"));
        assertTrue(toString.contains("title='New enquiry'"));
    }

    @Test
    void shouldInitializeEmptyMetadataWhenNull() {
        // Given
        ActivityEvent event = new ActivityEvent();

        // When
        event.setMetadata(null);

        // Then
        assertNotNull(event.getMetadata());
        assertTrue(event.getMetadata().isEmpty());
    }

    @Test
    void shouldHandleMetadataOperationsWhenMetadataIsNull() {
        // Given
        ActivityEvent event = new ActivityEvent();
        event.setMetadata(null);

        // When
        Object value = event.getMetadata("nonExistent");

        // Then
        assertNull(value);
    }
}