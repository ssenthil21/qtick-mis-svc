package com.qtick.mis.document;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * MongoDB document representing precomputed dashboard metrics snapshots.
 * Stores daily, weekly, and monthly aggregated business metrics for fast retrieval.
 */
@Document(collection = "dashboard_snapshots")
@CompoundIndexes({
    @CompoundIndex(name = "idx_snapshot_biz_date", def = "{'bizId': 1, 'snapshotDate': -1}"),
    @CompoundIndex(name = "idx_snapshot_biz_period", def = "{'bizId': 1, 'period': 1, 'snapshotDate': -1}"),
    @CompoundIndex(name = "idx_snapshot_branch", def = "{'bizId': 1, 'branchId': 1, 'snapshotDate': -1}")
})
public class DashboardSnapshot {

    @Id
    private String id;

    @Indexed
    private Long bizId;

    private Long branchId;

    @Indexed
    private LocalDate snapshotDate;

    @Indexed
    private String period; // daily, weekly, monthly

    // Core KPI metrics
    private BigDecimal grossSales = BigDecimal.ZERO;

    private BigDecimal netSales = BigDecimal.ZERO;

    private Integer bills = 0;

    private BigDecimal avgBill = BigDecimal.ZERO;

    private Integer newLeads = 0;

    private Integer totalLeads = 0;

    private Integer missedLeads = 0;

    private Integer appointments = 0;

    private Integer returningCustomers = 0;

    // Additional metrics
    private Integer walkIns = 0;

    private Integer completedAppointments = 0;

    private Integer cancelledAppointments = 0;

    private Integer noShows = 0;

    private BigDecimal totalDiscounts = BigDecimal.ZERO;

    private BigDecimal totalTax = BigDecimal.ZERO;

    private Integer newCustomers = 0;

    private Integer activeCustomers = 0;

    // Service breakdown
    private List<ServiceMetric> topServices = new ArrayList<>();

    // Staff performance
    private List<StaffMetric> topStaff = new ArrayList<>();

    // Additional metadata
    private Map<String, Object> additionalMetrics = new HashMap<>();

    @CreatedDate
    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    // Constructors
    public DashboardSnapshot() {}

