package com.qtick.mis.entity;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Bill entity.
 */
class BillTest {

    @Test
    void shouldCreateBillWithRequiredFields() {
        // Given
        Long bizId = 123L;
        Client client = new Client(bizId, "John Doe", "9876543210", "john@example.com");
        String billNumber = "BILL001";
        LocalDate billDate = LocalDate.now();
        BigDecimal grossAmount = BigDecimal.valueOf(1000);
        BigDecimal netAmount = BigDecimal.valueOf(1000);

        // When
        Bill bill = new Bill(bizId, client, billNumber, billDate, grossAmount, netAmount);

        // Then
        assertEquals(bizId, bill.getBizId());
        assertEquals(client, bill.getClient());
        assertEquals(billNumber, bill.getBillNumber());
        assertEquals(billDate, bill.getBillDate());
        assertEquals(grossAmount, bill.getGrossAmount());
        assertEquals(netAmount, bill.getNetAmount());
        assertEquals(netAmount, bill.getBalanceAmount());
        assertEquals(BigDecimal.ZERO, bill.getPaidAmount());
        assertEquals(BillStatus.PENDING, bill.getStatus());
        assertTrue(bill.getItems().isEmpty());
        assertTrue(bill.getPayments().isEmpty());
    }

    @Test
    void shouldAddItemToBill() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        BillItem item = new BillItem(bill, 101L, "Haircut", 1, BigDecimal.valueOf(500));

        // When
        bill.addItem(item);

        // Then
        assertEquals(1, bill.getItems().size());
        assertEquals(bill, item.getBill());
        assertTrue(bill.getItems().contains(item));
    }

    @Test
    void shouldAddPaymentToBill() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        BillPayment payment = new BillPayment(bill, BigDecimal.valueOf(500), 
                                            PaymentMode.CASH, LocalDateTime.now());

        // When
        bill.addPayment(payment);

        // Then
        assertEquals(1, bill.getPayments().size());
        assertEquals(bill, payment.getBill());
        assertTrue(bill.getPayments().contains(payment));
        assertEquals(BigDecimal.valueOf(500), bill.getPaidAmount());
        assertEquals(BigDecimal.valueOf(500), bill.getBalanceAmount());
        assertEquals(BillStatus.PARTIAL, bill.getStatus());
    }

    @Test
    void shouldMarkBillAsPaidWhenFullyPaid() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        BillPayment payment = new BillPayment(bill, BigDecimal.valueOf(1000), 
                                            PaymentMode.CASH, LocalDateTime.now());

        // When
        bill.addPayment(payment);

        // Then
        assertEquals(BigDecimal.valueOf(1000), bill.getPaidAmount());
        assertEquals(BigDecimal.ZERO, bill.getBalanceAmount());
        assertEquals(BillStatus.PAID, bill.getStatus());
    }

    @Test
    void shouldHandleMultiplePayments() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        
        BillPayment payment1 = new BillPayment(bill, BigDecimal.valueOf(300), 
                                             PaymentMode.CASH, LocalDateTime.now());
        BillPayment payment2 = new BillPayment(bill, BigDecimal.valueOf(700), 
                                             PaymentMode.CARD, LocalDateTime.now());

        // When
        bill.addPayment(payment1);
        bill.addPayment(payment2);

        // Then
        assertEquals(2, bill.getPayments().size());
        assertEquals(BigDecimal.valueOf(1000), bill.getPaidAmount());
        assertEquals(BigDecimal.ZERO, bill.getBalanceAmount());
        assertEquals(BillStatus.PAID, bill.getStatus());
    }

    @Test
    void shouldRecalculateAmountsFromItems() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.ZERO, BigDecimal.ZERO);
        
        BillItem item1 = new BillItem(bill, 101L, "Haircut", 1, BigDecimal.valueOf(500));
        BillItem item2 = new BillItem(bill, 102L, "Shampoo", 2, BigDecimal.valueOf(250));
        
        bill.addItem(item1);
        bill.addItem(item2);
        bill.setDiscountAmount(BigDecimal.valueOf(100));
        bill.setTaxAmount(BigDecimal.valueOf(90));

        // When
        bill.recalculateAmounts();

        // Then
        assertEquals(BigDecimal.valueOf(1000), bill.getGrossAmount()); // 500 + (2 * 250)
        assertEquals(BigDecimal.valueOf(990), bill.getNetAmount()); // 1000 - 100 + 90
        assertEquals(BigDecimal.valueOf(990), bill.getBalanceAmount());
    }

    @Test
    void shouldSetAllBillFields() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill();

        // When
        bill.setBizId(123L);
        bill.setBranchId(456L);
        bill.setClient(client);
        bill.setBillNumber("BILL002");
        bill.setBillDate(LocalDate.now());
        bill.setGrossAmount(BigDecimal.valueOf(1500));
        bill.setDiscountAmount(BigDecimal.valueOf(150));
        bill.setTaxAmount(BigDecimal.valueOf(135));
        bill.setNetAmount(BigDecimal.valueOf(1485));
        bill.setPaidAmount(BigDecimal.valueOf(500));
        bill.setBalanceAmount(BigDecimal.valueOf(985));
        bill.setStatus(BillStatus.PARTIAL);
        bill.setPaymentMode(PaymentMode.CARD);
        bill.setPaymentReference("TXN123456");
        bill.setPaymentDate(LocalDateTime.now());
        bill.setNotes("Partial payment received");
        bill.setStaffId(789L);
        bill.setStaffName("Staff Member");

        // Then
        assertEquals(123L, bill.getBizId());
        assertEquals(456L, bill.getBranchId());
        assertEquals(client, bill.getClient());
        assertEquals("BILL002", bill.getBillNumber());
        assertEquals(BigDecimal.valueOf(1500), bill.getGrossAmount());
        assertEquals(BigDecimal.valueOf(150), bill.getDiscountAmount());
        assertEquals(BigDecimal.valueOf(135), bill.getTaxAmount());
        assertEquals(BigDecimal.valueOf(1485), bill.getNetAmount());
        assertEquals(BigDecimal.valueOf(500), bill.getPaidAmount());
        assertEquals(BigDecimal.valueOf(985), bill.getBalanceAmount());
        assertEquals(BillStatus.PARTIAL, bill.getStatus());
        assertEquals(PaymentMode.CARD, bill.getPaymentMode());
        assertEquals("TXN123456", bill.getPaymentReference());
        assertEquals("Partial payment received", bill.getNotes());
        assertEquals(789L, bill.getStaffId());
        assertEquals("Staff Member", bill.getStaffName());
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        Client client = new Client(123L, "John Doe", "9876543210", "john@example.com");
        Bill bill = new Bill(123L, client, "BILL001", LocalDate.now(), 
                           BigDecimal.valueOf(1000), BigDecimal.valueOf(1000));
        bill.setId(1L);

        // When
        String toString = bill.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id=1"));
        assertTrue(toString.contains("billNumber='BILL001'"));
        assertTrue(toString.contains("netAmount=1000"));
        assertTrue(toString.contains("status=PENDING"));
    }
}