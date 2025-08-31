package com.qtick.mis.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Enquiry entity.
 */
class EnquiryTest {

    @Test
    void shouldCreateEnquiryWithRequiredFields() {
        // Given
        Long bizId = 123L;
        String name = "John Doe";
        String phone = "9876543210";
        String email = "john@example.com";
        EnquiryStage stage = EnquiryStage.LEAD;
        EnquiryStatus status = EnquiryStatus.ACTIVE;

        // When
        Enquiry enquiry = new Enquiry(bizId, name, phone, email, stage, status);

        // Then
        assertEquals(bizId, enquiry.getBizId());
        assertEquals(name, enquiry.getName());
        assertEquals(phone, enquiry.getPhone());
        assertEquals(email, enquiry.getEmail());
        assertEquals(stage, enquiry.getStage());
        assertEquals(status, enquiry.getStatus());
        assertNotNull(enquiry.getLastTouchDate());
        assertTrue(enquiry.getThreads().isEmpty());
    }

    @Test
    void shouldAddThreadToEnquiry() {
        // Given
        Enquiry enquiry = new Enquiry(123L, "John Doe", "9876543210", "john@example.com", 
                                     EnquiryStage.LEAD, EnquiryStatus.ACTIVE);
        EnquiryThread thread = new EnquiryThread(enquiry, ThreadType.NOTE, "Initial contact", 
                                               ThreadDirection.OUTBOUND, 1L, "Agent Smith");

        // When
        enquiry.addThread(thread);

        // Then
        assertEquals(1, enquiry.getThreads().size());
        assertEquals(enquiry, thread.getEnquiry());
        assertTrue(enquiry.getThreads().contains(thread));
    }

    @Test
    void shouldRemoveThreadFromEnquiry() {
        // Given
        Enquiry enquiry = new Enquiry(123L, "John Doe", "9876543210", "john@example.com", 
                                     EnquiryStage.LEAD, EnquiryStatus.ACTIVE);
        EnquiryThread thread = new EnquiryThread(enquiry, ThreadType.NOTE, "Initial contact", 
                                               ThreadDirection.OUTBOUND, 1L, "Agent Smith");
        enquiry.addThread(thread);

        // When
        enquiry.removeThread(thread);

        // Then
        assertTrue(enquiry.getThreads().isEmpty());
        assertNull(thread.getEnquiry());
    }

    @Test
    void shouldUpdateLastTouchDateWhenAddingThread() {
        // Given
        Enquiry enquiry = new Enquiry(123L, "John Doe", "9876543210", "john@example.com", 
                                     EnquiryStage.LEAD, EnquiryStatus.ACTIVE);
        LocalDateTime originalTouchDate = enquiry.getLastTouchDate();
        
        // Wait a bit to ensure time difference
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        EnquiryThread thread = new EnquiryThread(enquiry, ThreadType.NOTE, "Follow up", 
                                               ThreadDirection.OUTBOUND, 1L, "Agent Smith");

        // When
        enquiry.addThread(thread);

        // Then
        assertTrue(enquiry.getLastTouchDate().isAfter(originalTouchDate));
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        Enquiry enquiry = new Enquiry(123L, "John Doe", "9876543210", "john@example.com", 
                                     EnquiryStage.LEAD, EnquiryStatus.ACTIVE);
        enquiry.setId(1L);

        // When
        String toString = enquiry.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("bizId=123"));
        assertTrue(toString.contains("name='John Doe'"));
        assertTrue(toString.contains("phone='9876543210'"));
        assertTrue(toString.contains("stage=LEAD"));
        assertTrue(toString.contains("status=ACTIVE"));
    }

    @Test
    void shouldSetAllFields() {
        // Given
        Enquiry enquiry = new Enquiry();

        // When
        enquiry.setBizId(123L);
        enquiry.setBranchId(456L);
        enquiry.setCustId(789L);
        enquiry.setName("Jane Doe");
        enquiry.setPhone("9876543210");
        enquiry.setEmail("jane@example.com");
        enquiry.setEnqType(EnquiryType.NEW_CUSTOMER);
        enquiry.setSrvcEnq(101L);
        enquiry.setStage(EnquiryStage.QUALIFIED);
        enquiry.setStatus(EnquiryStatus.ACTIVE);
        enquiry.setSource("Website");
        enquiry.setChannel("Online");
        enquiry.setAssigneeId(202L);
        enquiry.setNextAction("Follow up call");

        LocalDateTime now = LocalDateTime.now();
        enquiry.setReContactOn(now);
        enquiry.setClosureDate(now.plusDays(7));

        // Then
        assertEquals(123L, enquiry.getBizId());
        assertEquals(456L, enquiry.getBranchId());
        assertEquals(789L, enquiry.getCustId());
        assertEquals("Jane Doe", enquiry.getName());
        assertEquals("9876543210", enquiry.getPhone());
        assertEquals("jane@example.com", enquiry.getEmail());
        assertEquals(EnquiryType.NEW_CUSTOMER, enquiry.getEnqType());
        assertEquals(101L, enquiry.getSrvcEnq());
        assertEquals(EnquiryStage.QUALIFIED, enquiry.getStage());
        assertEquals(EnquiryStatus.ACTIVE, enquiry.getStatus());
        assertEquals("Website", enquiry.getSource());
        assertEquals("Online", enquiry.getChannel());
        assertEquals(202L, enquiry.getAssigneeId());
        assertEquals("Follow up call", enquiry.getNextAction());
        assertEquals(now, enquiry.getReContactOn());
        assertEquals(now.plusDays(7), enquiry.getClosureDate());
    }
}