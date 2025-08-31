package com.qtick.mis.repository.mongo;

import com.qtick.mis.document.DashboardSnapshot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for DashboardSnapshotRepository.
 */
@DataMongoTest
@ActiveProfiles("test")
class DashboardSnapshotRepositoryTest {

    @Autowired
    private DashboardSnapshotRepository dashboardSnapshotRepository;

    private DashboardSnapshot testSnapshot1;
    private DashboardSnapshot testSnapshot2;
    private DashboardSnapshot testSnapshot3;

    @BeforeEach
    void setUp() {
        // Clear the collection
        dashboardSnapshotRepository.deleteAll();

        // Create test snapshots
        testSnapshot1 = new DashboardSnapshot(123L, LocalDate.of(2024, 1, 15), "daily");
        testSnapshot1.setBranchId(201L);
        testSnapshot1.setGrossSales(BigDecimal.valueOf(10000));
        testSnapshot1.setNetSales(BigDecimal.valueOf(9500));
        testSnapshot1.setBills(50);
        testSnapshot1.setAvgBill(BigDecimal.valueOf(190));
        testSnapshot1.setNewLeads(15);
        testSnapshot1.setTotalLeads(45);
        testSnapshot1.setMissedLeads(3);
        testSnapshot1.setAppointments(40);
        testSnapshot1.setReturningCustomers(25);

        DashboardSnapshot.ServiceMetric service1 = new DashboardSnapshot.ServiceMetric(101L, "Haircut", BigDecimal.valueOf(5000), 25);
        service1.setContribution(BigDecimal.valueOf(50.0));
        service1.setRank(1);
        testSnapshot1.addServiceMetric(service1);

        DashboardSnapshot.StaffMetric staff1 = new DashboardSnapshot.StaffMetric(201L, "John Stylist", BigDecimal.valueOf(6000), 30);
        staff1.setRating(BigDecimal.valueOf(4.8));
        staff1.setRank(1);
        testSnapshot1.addStaffMetric(staff1);

        testSnapshot2 = new DashboardSnapshot(123L, LocalDate.of(2024, 1, 16), "daily");
        testSnapshot2.setBranchId(201L);
        testSnapshot2.setGrossSales(BigDecimal.valueOf(12000));
        testSnapshot2.setNetSales(BigDecimal.valueOf(11400));
        testSnapshot2.setBills(60);
        testSnapshot2.setAvgBill(BigDecimal.valueOf(190));
        testSnapshot2.setNewLeads(18);
        testSnapshot2.setTotalLeads(50);
        testSnapshot2.setMissedLeads(2);
        testSnapshot2.setAppointments(45);
        testSnapshot2.setReturningCustomers(30);

        testSnapshot3 = new DashboardSnapshot(456L, LocalDate.of(2024, 1, 15), "daily");
        testSnapshot3.setBranchId(202L);
        testSnapshot3.setGrossSales(BigDecimal.valueOf(8000));
        testSnapshot3.setNetSales(BigDecimal.valueOf(7600));
        testSnapshot3.setBills(40);
        testSnapshot3.setAvgBill(BigDecimal.valueOf(190));
        testSnapshot3.setNewLeads(12);
        testSnapshot3.setTotalLeads(35);
        testSnapshot3.setMissedLeads(1);
        testSnapshot3.setAppointments(35);
        testSnapshot3.setReturningCustomers(20);

        dashboardSnapshotRepository.save(testSnapshot1);
        dashboardSnapshotRepository.save(testSnapshot2);
        dashboardSnapshotRepository.save(testSnapshot3);
    }