    public DashboardSnapshot(Long bizId, LocalDate snapshotDate, String period) {
        this.bizId = bizId;
        this.snapshotDate = snapshotDate;
        this.period = period;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getBizId() {
        return bizId;
    }

    public void setBizId(Long bizId) {
        this.bizId = bizId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public LocalDate getSnapshotDate() {
        return snapshotDate;
    }

    public void setSnapshotDate(LocalDate snapshotDate) {
        this.snapshotDate = snapshotDate;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public BigDecimal getGrossSales() {
        return grossSales;
    }

    public void setGrossSales(BigDecimal grossSales) {
        this.grossSales = grossSales;
    }

    public BigDecimal getNetSales() {
        return netSales;
    }

    public void setNetSales(BigDecimal netSales) {
        this.netSales = netSales;
    }

    public Integer getBills() {
        return bills;
    }

    public void setBills(Integer bills) {
        this.bills = bills;
    }

    public BigDecimal getAvgBill() {
        return avgBill;
    }

    public void setAvgBill(BigDecimal avgBill) {
        this.avgBill = avgBill;
    }

    public Integer getNewLeads() {
        return newLeads;
    }

    public void setNewLeads(Integer newLeads) {
        this.newLeads = newLeads;
    }

    public Integer getTotalLeads() {
        return totalLeads;
    }

    public void setTotalLeads(Integer totalLeads) {
        this.totalLeads = totalLeads;
    }

    public Integer getMissedLeads() {
        return missedLeads;
    }

    public void setMissedLeads(Integer missedLeads) {
        this.missedLeads = missedLeads;
    }

    public Integer getAppointments() {
        return appointments;
    }

    public void setAppointments(Integer appointments) {
        this.appointments = appointments;
    }

    public Integer getReturningCustomers() {
        return returningCustomers;
    }

    public void setReturningCustomers(Integer returningCustomers) {
        this.returningCustomers = returningCustomers;
    }

    public Integer getWalkIns() {
        return walkIns;
    }

    public void setWalkIns(Integer walkIns) {
        this.walkIns = walkIns;
    }

    public Integer getCompletedAppointments() {
        return completedAppointments;
    }

    public void setCompletedAppointments(Integer completedAppointments) {
        this.completedAppointments = completedAppointments;
    }

    public Integer getCancelledAppointments() {
        return cancelledAppointments;
    }

    public void setCancelledAppointments(Integer cancelledAppointments) {
        this.cancelledAppointments = cancelledAppointments;
    }

    public Integer getNoShows() {
        return noShows;
    }

    public void setNoShows(Integer noShows) {
        this.noShows = noShows;
    }

    public BigDecimal getTotalDiscounts() {
        return totalDiscounts;
    }

    public void setTotalDiscounts(BigDecimal totalDiscounts) {
        this.totalDiscounts = totalDiscounts;
    }

    public BigDecimal getTotalTax() {
        return totalTax;
    }

    public void setTotalTax(BigDecimal totalTax) {
        this.totalTax = totalTax;
    }

    public Integer getNewCustomers() {
        return newCustomers;
    }

    public void setNewCustomers(Integer newCustomers) {
        this.newCustomers = newCustomers;
    }

    public Integer getActiveCustomers() {
        return activeCustomers;
    }

    public void setActiveCustomers(Integer activeCustomers) {
        this.activeCustomers = activeCustomers;
    }

    public List<ServiceMetric> getTopServices() {
        return topServices;
    }

    public void setTopServices(List<ServiceMetric> topServices) {
        this.topServices = topServices != null ? topServices : new ArrayList<>();
    }

    public List<StaffMetric> getTopStaff() {
        return topStaff;
    }

    public void setTopStaff(List<StaffMetric> topStaff) {
        this.topStaff = topStaff != null ? topStaff : new ArrayList<>();
    }

    public Map<String, Object> getAdditionalMetrics() {
        return additionalMetrics;
    }

    public void setAdditionalMetrics(Map<String, Object> additionalMetrics) {
        this.additionalMetrics = additionalMetrics != null ? additionalMetrics : new HashMap<>();
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    public void addServiceMetric(ServiceMetric serviceMetric) {
        if (this.topServices == null) {
            this.topServices = new ArrayList<>();
        }
        this.topServices.add(serviceMetric);
    }

    public void addStaffMetric(StaffMetric staffMetric) {
        if (this.topStaff == null) {
            this.topStaff = new ArrayList<>();
        }
        this.topStaff.add(staffMetric);
    }

    public void addAdditionalMetric(String key, Object value) {
        if (this.additionalMetrics == null) {
            this.additionalMetrics = new HashMap<>();
        }
        this.additionalMetrics.put(key, value);
    }

    @Override
    public String toString() {
        return "DashboardSnapshot{" +
                "id='" + id + '\'' +
                ", bizId=" + bizId +
                ", snapshotDate=" + snapshotDate +
                ", period='" + period + '\'' +
                ", grossSales=" + grossSales +
                ", bills=" + bills +
                '}';
    }

    // Nested classes for service and staff metrics
    public static class ServiceMetric {
        private Long serviceId;
        private String serviceName;
        private BigDecimal revenue;
        private Integer jobs;
        private BigDecimal contribution;
        private Integer rank;

        // Constructors
        public ServiceMetric() {}

        public ServiceMetric(Long serviceId, String serviceName, BigDecimal revenue, Integer jobs) {
            this.serviceId = serviceId;
            this.serviceName = serviceName;
            this.revenue = revenue;
            this.jobs = jobs;
        }

        // Getters and Setters
        public Long getServiceId() {
            return serviceId;
        }

        public void setServiceId(Long serviceId) {
            this.serviceId = serviceId;
        }

        public String getServiceName() {
            return serviceName;
        }

        public void setServiceName(String serviceName) {
            this.serviceName = serviceName;
        }

        public BigDecimal getRevenue() {
            return revenue;
        }

        public void setRevenue(BigDecimal revenue) {
            this.revenue = revenue;
        }

        public Integer getJobs() {
            return jobs;
        }

        public void setJobs(Integer jobs) {
            this.jobs = jobs;
        }

        public BigDecimal getContribution() {
            return contribution;
        }

        public void setContribution(BigDecimal contribution) {
            this.contribution = contribution;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }
    }

    public static class StaffMetric {
        private Long staffId;
        private String staffName;
        private BigDecimal revenue;
        private Integer jobs;
        private BigDecimal rating;
        private Integer rank;

        // Constructors
        public StaffMetric() {}

        public StaffMetric(Long staffId, String staffName, BigDecimal revenue, Integer jobs) {
            this.staffId = staffId;
            this.staffName = staffName;
            this.revenue = revenue;
            this.jobs = jobs;
        }

        // Getters and Setters
        public Long getStaffId() {
            return staffId;
        }

        public void setStaffId(Long staffId) {
            this.staffId = staffId;
        }

        public String getStaffName() {
            return staffName;
        }

        public void setStaffName(String staffName) {
            this.staffName = staffName;
        }

        public BigDecimal getRevenue() {
            return revenue;
        }

        public void setRevenue(BigDecimal revenue) {
            this.revenue = revenue;
        }

        public Integer getJobs() {
            return jobs;
        }

        public void setJobs(Integer jobs) {
            this.jobs = jobs;
        }

        public BigDecimal getRating() {
            return rating;
        }

        public void setRating(BigDecimal rating) {
            this.rating = rating;
        }

        public Integer getRank() {
            return rank;
        }

        public void setRank(Integer rank) {
            this.rank = rank;
        }
    }
}