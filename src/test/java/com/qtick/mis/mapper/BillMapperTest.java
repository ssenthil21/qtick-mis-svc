package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.BillDto;
import com.qtick.mis.dto.client.BillItemDto;
import com.qtick.mis.dto.client.BillPaymentDto;
import com.qtick.mis.entity.Bill;
import com.qtick.mis.entity.BillItem;
import com.qtick.mis.entity.BillPayment;
import com.qtick.mis.entity.Client;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BillMapperTest {
    
    private final BillMapper mapper = Mappers.getMapper(BillMapper.class);
    
    @Test
    void shouldMapBillToDto() {
        // Given
        Bill bill = createTestBill();
        BillItem item = createTestBillItem(bill);
        BillPayment payment = createTestBillPayment(bill);
        bill.setItems(Arrays.asList(item));
        bill.setPayments(Arrays.asList(payment));
        
        // When
        BillDto dto = mapper.toDto(bill);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getBillId()).isEqualTo(bill.getBillId());
        assertThat(dto.getCustId()).isEqualTo(bill.getClient().getCustId());
        assertThat(dto.getTotalAmount()).isEqualTo(bill.getTotalAmount());
        assertThat(dto.getPaidAmount()).isEqualTo(bill.getPaidAmount());
        assertThat(dto.getBalanceAmount()).isEqualTo(bill.getBalanceAmount());
        assertThat(dto.getStatus()).isEqualTo(bill.getStatus());
        assertThat(dto.getBillDate()).isEqualTo(bill.getBillDate());
        assertThat(dto.getItems()).hasSize(1);
        assertThat(dto.getPayments()).hasSize(1);
    }
    
    @Test
    void shouldMapBillToSummaryDto() {
        // Given
        Bill bill = createTestBill();
        
        // When
        BillDto dto = mapper.toSummaryDto(bill);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getBillId()).isEqualTo(bill.getBillId());
        assertThat(dto.getCustId()).isEqualTo(bill.getClient().getCustId());
        assertThat(dto.getTotalAmount()).isEqualTo(bill.getTotalAmount());
        assertThat(dto.getItems()).isNull();
        assertThat(dto.getPayments()).isNull();
    }
    
    @Test
    void shouldMapBillItemToDto() {
        // Given
        BillItem item = createTestBillItem(createTestBill());
        
        // When
        BillItemDto dto = mapper.toItemDto(item);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getItemId()).isEqualTo(item.getItemId());
        assertThat(dto.getBillId()).isEqualTo(item.getBill().getBillId());
        assertThat(dto.getQuantity()).isEqualTo(item.getQuantity());
        assertThat(dto.getUnitPrice()).isEqualTo(item.getUnitPrice());
        assertThat(dto.getTotalPrice()).isEqualTo(item.getTotalPrice());
    }
    
    @Test
    void shouldMapBillPaymentToDto() {
        // Given
        BillPayment payment = createTestBillPayment(createTestBill());
        
        // When
        BillPaymentDto dto = mapper.toPaymentDto(payment);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getPaymentId()).isEqualTo(payment.getPaymentId());
        assertThat(dto.getBillId()).isEqualTo(payment.getBill().getBillId());
        assertThat(dto.getAmount()).isEqualTo(payment.getAmount());
        assertThat(dto.getPaymentMethod()).isEqualTo(payment.getPaymentMethod());
        assertThat(dto.getPaymentDate()).isEqualTo(payment.getPaymentDate());
    }
    
    @Test
    void shouldMapBillList() {
        // Given
        List<Bill> bills = Arrays.asList(createTestBill(), createTestBill());
        
        // When
        List<BillDto> dtos = mapper.toDtos(bills);
        
        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getTotalAmount()).isEqualTo(new BigDecimal("100.00"));
    }
    
    private Bill createTestBill() {
        Client client = new Client();
        client.setCustId(1L);
        client.setName("John Doe");
        
        Bill bill = new Bill();
        bill.setBillId(1L);
        bill.setClient(client);
        bill.setTotalAmount(new BigDecimal("100.00"));
        bill.setPaidAmount(new BigDecimal("50.00"));
        bill.setBalanceAmount(new BigDecimal("50.00"));
        bill.setStatus("PARTIAL");
        bill.setBillDate(LocalDateTime.now());
        bill.setPaymentMethod("CASH");
        return bill;
    }
    
    private BillItem createTestBillItem(Bill bill) {
        BillItem item = new BillItem();
        item.setItemId(1L);
        item.setBill(bill);
        item.setQuantity(2);
        item.setUnitPrice(new BigDecimal("25.00"));
        item.setTotalPrice(new BigDecimal("50.00"));
        return item;
    }
    
    private BillPayment createTestBillPayment(Bill bill) {
        BillPayment payment = new BillPayment();
        payment.setPaymentId(1L);
        payment.setBill(bill);
        payment.setAmount(new BigDecimal("50.00"));
        payment.setPaymentMethod("CASH");
        payment.setPaymentDate(LocalDateTime.now());
        return payment;
    }
}