    @Test
    void shouldFindByBizIdOrderBySnapshotDateDesc() {
        // When
        List<DashboardSnapshot> snapshots = dashboardSnapshotRepository.findByBizIdOrderBySnapshotDateDesc(123L);

        // Then
        assertEquals(2, snapshots.size());
        assertTrue(snapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot1.getId())));
        assertTrue(snapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot2.getId())));
        assertFalse(snapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot3.getId()))); // Different bizId
        // Should be ordered by date descending
        assertEquals(LocalDate.of(2024, 1, 16), snapshots.get(0).getSnapshotDate());
        assertEquals(LocalDate.of(2024, 1, 15), snapshots.get(1).getSnapshotDate());
    }

    @Test
    void shouldFindByIdAndBizId() {
        // When
        Optional<DashboardSnapshot> found = dashboardSnapshotRepository.findByIdAndBizId(testSnapshot1.getId(), 123L);
        Optional<DashboardSnapshot> notFound = dashboardSnapshotRepository.findByIdAndBizId(testSnapshot1.getId(), 456L);

        // Then
        assertTrue(found.isPresent());
        assertEquals(testSnapshot1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndPeriod() {
        // When
        List<DashboardSnapshot> dailySnapshots = dashboardSnapshotRepository.findByBizIdAndPeriodOrderBySnapshotDateDesc(123L, "daily");
        List<DashboardSnapshot> weeklySnapshots = dashboardSnapshotRepository.findByBizIdAndPeriodOrderBySnapshotDateDesc(123L, "weekly");

        // Then
        assertEquals(2, dailySnapshots.size());
        assertEquals(0, weeklySnapshots.size());
    }

    @Test
    void shouldFindByBizIdAndSnapshotDateAndPeriod() {
        // When
        Optional<DashboardSnapshot> found = dashboardSnapshotRepository.findByBizIdAndSnapshotDateAndPeriod(123L, LocalDate.of(2024, 1, 15), "daily");
        Optional<DashboardSnapshot> notFound = dashboardSnapshotRepository.findByBizIdAndSnapshotDateAndPeriod(123L, LocalDate.of(2024, 1, 17), "daily");

        // Then
        assertTrue(found.isPresent());
        assertEquals(testSnapshot1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndSnapshotDateBetween() {
        // When
        List<DashboardSnapshot> snapshotsInRange = dashboardSnapshotRepository.findByBizIdAndSnapshotDateBetweenOrderBySnapshotDateDesc(
            123L, LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 16));

        // Then
        assertEquals(2, snapshotsInRange.size());
        assertTrue(snapshotsInRange.stream().anyMatch(s -> s.getId().equals(testSnapshot1.getId())));
        assertTrue(snapshotsInRange.stream().anyMatch(s -> s.getId().equals(testSnapshot2.getId())));
    }

    @Test
    void shouldFindByBizIdAndBranchId() {
        // When
        List<DashboardSnapshot> branch201Snapshots = dashboardSnapshotRepository.findByBizIdAndBranchIdOrderBySnapshotDateDesc(123L, 201L);
        List<DashboardSnapshot> branch202Snapshots = dashboardSnapshotRepository.findByBizIdAndBranchIdOrderBySnapshotDateDesc(123L, 202L);

        // Then
        assertEquals(2, branch201Snapshots.size());
        assertEquals(0, branch202Snapshots.size()); // testSnapshot3 has different bizId
    }

    @Test
    void shouldFindByBizIdAndBranchIdAndSnapshotDateAndPeriod() {
        // When
        Optional<DashboardSnapshot> found = dashboardSnapshotRepository.findByBizIdAndBranchIdAndSnapshotDateAndPeriod(
            123L, 201L, LocalDate.of(2024, 1, 15), "daily");
        Optional<DashboardSnapshot> notFound = dashboardSnapshotRepository.findByBizIdAndBranchIdAndSnapshotDateAndPeriod(
            123L, 999L, LocalDate.of(2024, 1, 15), "daily");

        // Then
        assertTrue(found.isPresent());
        assertEquals(testSnapshot1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindDailySnapshot() {
        // When
        Optional<DashboardSnapshot> found = dashboardSnapshotRepository.findDailySnapshot(123L, LocalDate.of(2024, 1, 15));
        Optional<DashboardSnapshot> notFound = dashboardSnapshotRepository.findDailySnapshot(123L, LocalDate.of(2024, 1, 17));

        // Then
        assertTrue(found.isPresent());
        assertEquals(testSnapshot1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindDailySnapshotForBranch() {
        // When
        Optional<DashboardSnapshot> found = dashboardSnapshotRepository.findDailySnapshotForBranch(123L, 201L, LocalDate.of(2024, 1, 15));
        Optional<DashboardSnapshot> notFound = dashboardSnapshotRepository.findDailySnapshotForBranch(123L, 999L, LocalDate.of(2024, 1, 15));

        // Then
        assertTrue(found.isPresent());
        assertEquals(testSnapshot1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindSnapshotsForTrendAnalysis() {
        // When
        List<DashboardSnapshot> trendSnapshots = dashboardSnapshotRepository.findSnapshotsForTrendAnalysis(
            123L, "daily", LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 17));

        // Then
        assertEquals(2, trendSnapshots.size());
        // Should be ordered by date ascending for trend analysis
        assertEquals(LocalDate.of(2024, 1, 15), trendSnapshots.get(0).getSnapshotDate());
        assertEquals(LocalDate.of(2024, 1, 16), trendSnapshots.get(1).getSnapshotDate());
    }

    @Test
    void shouldFindBranchSnapshotsForTrendAnalysis() {
        // When
        List<DashboardSnapshot> branchTrendSnapshots = dashboardSnapshotRepository.findBranchSnapshotsForTrendAnalysis(
            123L, 201L, "daily", LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 17));

        // Then
        assertEquals(2, branchTrendSnapshots.size());
        assertEquals(LocalDate.of(2024, 1, 15), branchTrendSnapshots.get(0).getSnapshotDate());
        assertEquals(LocalDate.of(2024, 1, 16), branchTrendSnapshots.get(1).getSnapshotDate());
    }

    @Test
    void shouldFindSnapshotsForComparison() {
        // When
        List<LocalDate> comparisonDates = List.of(LocalDate.of(2024, 1, 15), LocalDate.of(2024, 1, 16));
        List<DashboardSnapshot> comparisonSnapshots = dashboardSnapshotRepository.findSnapshotsForComparison(123L, "daily", comparisonDates);

        // Then
        assertEquals(2, comparisonSnapshots.size());
        assertTrue(comparisonSnapshots.stream().anyMatch(s -> s.getSnapshotDate().equals(LocalDate.of(2024, 1, 15))));
        assertTrue(comparisonSnapshots.stream().anyMatch(s -> s.getSnapshotDate().equals(LocalDate.of(2024, 1, 16))));
    }

    @Test
    void shouldFindBestPerformingDays() {
        // When
        List<DashboardSnapshot> bestDays = dashboardSnapshotRepository.findBestPerformingDays(
            123L, LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 17), PageRequest.of(0, 5));

        // Then
        assertEquals(2, bestDays.size());
        // Should be ordered by grossSales descending
        assertEquals(BigDecimal.valueOf(12000), bestDays.get(0).getGrossSales()); // testSnapshot2
        assertEquals(BigDecimal.valueOf(10000), bestDays.get(1).getGrossSales()); // testSnapshot1
    }

    @Test
    void shouldFindWorstPerformingDays() {
        // When
        List<DashboardSnapshot> worstDays = dashboardSnapshotRepository.findWorstPerformingDays(
            123L, LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 17), PageRequest.of(0, 5));

        // Then
        assertEquals(2, worstDays.size());
        // Should be ordered by grossSales ascending
        assertEquals(BigDecimal.valueOf(10000), worstDays.get(0).getGrossSales()); // testSnapshot1
        assertEquals(BigDecimal.valueOf(12000), worstDays.get(1).getGrossSales()); // testSnapshot2
    }

    @Test
    void shouldFindBusiestDays() {
        // When
        List<DashboardSnapshot> busiestDays = dashboardSnapshotRepository.findBusiestDays(
            123L, LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 17), PageRequest.of(0, 5));

        // Then
        assertEquals(2, busiestDays.size());
        // Should be ordered by bills descending
        assertEquals(60, busiestDays.get(0).getBills()); // testSnapshot2
        assertEquals(50, busiestDays.get(1).getBills()); // testSnapshot1
    }

    @Test
    void shouldFindSnapshotsForAggregation() {
        // When
        List<DashboardSnapshot> aggregationSnapshots = dashboardSnapshotRepository.findSnapshotsForAggregation(
            123L, "daily", LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 17));

        // Then
        assertEquals(2, aggregationSnapshots.size());
        assertTrue(aggregationSnapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot1.getId())));
        assertTrue(aggregationSnapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot2.getId())));
    }

    @Test
    void shouldFindAllBranchSnapshotsForDate() {
        // When
        List<DashboardSnapshot> branchSnapshots = dashboardSnapshotRepository.findAllBranchSnapshotsForDate(123L, "daily", LocalDate.of(2024, 1, 15));

        // Then
        assertEquals(1, branchSnapshots.size());
        assertEquals(testSnapshot1.getId(), branchSnapshots.get(0).getId());
        assertEquals(201L, branchSnapshots.get(0).getBranchId());
    }

    @Test
    void shouldCheckExistsByIdAndBizId() {
        // When
        boolean exists = dashboardSnapshotRepository.existsByIdAndBizId(testSnapshot1.getId(), 123L);
        boolean notExists = dashboardSnapshotRepository.existsByIdAndBizId(testSnapshot1.getId(), 456L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndSnapshotDateAndPeriod() {
        // When
        boolean exists = dashboardSnapshotRepository.existsByBizIdAndSnapshotDateAndPeriod(123L, LocalDate.of(2024, 1, 15), "daily");
        boolean notExists = dashboardSnapshotRepository.existsByBizIdAndSnapshotDateAndPeriod(123L, LocalDate.of(2024, 1, 17), "daily");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndBranchIdAndSnapshotDateAndPeriod() {
        // When
        boolean exists = dashboardSnapshotRepository.existsByBizIdAndBranchIdAndSnapshotDateAndPeriod(123L, 201L, LocalDate.of(2024, 1, 15), "daily");
        boolean notExists = dashboardSnapshotRepository.existsByBizIdAndBranchIdAndSnapshotDateAndPeriod(123L, 999L, LocalDate.of(2024, 1, 15), "daily");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCountByBizIdAndPeriod() {
        // When
        long dailyCount = dashboardSnapshotRepository.countByBizIdAndPeriod(123L, "daily");
        long weeklyCount = dashboardSnapshotRepository.countByBizIdAndPeriod(123L, "weekly");

        // Then
        assertEquals(2L, dailyCount);
        assertEquals(0L, weeklyCount);
    }

    @Test
    void shouldCountByBizIdAndPeriodAndSnapshotDateBetween() {
        // When
        long count = dashboardSnapshotRepository.countByBizIdAndPeriodAndSnapshotDateBetween(
            123L, "daily", LocalDate.of(2024, 1, 14), LocalDate.of(2024, 1, 16));

        // Then
        assertEquals(2L, count);
    }

    @Test
    void shouldFindWithPagination() {
        // When
        Page<DashboardSnapshot> page = dashboardSnapshotRepository.findByBizIdOrderBySnapshotDateDesc(123L, PageRequest.of(0, 1));

        // Then
        assertEquals(2, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertEquals(1, page.getContent().size());
    }

    @Test
    void shouldFindLatestSnapshotsByPeriod() {
        // When
        List<DashboardSnapshot> latestSnapshots = dashboardSnapshotRepository.findLatestSnapshotsByPeriod(123L, "daily", PageRequest.of(0, 5));

        // Then
        assertEquals(2, latestSnapshots.size());
        // Should be ordered by date descending
        assertEquals(LocalDate.of(2024, 1, 16), latestSnapshots.get(0).getSnapshotDate());
        assertEquals(LocalDate.of(2024, 1, 15), latestSnapshots.get(1).getSnapshotDate());
    }

    @Test
    void shouldFindRecentSnapshots() {
        // When
        List<DashboardSnapshot> recentSnapshots = dashboardSnapshotRepository.findRecentSnapshots(123L, PageRequest.of(0, 5));

        // Then
        assertEquals(2, recentSnapshots.size());
        assertTrue(recentSnapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot1.getId())));
        assertTrue(recentSnapshots.stream().anyMatch(s -> s.getId().equals(testSnapshot2.getId())));
    }
}