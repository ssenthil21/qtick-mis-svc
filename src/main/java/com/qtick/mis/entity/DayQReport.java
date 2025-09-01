package com.qtick.mis.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "day_q_report")
public class DayQReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long bizId;

    @Column(nullable = false)
    private Integer periodId;

    @Column(length = 3, nullable = false)
    private String periodType;

    private Integer served;
    private Double sales;
    private Double netSales;
    private Integer queued;
    private Integer missed;
    private Integer leftQ;
    private Integer cancelled;
    private Integer peeped;

    // getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getBizId() { return bizId; }
    public void setBizId(Long bizId) { this.bizId = bizId; }

    public Integer getPeriodId() { return periodId; }
    public void setPeriodId(Integer periodId) { this.periodId = periodId; }

    public String getPeriodType() { return periodType; }
    public void setPeriodType(String periodType) { this.periodType = periodType; }

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

    public Integer getPeeped() { return peeped; }
    public void setPeeped(Integer peeped) { this.peeped = peeped; }
}
