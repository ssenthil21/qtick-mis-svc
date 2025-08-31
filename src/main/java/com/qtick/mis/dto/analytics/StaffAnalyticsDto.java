package com.qtick.mis.dto.analytics;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class StaffAnalyticsDto {
    
    @NotNull
    private Long staffId;
    
    @NotNull
    private String staffName;
    
    @NotNull
    private BigDecimal revenue;
    
    @NotNull
    private Integer jobs;
    
    @NotNull
    private BigDecimal utilization; // percentage of working hours utilized
    
    @NotNull
    private BigDecimal averageRating;
    
    private BigDecimal averageJobValue;
    private Integer totalCustomers;
    private Integer repeatCustomers;
    private BigDecimal customerRetentionRate;
    
    // Performance metrics
    private List<MonthlyPerformance> monthlyPerformance;
    
    // Constructors
    public StaffAnalyticsDto() {}
    
    public StaffAnalyticsDto(Long staffId, String staffName, BigDecimal revenue, 
                            Integer jobs, BigDecimal utilization, BigDecimal averageRating) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.revenue = revenue;
        this.jobs = jobs;
        this.utilization = utilization;
        this.averageRating = averageRating;
    }
    
    // Getters and Setters
    public Long getStaffId() { return staffId; }
    public void setStaffId(Long staffId) { this.staffId = staffId; }
    
    public String getStaffName() { return staffName; }
    public void setStaffName(String staffName) { this.staffName = staffName; }
    
    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
    
    public Integer getJobs() { return jobs; }
    public void setJobs(Integer jobs) { this.jobs = jobs; }
    
    public BigDecimal getUtilization() { return utilization; }
    public void setUtilization(BigDecimal utilization) { this.utilization = utilization; }
    
    public BigDecimal getAverageRating() { return averageRating; }
    public void setAverageRating(BigDecimal averageRating) { this.averageRating = averageRating; }
    
    public BigDecimal getAverageJobValue() { return averageJobValue; }
    public void setAverageJobValue(BigDecimal averageJobValue) { this.averageJobValue = averageJobValue; }
    
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    
    public Integer getRepeatCustomers() { return repeatCustomers; }
    public void setRepeatCustomers(Integer repeatCustomers) { this.repeatCustomers = repeatCustomers; }
    
    public BigDecimal getCustomerRetentionRate() { return customerRetentionRate; }
    public void setCustomerRetentionRate(BigDecimal customerRetentionRate) { this.customerRetentionRate = customerRetentionRate; }
    
    public List<MonthlyPerformance> getMonthlyPerformance() { return monthlyPerformance; }
    public void setMonthlyPerformance(List<MonthlyPerformance> monthlyPerformance) { this.monthlyPerformance = monthlyPerformance; }
    
    // Inner class for monthly performance
    public static class MonthlyPerformance {
        private String month;
        private BigDecimal revenue;
        private Integer jobs;
        private BigDecimal utilization;
        private BigDecimal rating;
        
        public MonthlyPerformance() {}
        
        public MonthlyPerformance(String month, BigDecimal revenue, Integer jobs, 
                                 BigDecimal utilization, BigDecimal rating) {
            this.month = month;
            this.revenue = revenue;
            this.jobs = jobs;
            this.utilization = utilization;
            this.rating = rating;
        }
        
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }
        
        public BigDecimal getRevenue() { return revenue; }
        public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
        
        public Integer getJobs() { return jobs; }
        public void setJobs(Integer jobs) { this.jobs = jobs; }
        
        public BigDecimal getUtilization() { return utilization; }
        public void setUtilization(BigDecimal utilization) { this.utilization = utilization; }
        
        public BigDecimal getRating() { return rating; }
        public void setRating(BigDecimal rating) { this.rating = rating; }
    }
}