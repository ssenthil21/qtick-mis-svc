package com.qtick.mis.dto.analytics;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class FunnelAnalyticsDto {
    
    @NotNull
    private BigDecimal leadConversionRate;
    
    @NotNull
    private BigDecimal winRate;
    
    @NotNull
    private BigDecimal averageCycleTime; // in days
    
    private List<StageConversion> stageConversions;
    private List<SourcePerformance> sourcePerformance;
    
    // Constructors
    public FunnelAnalyticsDto() {}
    
    public FunnelAnalyticsDto(BigDecimal leadConversionRate, BigDecimal winRate, BigDecimal averageCycleTime) {
        this.leadConversionRate = leadConversionRate;
        this.winRate = winRate;
        this.averageCycleTime = averageCycleTime;
    }
    
    // Getters and Setters
    public BigDecimal getLeadConversionRate() { return leadConversionRate; }
    public void setLeadConversionRate(BigDecimal leadConversionRate) { this.leadConversionRate = leadConversionRate; }
    
    public BigDecimal getWinRate() { return winRate; }
    public void setWinRate(BigDecimal winRate) { this.winRate = winRate; }
    
    public BigDecimal getAverageCycleTime() { return averageCycleTime; }
    public void setAverageCycleTime(BigDecimal averageCycleTime) { this.averageCycleTime = averageCycleTime; }
    
    public List<StageConversion> getStageConversions() { return stageConversions; }
    public void setStageConversions(List<StageConversion> stageConversions) { this.stageConversions = stageConversions; }
    
    public List<SourcePerformance> getSourcePerformance() { return sourcePerformance; }
    public void setSourcePerformance(List<SourcePerformance> sourcePerformance) { this.sourcePerformance = sourcePerformance; }
    
    // Inner classes
    public static class StageConversion {
        private String fromStage;
        private String toStage;
        private Integer totalLeads;
        private Integer convertedLeads;
        private BigDecimal conversionRate;
        private BigDecimal averageTimeInStage; // in days
        
        public StageConversion() {}
        
        public StageConversion(String fromStage, String toStage, Integer totalLeads, 
                              Integer convertedLeads, BigDecimal conversionRate, BigDecimal averageTimeInStage) {
            this.fromStage = fromStage;
            this.toStage = toStage;
            this.totalLeads = totalLeads;
            this.convertedLeads = convertedLeads;
            this.conversionRate = conversionRate;
            this.averageTimeInStage = averageTimeInStage;
        }
        
        public String getFromStage() { return fromStage; }
        public void setFromStage(String fromStage) { this.fromStage = fromStage; }
        
        public String getToStage() { return toStage; }
        public void setToStage(String toStage) { this.toStage = toStage; }
        
        public Integer getTotalLeads() { return totalLeads; }
        public void setTotalLeads(Integer totalLeads) { this.totalLeads = totalLeads; }
        
        public Integer getConvertedLeads() { return convertedLeads; }
        public void setConvertedLeads(Integer convertedLeads) { this.convertedLeads = convertedLeads; }
        
        public BigDecimal getConversionRate() { return conversionRate; }
        public void setConversionRate(BigDecimal conversionRate) { this.conversionRate = conversionRate; }
        
        public BigDecimal getAverageTimeInStage() { return averageTimeInStage; }
        public void setAverageTimeInStage(BigDecimal averageTimeInStage) { this.averageTimeInStage = averageTimeInStage; }
    }
    
    public static class SourcePerformance {
        private String source;
        private Integer totalLeads;
        private Integer convertedLeads;
        private BigDecimal conversionRate;
        private BigDecimal averageCycleTime;
        private BigDecimal totalRevenue;
        
        public SourcePerformance() {}
        
        public SourcePerformance(String source, Integer totalLeads, Integer convertedLeads, 
                                BigDecimal conversionRate, BigDecimal averageCycleTime, BigDecimal totalRevenue) {
            this.source = source;
            this.totalLeads = totalLeads;
            this.convertedLeads = convertedLeads;
            this.conversionRate = conversionRate;
            this.averageCycleTime = averageCycleTime;
            this.totalRevenue = totalRevenue;
        }
        
        public String getSource() { return source; }
        public void setSource(String source) { this.source = source; }
        
        public Integer getTotalLeads() { return totalLeads; }
        public void setTotalLeads(Integer totalLeads) { this.totalLeads = totalLeads; }
        
        public Integer getConvertedLeads() { return convertedLeads; }
        public void setConvertedLeads(Integer convertedLeads) { this.convertedLeads = convertedLeads; }
        
        public BigDecimal getConversionRate() { return conversionRate; }
        public void setConversionRate(BigDecimal conversionRate) { this.conversionRate = conversionRate; }
        
        public BigDecimal getAverageCycleTime() { return averageCycleTime; }
        public void setAverageCycleTime(BigDecimal averageCycleTime) { this.averageCycleTime = averageCycleTime; }
        
        public BigDecimal getTotalRevenue() { return totalRevenue; }
        public void setTotalRevenue(BigDecimal totalRevenue) { this.totalRevenue = totalRevenue; }
    }
}