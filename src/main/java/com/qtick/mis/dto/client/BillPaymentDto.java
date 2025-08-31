package com.qtick.mis.dto.client;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class BillPaymentDto {
    
    @NotNull
    private Long paymentId;
    
    @NotNull
    private Long billId;
    
    @NotNull
    private BigDecimal amount;
    
    @NotNull
    private String paymentMethod;
    
    @NotNull
    private LocalDateTime paymentDate;
    
    private String transactionId;
    private String notes;
    
    // Constructors
    public BillPaymentDto() {}
    
    public BillPaymentDto(Long paymentId, Long billId, BigDecimal amount, String paymentMethod, 
                         LocalDateTime paymentDate) {
        this.paymentId = paymentId;
        this.billId = billId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.paymentDate = paymentDate;
    }
    
    // Getters and Setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    
    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }
    
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDateTime paymentDate) { this.paymentDate = paymentDate; }
    
    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}