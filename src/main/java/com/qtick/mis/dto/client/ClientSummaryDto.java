package com.qtick.mis.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class ClientSummaryDto {
    
    @NotNull
    private Long custId;
    
    @NotNull
    private String name;
    
    @Email(message = "Invalid email format")
    private String email;
    
    @Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Invalid phone number format")
    private String phone;
    
    private LocalDate lastVisit;
    private BigDecimal totalSpending;
    private Integer loyaltyPoints;
    private String loyaltyStatus;
    
    private List<String> tags;
    
    @NotNull
    private LocalDateTime createdOn;
    
    // Constructors
    public ClientSummaryDto() {}
    
    public ClientSummaryDto(Long custId, String name, String email, String phone) {
        this.custId = custId;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    
    // Getters and Setters
    public Long getCustId() { return custId; }
    public void setCustId(Long custId) { this.custId = custId; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public LocalDate getLastVisit() { return lastVisit; }
    public void setLastVisit(LocalDate lastVisit) { this.lastVisit = lastVisit; }
    
    public BigDecimal getTotalSpending() { return totalSpending; }
    public void setTotalSpending(BigDecimal totalSpending) { this.totalSpending = totalSpending; }
    
    public Integer getLoyaltyPoints() { return loyaltyPoints; }
    public void setLoyaltyPoints(Integer loyaltyPoints) { this.loyaltyPoints = loyaltyPoints; }
    
    public String getLoyaltyStatus() { return loyaltyStatus; }
    public void setLoyaltyStatus(String loyaltyStatus) { this.loyaltyStatus = loyaltyStatus; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    public LocalDateTime getCreatedOn() { return createdOn; }
    public void setCreatedOn(LocalDateTime createdOn) { this.createdOn = createdOn; }
}