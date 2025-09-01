package com.qtick.mis.dto.dashboard;

import java.time.LocalDate;

public class BusinessViewCountDto {
    private Long bizId;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer served;
    private Double sales;
    private Double netSales;
    private Integer queued;
    private Integer missed;
    private Integer leftQ;
    private Integer cancelled;
    private Integer viewCount;

    public Long getBizId() { return bizId; }
    public void setBizId(Long bizId) { this.bizId = bizId; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public LocalDate getEndDate() { return endDate; }
    public void setEndDate(LocalDate endDate) { this.endDate = endDate; }

    public Integer getServed() { return served; }
    public void setServed(Integer served) { this.served = served; }

    public Double getSales() { return sales; }
    public void setSales(Double sales) { this.sales = sales; }

    public Double getNetSales() { return netSales; }
    public void setNetSales(Double netSales) { this.netSales = netSales; }

    public Integer getQueued() { return queued; }
    public void setQueued(Integer queued) { this.queued = queued; }

    public Integer getMissed() { return missed; }
    public void setMissed(Integer missed) { this.missed = missed; }

    public Integer getLeftQ() { return leftQ; }
    public void setLeftQ(Integer leftQ) { this.leftQ = leftQ; }

    public Integer getCancelled() { return cancelled; }
    public void setCancelled(Integer cancelled) { this.cancelled = cancelled; }

    public Integer getViewCount() { return viewCount; }
    public void setViewCount(Integer viewCount) { this.viewCount = viewCount; }
}
