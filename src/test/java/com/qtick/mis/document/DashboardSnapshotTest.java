package com.qtick.mis.document;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DashboardSnapshot document.
 */
class DashboardSnapshotTest {

    @Test
    void shouldCreateDashboardSnapshotWithRequiredFields() {
        // Given
        Long bizId = 123L;
        LocalDate snapshotDate = LocalDate.now();
        String period = "daily";

        // When
        DashboardSnapshot snapshot = new DashboardSnapshot(bizId, snapshotDate, period);

        // Then
        assertEquals(bizId, snapshot.getBizId());
        assertEquals(snapshotDate, snapshot.getSnapshotDate());
        assertEquals(period, snapshot.getPeriod());
        assertEquals(BigDecimal.ZERO, snapshot.getGrossSales());
        assertEquals(BigDecimal.ZERO, snapshot.getNetSales());
        assertEquals(0, snapshot.getBills());
        assertEquals(BigDecimal.ZERO, snapshot.getAvgBill());
        assertEquals(0, snapshot.getNewLeads());
        assertEquals(0, snapshot.getTotalLeads());
        assertEquals(0, snapshot.getMissedLeads());
        assertEquals(0, snapshot.getAppointments());
        assertEquals(0, snapshot.getReturningCustomers());
        assertTrue(snapshot.getTopServices().isEmpty());
        assertTrue(snapshot.getTopStaff().isEmpty());
        assertTrue(snapshot.getAdditionalMetrics().isEmpty());
    }

    @Test
    void shouldAddServiceMetric() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot(123L, LocalDate.now(), "daily");
        DashboardSnapshot.ServiceMetric serviceMetric = new DashboardSnapshot.ServiceMetric(
            101L, "Haircut", BigDecimal.valueOf(5000), 25);
        serviceMetric.setContribution(BigDecimal.valueOf(35.5));
        serviceMetric.setRank(1);

        // When
        snapshot.addServiceMetric(serviceMetric);

