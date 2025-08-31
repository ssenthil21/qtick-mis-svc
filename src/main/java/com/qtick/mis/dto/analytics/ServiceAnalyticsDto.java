package com.qtick.mis.dto.analytics;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class ServiceAnalyticsDto {
    
    @NotNull
    private Long serviceId;
    
    @NotNull
    private String serviceName;
    
    @NotNull
    private BigDecimal revenue;
    
    @NotNull
    private Integer jobs;
    
    @NotNull
    private BigDecimal mix; // percentage of total jobs
    
    @NotNull
    private BigDecimal yearOverYearGrowth;
    
    private BigDecimal averagePrice;
    private Integer totalCustomers;
    private BigDecimal customerSatisfactionScore;
    
    // Trend data
    private List<TrendDataPoint> monthlyTrends;
    
    // Constructors
    public ServiceAnalyticsDto() {}
    
    public ServiceAnalyticsDto(Long serviceId, String serviceName, BigDecimal revenue, 
                              Integer jobs, BigDecimal mix, BigDecimal yearOverYearGrowth) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.revenue = revenue;
        this.jobs = jobs;
        this.mix = mix;
        this.yearOverYearGrowth = yearOverYearGrowth;
    }
    
    // Getters and Setters
    public Long getServiceId() { return serviceId; }
    public void setServiceId(Long serviceId) { this.serviceId = serviceId; }
    
    public String getServiceName() { return serviceName; }
    public void setServiceName(String serviceName) { this.serviceName = serviceName; }
    
    public BigDecimal getRevenue() { return revenue; }
    public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
    
    public Integer getJobs() { return jobs; }
    public void setJobs(Integer jobs) { this.jobs = jobs; }
    
    public BigDecimal getMix() { return mix; }
    public void setMix(BigDecimal mix) { this.mix = mix; }
    
    public BigDecimal getYearOverYearGrowth() { return yearOverYearGrowth; }
    public void setYearOverYearGrowth(BigDecimal yearOverYearGrowth) { this.yearOverYearGrowth = yearOverYearGrowth; }
    
    public BigDecimal getAveragePrice() { return averagePrice; }
    public void setAveragePrice(BigDecimal averagePrice) { this.averagePrice = averagePrice; }
    
    public Integer getTotalCustomers() { return totalCustomers; }
    public void setTotalCustomers(Integer totalCustomers) { this.totalCustomers = totalCustomers; }
    
    public BigDecimal getCustomerSatisfactionScore() { return customerSatisfactionScore; }
    public void setCustomerSatisfactionScore(BigDecimal customerSatisfactionScore) { this.customerSatisfactionScore = customerSatisfactionScore; }
    
    public List<TrendDataPoint> getMonthlyTrends() { return monthlyTrends; }
    public void setMonthlyTrends(List<TrendDataPoint> monthlyTrends) { this.monthlyTrends = monthlyTrends; }
    
    // Inner class for trend data
    public static class TrendDataPoint {
        private String month;
        private BigDecimal revenue;
        private Integer jobs;
        
        public TrendDataPoint() {}
        
        public TrendDataPoint(String month, BigDecimal revenue, Integer jobs) {
            this.month = month;
            this.revenue = revenue;
            this.jobs = jobs;
        }
        
        public String getMonth() { return month; }
        public void setMonth(String month) { this.month = month; }
        
        public BigDecimal getRevenue() { return revenue; }
        public void setRevenue(BigDecimal revenue) { this.revenue = revenue; }
        
        public Integer getJobs() { return jobs; }
        public void setJobs(Integer jobs) { this.jobs = jobs; }
    }
}