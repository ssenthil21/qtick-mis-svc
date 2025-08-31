package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class BusinessDetailsDto {
    
    @NotNull
    private Long custId;
    
    @NotNull
    private String businessName;
    
    @NotNull
    private String businessType;
    
    private String contactPerson;
    private String phone;
    private String email;
    private LocalDate lastVisit;
    
    private DailyJobStatsDto dailyStats;
    
    // Constructors
    public BusinessDetailsDto() {}
    
    public BusinessDetailsDto(Long custId, String businessName, String businessType) {
        this.custId = custId;
        this.businessName = businessName;
        this.businessType = businessType;
    }
    
    // Getters and Setters
    public Long getCustId() { return custId; }
    public void setCustId(Long custId) { this.custId = custId; }
    
    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }
    
    public String getBusinessType() { return businessType; }
    public void setBusinessType(String businessType) { this.businessType = businessType; }
    
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    
    public LocalDate getLastVisit() { return lastVisit; }
    public void setLastVisit(LocalDate lastVisit) { this.lastVisit = lastVisit; }
    
    public DailyJobStatsDto getDailyStats() { return dailyStats; }
    public void setDailyStats(DailyJobStatsDto dailyStats) { this.dailyStats = dailyStats; }
}