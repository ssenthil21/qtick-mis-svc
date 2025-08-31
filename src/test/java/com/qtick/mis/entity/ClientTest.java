package com.qtick.mis.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Client entity.
 */
class ClientTest {

    @Test
    void shouldCreateClientWithRequiredFields() {
        // Given
        Long bizId = 123L;
        String name = "John Doe";
        String phone = "9876543210";
        String email = "john@example.com";

        // When
        Client client = new Client(bizId, name, phone, email);

        // Then
        assertEquals(bizId, client.getBizId());
        assertEquals(name, client.getName());
        assertEquals(phone, client.getPhone());
        assertEquals(email, client.getEmail());
        assertEquals(ClientStatus.ACTIVE, client.getStatus());
        assertEquals(0, client.getLoyaltyPoints());
        assertTrue(client.getBills().isEmpty());
        assertTrue(client.getAppointments().isEmpty());
        assertTrue(client.getPointsTransactions().isEmpty());
    }

    @Test
    void shouldAddBillToClient() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));

        // When
        client.addBill(bill);

        // Then
        assertEquals(1, client.getBills().size());
        assertEquals(client, bill.getClient());
        assertTrue(client.getBills().contains(bill));
        assertNotNull(client.getLastVisitDate());
    }

    @Test
    void shouldAddAppointmentToClient() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Appointment appointment = new Appointment(123L, client, LocalDateTime.now(), 
                                                101L, "Haircut");

        // When
        client.addAppointment(appointment);

        // Then
        assertEquals(1, client.getAppointments().size());
        assertEquals(client, appointment.getClient());
        assertTrue(client.getAppointments().contains(appointment));
    }

    @Test
    void shouldAddPointsTransactionToClient() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        PointsTransaction transaction = new PointsTransaction(client, 100, 
                                                            PointsTransactionType.EARNED, 
                                                            "Purchase reward");

        // When
        client.addPointsTransaction(transaction);

        // Then
        assertEquals(1, client.getPointsTransactions().size());
        assertEquals(client, transaction.getClient());
        assertTrue(client.getPointsTransactions().contains(transaction));
        assertEquals(100, client.getLoyaltyPoints());
    }

    @Test
    void shouldAccumulateLoyaltyPoints() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        
        PointsTransaction earnedPoints = new PointsTransaction(client, 100, 
                                                             PointsTransactionType.EARNED, 
                                                             "Purchase reward");
        PointsTransaction bonusPoints = new PointsTransaction(client, 50, 
                                                            PointsTransactionType.BONUS, 
                                                            "Birthday bonus");
        PointsTransaction redeemedPoints = new PointsTransaction(client, -25, 
                                                               PointsTransactionType.REDEEMED, 
                                                               "Discount applied");

        // When
        client.addPointsTransaction(earnedPoints);
        client.addPointsTransaction(bonusPoints);
        client.addPointsTransaction(redeemedPoints);

        // Then
        assertEquals(125, client.getLoyaltyPoints()); // 100 + 50 - 25
        assertEquals(3, client.getPointsTransactions().size());
    }

    @Test
    void shouldSetAllClientFields() {
        // Given
        Client client = new Client();

        // When
        client.setBizId(123L);
        client.setName("Jane Doe");
        client.setEmail("jane@example.com");
        client.setPhone("9876543210");
        client.setDob(LocalDate.of(1990, 5, 15));
        client.setGender(Gender.FEMALE);
        client.setAddress("123 Main St");
        client.setCity("Mumbai");
        client.setState("Maharashtra");
        client.setPincode("400001");
        client.setCountry("India");
        client.setBusinessName("Jane's Boutique");
        client.setBusinessType("Retail");
        client.setContactPerson("Jane Doe");
        client.setLoyaltyPoints(250);
        client.setPreferredChannel("WhatsApp");
        client.setTags("VIP,Regular");
        client.setStatus(ClientStatus.ACTIVE);

        LocalDateTime lastVisit = LocalDateTime.now();
        client.setLastVisitDate(lastVisit);

        // Then
        assertEquals(123L, client.getBizId());
        assertEquals("Jane Doe", client.getName());
        assertEquals("jane@example.com", client.getEmail());
        assertEquals("9876543210", client.getPhone());
        assertEquals(LocalDate.of(1990, 5, 15), client.getDob());
        assertEquals(Gender.FEMALE, client.getGender());
        assertEquals("123 Main St", client.getAddress());
        assertEquals("Mumbai", client.getCity());
        assertEquals("Maharashtra", client.setState());
        assertEquals("400001", client.getPincode());
        assertEquals("India", client.getCountry());
        assertEquals("Jane's Boutique", client.getBusinessName());
        assertEquals("Retail", client.getBusinessType());
        assertEquals("Jane Doe", client.getContactPerson());
        assertEquals(250, client.getLoyaltyPoints());
        assertEquals("WhatsApp", client.getPreferredChannel());
        assertEquals("VIP,Regular", client.getTags());
        assertEquals(ClientStatus.ACTIVE, client.getStatus());
        assertEquals(lastVisit, client.getLastVisitDate());
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        client.setCustId(1L);

        // When
        String toString = client.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("custId=1"));
        assertTrue(toString.contains("bizId=123"));
        assertTrue(toString.contains("name='John Doe'"));
        assertTrue(toString.contains("phone='9876543210'"));
        assertTrue(toString.contains("email='john@example.com'"));
    }
}