        // Then
        assertEquals(1, snapshot.getTopServices().size());
        DashboardSnapshot.ServiceMetric addedMetric = snapshot.getTopServices().get(0);
        assertEquals(101L, addedMetric.getServiceId());
        assertEquals("Haircut", addedMetric.getServiceName());
        assertEquals(BigDecimal.valueOf(5000), addedMetric.getRevenue());
        assertEquals(25, addedMetric.getJobs());
        assertEquals(BigDecimal.valueOf(35.5), addedMetric.getContribution());
        assertEquals(1, addedMetric.getRank());
    }

    @Test
    void shouldAddStaffMetric() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot(123L, LocalDate.now(), "daily");
        DashboardSnapshot.StaffMetric staffMetric = new DashboardSnapshot.StaffMetric(
            201L, "John Stylist", BigDecimal.valueOf(8000), 40);
        staffMetric.setRating(BigDecimal.valueOf(4.8));
        staffMetric.setRank(1);

        // When
        snapshot.addStaffMetric(staffMetric);

        // Then
        assertEquals(1, snapshot.getTopStaff().size());
        DashboardSnapshot.StaffMetric addedMetric = snapshot.getTopStaff().get(0);
        assertEquals(201L, addedMetric.getStaffId());
        assertEquals("John Stylist", addedMetric.getStaffName());
        assertEquals(BigDecimal.valueOf(8000), addedMetric.getRevenue());
        assertEquals(40, addedMetric.getJobs());
        assertEquals(BigDecimal.valueOf(4.8), addedMetric.getRating());
        assertEquals(1, addedMetric.getRank());
    }

    @Test
    void shouldAddAdditionalMetric() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot(123L, LocalDate.now(), "daily");

        // When
        snapshot.addAdditionalMetric("conversionRate", 15.5);
        snapshot.addAdditionalMetric("avgWaitTime", 12);
        snapshot.addAdditionalMetric("customerSatisfaction", 4.2);

        // Then
        assertEquals(3, snapshot.getAdditionalMetrics().size());
        assertEquals(15.5, snapshot.getAdditionalMetrics().get("conversionRate"));
        assertEquals(12, snapshot.getAdditionalMetrics().get("avgWaitTime"));
        assertEquals(4.2, snapshot.getAdditionalMetrics().get("customerSatisfaction"));
    }

    @Test
    void shouldSetAllKpiFields() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot();

        // When
        snapshot.setBizId(123L);
        snapshot.setBranchId(456L);
        snapshot.setSnapshotDate(LocalDate.of(2024, 1, 15));
        snapshot.setPeriod("weekly");
        snapshot.setGrossSales(BigDecimal.valueOf(50000));
        snapshot.setNetSales(BigDecimal.valueOf(45000));
        snapshot.setBills(150);
        snapshot.setAvgBill(BigDecimal.valueOf(300));
        snapshot.setNewLeads(25);
        snapshot.setTotalLeads(100);
        snapshot.setMissedLeads(5);
        snapshot.setAppointments(120);
        snapshot.setReturningCustomers(80);
        snapshot.setWalkIns(30);
        snapshot.setCompletedAppointments(110);
        snapshot.setCancelledAppointments(8);
        snapshot.setNoShows(2);
        snapshot.setTotalDiscounts(BigDecimal.valueOf(2500));
        snapshot.setTotalTax(BigDecimal.valueOf(4500));
        snapshot.setNewCustomers(20);
        snapshot.setActiveCustomers(200);

        LocalDateTime now = LocalDateTime.now();
        snapshot.setCreatedAt(now);
        snapshot.setUpdatedAt(now);

        // Then
        assertEquals(123L, snapshot.getBizId());
        assertEquals(456L, snapshot.getBranchId());
        assertEquals(LocalDate.of(2024, 1, 15), snapshot.getSnapshotDate());
        assertEquals("weekly", snapshot.getPeriod());
        assertEquals(BigDecimal.valueOf(50000), snapshot.getGrossSales());
        assertEquals(BigDecimal.valueOf(45000), snapshot.getNetSales());
        assertEquals(150, snapshot.getBills());
        assertEquals(BigDecimal.valueOf(300), snapshot.getAvgBill());
        assertEquals(25, snapshot.getNewLeads());
        assertEquals(100, snapshot.getTotalLeads());
        assertEquals(5, snapshot.getMissedLeads());
        assertEquals(120, snapshot.getAppointments());
        assertEquals(80, snapshot.getReturningCustomers());
        assertEquals(30, snapshot.getWalkIns());
        assertEquals(110, snapshot.getCompletedAppointments());
        assertEquals(8, snapshot.getCancelledAppointments());
        assertEquals(2, snapshot.getNoShows());
        assertEquals(BigDecimal.valueOf(2500), snapshot.getTotalDiscounts());
        assertEquals(BigDecimal.valueOf(4500), snapshot.getTotalTax());
        assertEquals(20, snapshot.getNewCustomers());
        assertEquals(200, snapshot.getActiveCustomers());
        assertEquals(now, snapshot.getCreatedAt());
        assertEquals(now, snapshot.getUpdatedAt());
    }

    @Test
    void shouldHandleNullCollections() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot();

        // When
        snapshot.setTopServices(null);
        snapshot.setTopStaff(null);
        snapshot.setAdditionalMetrics(null);

        // Then
        assertNotNull(snapshot.getTopServices());
        assertNotNull(snapshot.getTopStaff());
        assertNotNull(snapshot.getAdditionalMetrics());
        assertTrue(snapshot.getTopServices().isEmpty());
        assertTrue(snapshot.getTopStaff().isEmpty());
        assertTrue(snapshot.getAdditionalMetrics().isEmpty());
    }

    @Test
    void shouldAddMetricsToNullCollections() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot();
        snapshot.setTopServices(null);
        snapshot.setTopStaff(null);
        snapshot.setAdditionalMetrics(null);

        DashboardSnapshot.ServiceMetric serviceMetric = new DashboardSnapshot.ServiceMetric(
            101L, "Service", BigDecimal.valueOf(1000), 10);
        DashboardSnapshot.StaffMetric staffMetric = new DashboardSnapshot.StaffMetric(
            201L, "Staff", BigDecimal.valueOf(2000), 20);

        // When
        snapshot.addServiceMetric(serviceMetric);
        snapshot.addStaffMetric(staffMetric);
        snapshot.addAdditionalMetric("test", "value");

        // Then
        assertEquals(1, snapshot.getTopServices().size());
        assertEquals(1, snapshot.getTopStaff().size());
        assertEquals(1, snapshot.getAdditionalMetrics().size());
    }

    @Test
    void shouldHaveProperToString() {
        // Given
        DashboardSnapshot snapshot = new DashboardSnapshot(123L, LocalDate.of(2024, 1, 15), "daily");
        snapshot.setId("snapshot123");
        snapshot.setGrossSales(BigDecimal.valueOf(10000));
        snapshot.setBills(50);

        // When
        String toString = snapshot.toString();

        // Then
        assertNotNull(toString);
        assertTrue(toString.contains("id='snapshot123'"));
        assertTrue(toString.contains("bizId=123"));
        assertTrue(toString.contains("snapshotDate=2024-01-15"));
        assertTrue(toString.contains("period='daily'"));
        assertTrue(toString.contains("grossSales=10000"));
        assertTrue(toString.contains("bills=50"));
    }

    @Test
    void shouldCreateServiceMetricWithAllFields() {
        // Given & When
        DashboardSnapshot.ServiceMetric metric = new DashboardSnapshot.ServiceMetric(
            101L, "Haircut", BigDecimal.valueOf(5000), 25);
        metric.setContribution(BigDecimal.valueOf(35.5));
        metric.setRank(1);

        // Then
        assertEquals(101L, metric.getServiceId());
        assertEquals("Haircut", metric.getServiceName());
        assertEquals(BigDecimal.valueOf(5000), metric.getRevenue());
        assertEquals(25, metric.getJobs());
        assertEquals(BigDecimal.valueOf(35.5), metric.getContribution());
        assertEquals(1, metric.getRank());
    }

    @Test
    void shouldCreateStaffMetricWithAllFields() {
        // Given & When
        DashboardSnapshot.StaffMetric metric = new DashboardSnapshot.StaffMetric(
            201L, "John Stylist", BigDecimal.valueOf(8000), 40);
        metric.setRating(BigDecimal.valueOf(4.8));
        metric.setRank(1);

        // Then
        assertEquals(201L, metric.getStaffId());
        assertEquals("John Stylist", metric.getStaffName());
        assertEquals(BigDecimal.valueOf(8000), metric.getRevenue());
        assertEquals(40, metric.getJobs());
        assertEquals(BigDecimal.valueOf(4.8), metric.getRating());
        assertEquals(1, metric.getRank());
    }
}