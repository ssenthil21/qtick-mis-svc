package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TopServiceDto {
    
    @NotNull
    private Long serviceId;
    
    @NotNull
    private String serviceName;
    
    @NotNull
    private BigDecimal revenue;
    
    @NotNull
    private Integer jobs;
    
    @NotNull
    private BigDecimal contribution; // percentage contribution to total revenue
    
    private Integer rank;
    
    // Constructors
    public TopServiceDto() {}
    
    public TopServiceDto(Long serviceId, String serviceName, BigDecimal revenue, 
                        Integer jobs, BigDecimal contribution) {
        this.serviceId = serviceId;
        this.serviceName = serviceName;
        this.revenue = revenue;
        this.jobs = jobs;
        this.contribution = contribution;
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
    
    public BigDecimal getContribution() { return contribution; }
    public void setContribution(BigDecimal contribution) { this.contribution = contribution; }
    
    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }
}