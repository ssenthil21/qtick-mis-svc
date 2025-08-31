package com.qtick.mis.dto.client;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class BillDto {
    
    @NotNull
    private Long billId;
    
    @NotNull
    private Long custId;
    
    @NotNull
    private BigDecimal totalAmount;
    
    @NotNull
    private BigDecimal paidAmount;
    
    @NotNull
    private BigDecimal balanceAmount;
    
    @NotNull
    private String status;
    
    @NotNull
    private LocalDateTime billDate;
    
    private LocalDateTime dueDate;
    private String paymentMethod;
    private String notes;
    
    private List<BillItemDto> items;
    private List<BillPaymentDto> payments;
    
    // Constructors
    public BillDto() {}
    
    public BillDto(Long billId, Long custId, BigDecimal totalAmount, BigDecimal paidAmount, 
                   BigDecimal balanceAmount, String status, LocalDateTime billDate) {
        this.billId = billId;
        this.custId = custId;
        this.totalAmount = totalAmount;
        this.paidAmount = paidAmount;
        this.balanceAmount = balanceAmount;
        this.status = status;
        this.billDate = billDate;
    }
    
    // Getters and Setters
    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }
    
    public Long getCustId() { return custId; }
    public void setCustId(Long custId) { this.custId = custId; }
    
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    
    public BigDecimal getPaidAmount() { return paidAmount; }
    public void setPaidAmount(BigDecimal paidAmount) { this.paidAmount = paidAmount; }
    
    public BigDecimal getBalanceAmount() { return balanceAmount; }
    public void setBalanceAmount(BigDecimal balanceAmount) { this.balanceAmount = balanceAmount; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public LocalDateTime getBillDate() { return billDate; }
    public void setBillDate(LocalDateTime billDate) { this.billDate = billDate; }
    
    public LocalDateTime getDueDate() { return dueDate; }
    public void setDueDate(LocalDateTime dueDate) { this.dueDate = dueDate; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
    
    public List<BillItemDto> getItems() { return items; }
    public void setItems(List<BillItemDto> items) { this.items = items; }
    
    public List<BillPaymentDto> getPayments() { return payments; }
    public void setPayments(List<BillPaymentDto> payments) { this.payments = payments; }
}