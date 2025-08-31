package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class DashboardSummaryDto {
    
    @NotNull
    private BigDecimal grossSales;
    
    @NotNull
    private BigDecimal netSales;
    
    @NotNull
    private Integer bills;
    
    @NotNull
    private BigDecimal avgBill;
    
    @NotNull
    private Integer newLeads;
    
    @NotNull
    private Integer totalLeads;
    
    @NotNull
    private Integer missedLeads;
    
    @NotNull
    private Integer appointments;
    
    @NotNull
    private Integer returningCustomers;
    
    private LocalDate periodStart;
    private LocalDate periodEnd;
    
    // Comparison deltas (optional)
    private BigDecimal grossSalesDelta;
    private BigDecimal netSalesDelta;
    private BigDecimal billsDelta;
    private BigDecimal avgBillDelta;
    private BigDecimal newLeadsDelta;
    private BigDecimal totalLeadsDelta;
    private BigDecimal missedLeadsDelta;
    private BigDecimal appointmentsDelta;
    private BigDecimal returningCustomersDelta;
    
    // Constructors
    public DashboardSummaryDto() {}
    
    public DashboardSummaryDto(BigDecimal grossSales, BigDecimal netSales, Integer bills, 
                              BigDecimal avgBill, Integer newLeads, Integer totalLeads, 
                              Integer missedLeads, Integer appointments, Integer returningCustomers) {
        this.grossSales = grossSales;
        this.netSales = netSales;
        this.bills = bills;
        this.avgBill = avgBill;
        this.newLeads = newLeads;
        this.totalLeads = totalLeads;
        this.missedLeads = missedLeads;
        this.appointments = appointments;
        this.returningCustomers = returningCustomers;
    }
    
    // Getters and Setters
    public BigDecimal getGrossSales() { return grossSales; }
    public void setGrossSales(BigDecimal grossSales) { this.grossSales = grossSales; }
    
    public BigDecimal getNetSales() { return netSales; }
    public void setNetSales(BigDecimal netSales) { this.netSales = netSales; }
    
    public Integer getBills() { return bills; }
    public void setBills(Integer bills) { this.bills = bills; }
    
    public BigDecimal getAvgBill() { return avgBill; }
    public void setAvgBill(BigDecimal avgBill) { this.avgBill = avgBill; }
    
    public Integer getNewLeads() { return newLeads; }
    public void setNewLeads(Integer newLeads) { this.newLeads = newLeads; }
    
    public Integer getTotalLeads() { return totalLeads; }
    public void setTotalLeads(Integer totalLeads) { this.totalLeads = totalLeads; }
    
    public Integer getMissedLeads() { return missedLeads; }
    public void setMissedLeads(Integer missedLeads) { this.missedLeads = missedLeads; }
    
    public Integer getAppointments() { return appointments; }
    public void setAppointments(Integer appointments) { this.appointments = appointments; }
    
    public Integer getReturningCustomers() { return returningCustomers; }
    public void setReturningCustomers(Integer returningCustomers) { this.returningCustomers = returningCustomers; }
    
    public LocalDate getPeriodStart() { return periodStart; }
    public void setPeriodStart(LocalDate periodStart) { this.periodStart = periodStart; }
    
    public LocalDate getPeriodEnd() { return periodEnd; }
    public void setPeriodEnd(LocalDate periodEnd) { this.periodEnd = periodEnd; }
    
    public BigDecimal getGrossSalesDelta() { return grossSalesDelta; }
    public void setGrossSalesDelta(BigDecimal grossSalesDelta) { this.grossSalesDelta = grossSalesDelta; }
    
    public BigDecimal getNetSalesDelta() { return netSalesDelta; }
    public void setNetSalesDelta(BigDecimal netSalesDelta) { this.netSalesDelta = netSalesDelta; }
    
    public BigDecimal getBillsDelta() { return billsDelta; }
    public void setBillsDelta(BigDecimal billsDelta) { this.billsDelta = billsDelta; }
    
    public BigDecimal getAvgBillDelta() { return avgBillDelta; }
    public void setAvgBillDelta(BigDecimal avgBillDelta) { this.avgBillDelta = avgBillDelta; }
    
    public BigDecimal getNewLeadsDelta() { return newLeadsDelta; }
    public void setNewLeadsDelta(BigDecimal newLeadsDelta) { this.newLeadsDelta = newLeadsDelta; }
    
    public BigDecimal getTotalLeadsDelta() { return totalLeadsDelta; }
    public void setTotalLeadsDelta(BigDecimal totalLeadsDelta) { this.totalLeadsDelta = totalLeadsDelta; }
    
    public BigDecimal getMissedLeadsDelta() { return missedLeadsDelta; }
    public void setMissedLeadsDelta(BigDecimal missedLeadsDelta) { this.missedLeadsDelta = missedLeadsDelta; }
    
    public BigDecimal getAppointmentsDelta() { return appointmentsDelta; }
    public void setAppointmentsDelta(BigDecimal appointmentsDelta) { this.appointmentsDelta = appointmentsDelta; }
    
    public BigDecimal getReturningCustomersDelta() { return returningCustomersDelta; }
    public void setReturningCustomersDelta(BigDecimal returningCustomersDelta) { this.returningCustomersDelta = returningCustomersDelta; }
}