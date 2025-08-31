package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class ServiceBreakdownDto {
    
    @NotNull
    private String serviceName;
    
    @NotNull
    private Integer appointmentCount;
    
    @NotNull
    private Integer walkInCount;
    
    @NotNull
    private Integer billCount;
    
    @NotNull
    private BigDecimal revenue;
    
    // Constructors
    public ServiceBreakdownDto() {}
    
    public ServiceBreakdownDto(String serviceName, Integer appointmentCount, Integer walkInCount, 
                              Integer billCount, BigDecimal revenue) {
        this.serviceName = serviceName;
        this.appointmentCount = appointmentCount;
        this.walkInCount = walkInCount;
        this.billCount = billCount;
        this.revenue = revenue;
    }
    
    // Getters and Setters
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public Integer getAppointmentCount() { return appointmentCount; }
    public void setAppointmentCount(Integer appointmentCount) { this.appointmentCount = appointmentCount; }
    
    public Integer getWalkInCount() { return walkInCount; }
    public void setWalkInCount(Integer walkInCount) { this.walkInCount = walkInCount; }
    
    public Integer getBillCount() { return billCount; }
    public void setBillCount(Integer billCount) { this.billCount = billCount; }
    
    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
}