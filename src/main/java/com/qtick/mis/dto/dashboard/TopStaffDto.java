package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TopStaffDto {
    
    @NotNull
    private Long staffId;
    
    @NotNull
    private String staffName;
    
    @NotNull
    private BigDecimal revenue;
    
    @NotNull
    private Integer jobs;
    
    @NotNull
    private BigDecimal rating;
    
    private Integer rank;
    
    // Constructors
    public TopStaffDto() {}
    
    public TopStaffDto(Long staffId, String staffName, BigDecimal revenue, 
                      Integer jobs, BigDecimal rating) {
        this.staffId = staffId;
        this.staffName = staffName;
        this.revenue = revenue;
        this.jobs = jobs;
        this.rating = rating;
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
    
    public BigDecimal getRating() { return rating; }
    public void setRating(BigDecimal rating) { this.rating = rating; }
    
    public Integer getRank() { return rank; }
    public void setRank(Integer rank) { this.rank = rank; }
}