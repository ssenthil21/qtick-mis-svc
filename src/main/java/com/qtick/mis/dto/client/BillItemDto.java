package com.qtick.mis.dto.client;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class BillItemDto {
    
    @NotNull
    private Long itemId;
    
    @NotNull
    private Long billId;
    
    @NotNull
    private String serviceName;
    
    @NotNull
    private Integer quantity;
    
    @NotNull
    private BigDecimal unitPrice;
    
    @NotNull
    private BigDecimal totalPrice;
    
    private BigDecimal discountAmount;
    private String notes;
    
    // Constructors
    public BillItemDto() {}
    
    public BillItemDto(Long itemId, Long billId, String serviceName, Integer quantity, 
                       BigDecimal unitPrice, BigDecimal totalPrice) {
        this.itemId = itemId;
        this.billId = billId;
        this.serviceName = serviceName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
    }
    
    // Getters and Setters
    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }
    
    public Long getBillId() { return billId; }
    public void setBillId(Long billId) { this.billId = billId; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
    
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    
    public BigDecimal getTotalPrice() { return totalPrice; }
    public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
    
    public BigDecimal getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
    
    public String getNotes() { return notes; }
    public void setNotes(String notes) { this.notes = notes; }
}