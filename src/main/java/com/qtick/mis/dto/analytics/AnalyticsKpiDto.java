package com.qtick.mis.dto.analytics;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class AnalyticsKpiDto {
    
    @NotNull
    private LocalDate periodStart;
    
    @NotNull
    private LocalDate periodEnd;
    
    // Revenue KPIs
    @NotNull
    private BigDecimal totalRevenue;
    
    @NotNull
    private BigDecimal averageOrderValue;
    
    @NotNull
    private BigDecimal revenueGrowth;
    
    // Customer KPIs
    @NotNull
    private Integer totalCustomers;
    
    @NotNull
    private Integer newCustomers;
    
    @NotNull
    private Integer returningCustomers;
    
    @NotNull
    private BigDecimal customerRetentionRate;
    
    // Service KPIs
    @NotNull
    private Integer totalServices;
    
    @NotNull
    private Integer totalAppointments;
    
    @NotNull
    private BigDecimal serviceUtilization;
    
    // Comparison deltas
    private BigDecimal totalRevenueDelta;
    private BigDecimal averageOrderValueDelta;
    private BigDecimal totalCustomersDelta;
    private BigDecimal newCustomersDelta;
    private BigDecimal returningCustomersDelta;
    private BigDecimal customerRetentionRateDelta;
    
    // Constructors
    public AnalyticsKpiDto() {}
    
    // Getters and Setters
    public LocalDate getPeriodStart() { return periodStart; }
    public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }
    
    public LocalDate getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }
    
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public BigDecimal getAverageOrderValue() { return averageOrderValue; }
    public void setAverageOrderValue(BigDecimal averageOrderValue) { this.averageOrderValue = averageOrderValue; }
    
    public BigDecimal getRevenueGrowth() { return revenueGrowth; }
    public void setRevenueGrowth(BigDecimal revenueGrowth) { this.revenueGrowth = revenueGrowth; }
    
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    
    public Integer getNewCustomers() { return newCustomers; }
    public void setNewCustomers(Integer newCustomers) { this.newCustomers = newCustomers; }
    
    public Integer getReturningCustomers() { return returningCustomers; }
    public void setReturningCustomers(Integer returningCustomers) { this.returningCustomers = returningCustomers; }
    
    public BigDecimal getCustomerRetentionRate() { return customerRetentionRate; }
    public void setCustomerRetentionRate(BigDecimal customerRetentionRate) { this.customerRetentionRate = customerRetentionRate; }
    
    public Integer getTotalServices() { return totalServices; }
    public void setTotalServices(Integer totalServices) { this.totalServices = totalServices; }
    
    public Integer getTotalAppointments() { return totalAppointments; }
    public void setTotalAppointments(Integer totalAppointments) { this.totalAppointments = totalAppointments; }
    
    public BigDecimal getServiceUtilization() { return serviceUtilization; }
    public void setServiceUtilization(BigDecimal serviceUtilization) { this.serviceUtilization = serviceUtilization; }
    
    public BigDecimal getTotalRevenueDelta() { return totalRevenueDelta; }
    public void setTotalRevenueDelta(BigDecimal totalRevenueDelta) { this.totalRevenueDelta = totalRevenueDelta; }
    
    public BigDecimal getAverageOrderValueDelta() { return averageOrderValueDelta; }
    public void setAverageOrderValueDelta(BigDecimal averageOrderValueDelta) { this.averageOrderValueDelta = averageOrderValueDelta; }
    
    public BigDecimal getTotalCustomersDelta() { return totalCustomersDelta; }
    public void setTotalCustomersDelta(BigDecimal totalCustomersDelta) { this.totalCustomersDelta = totalCustomersDelta; }
    
    public BigDecimal getNewCustomersDelta() { return newCustomersDelta; }
    public void setNewCustomersDelta(BigDecimal newCustomersDelta) { this.newCustomersDelta = newCustomersDelta; }
    
    public BigDecimal getReturningCustomersDelta() { return returningCustomersDelta; }
    public void setReturningCustomersDelta(BigDecimal returningCustomersDelta) { this.returningCustomersDelta = returningCustomersDelta; }
    
    public BigDecimal getCustomerRetentionRateDelta() { return customerRetentionRateDelta; }
    public void setCustomerRetentionRateDelta(BigDecimal customerRetentionRateDelta) { this.customerRetentionRateDelta = customerRetentionRateDelta; }
}