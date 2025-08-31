package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class DailyJobStatsDto {
    
    @NotNull
    private LocalDate date;
    
    @NotNull
    private Integer totalAppointments;
    
    @NotNull
    private Integer totalWalkIns;
    
    @NotNull
    private Integer totalBills;
    
    @NotNull
    private BigDecimal totalRevenue;
    
    private List<ServiceBreakdownDto> serviceBreakdown;
    
    // Constructors
    public DailyJobStatsDto() {}
    
    public DailyJobStatsDto(LocalDate date, Integer totalAppointments, Integer totalWalkIns, 
                           Integer totalBills, BigDecimal totalRevenue) {
        this.date = date;
        this.totalAppointments = totalAppointments;
        this.totalWalkIns = totalWalkIns;
        this.totalBills = totalBills;
        this.totalRevenue = totalRevenue;
    }
    
    // Getters and Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public Integer getTotalAppointments() { return totalAppointments; }
    public void setTotalAppointments(Integer totalAppointments) { this.totalAppointments = totalAppointments; }
    
    public Integer getTotalWalkIns() { return totalWalkIns; }
    public void setTotalWalkIns(Integer totalWalkIns) { this.totalWalkIns = totalWalkIns; }
    
    public Integer getTotalBills() { return totalBills; }
    public void setTotalBills(Integer totalBills) { this.totalBills = totalBills; }
    
    public BigDecimal getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    
    public List<ServiceBreakdownDto> getServiceBreakdown() { return serviceBreakdown; }
    public void setServiceBreakdown(List<ServiceBreakdownDto> serviceBreakdown) { this.serviceBreakdown = serviceBreakdown; }
}