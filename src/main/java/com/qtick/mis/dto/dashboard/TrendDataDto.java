package com.qtick.mis.dto.dashboard;

import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class TrendDataDto {
    
    @NotNull
    private LocalDate date;
    
    @NotNull
    private String metric;
    
    @NotNull
    private BigDecimal value;
    
    private String period; // day, week, month
    
    // Constructors
    public TrendDataDto() {}
    
    public TrendDataDto(LocalDate date, String metric, BigDecimal value) {
        this.date = date;
        this.metric = metric;
        this.value = value;
    }
    
    public TrendDataDto(LocalDate date, String metric, BigDecimal value, String period) {
        this.date = date;
        this.metric = metric;
        this.value = value;
        this.period = period;
    }
    
    // Getters and Setters
    public LocalDate getDate() { return date; }
    public void setDate(LocalDate date) { this.date = date; }
    
    public String getMetric() { return metric; }
    public void setMetric(String metric) { this.metric = metric; }
    
    public BigDecimal getValue() { return value; }
    public void setValue(BigDecimal value) { this.value = value; }
    
    public String getPeriod() { return period; }
    public void setPeriod(String period) { this.period = period; }
}