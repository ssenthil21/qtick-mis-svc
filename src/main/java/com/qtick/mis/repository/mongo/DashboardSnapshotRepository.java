package com.qtick.mis.repository.mongo;

import com.qtick.mis.document.DashboardSnapshot;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for DashboardSnapshot document with date-based queries.
 * Provides methods for dashboard metrics retrieval, trend analysis, and performance optimization.
 */
@Repository
public interface DashboardSnapshotRepository extends MongoRepository<DashboardSnapshot, String> {

    // Basic tenant-aware queries
    List<DashboardSnapshot> findByBizIdOrderBySnapshotDateDesc(Long bizId);

    Page<DashboardSnapshot> findByBizIdOrderBySnapshotDateDesc(Long bizId, Pageable pageable);

    Optional<DashboardSnapshot> findByIdAndBizId(String id, Long bizId);

    // Period-specific queries
    List<DashboardSnapshot> findByBizIdAndPeriodOrderBySnapshotDateDesc(Long bizId, String period);

    Page<DashboardSnapshot> findByBizIdAndPeriodOrderBySnapshotDateDesc(Long bizId, String period, Pageable pageable);

    // Date-specific queries
    Optional<DashboardSnapshot> findByBizIdAndSnapshotDateAndPeriod(Long bizId, LocalDate snapshotDate, String period);

    List<DashboardSnapshot> findByBizIdAndSnapshotDateBetweenOrderBySnapshotDateDesc(Long bizId, LocalDate startDate, LocalDate endDate);

    List<DashboardSnapshot> findByBizIdAndPeriodAndSnapshotDateBetweenOrderBySnapshotDateDesc(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    // Branch-specific queries
    List<DashboardSnapshot> findByBizIdAndBranchIdOrderBySnapshotDateDesc(Long bizId, Long branchId);

    Optional<DashboardSnapshot> findByBizIdAndBranchIdAndSnapshotDateAndPeriod(Long bizId, Long branchId, LocalDate snapshotDate, String period);

    List<DashboardSnapshot> findByBizIdAndBranchIdAndPeriodAndSnapshotDateBetweenOrderBySnapshotDateDesc(Long bizId, Long branchId, String period, LocalDate startDate, LocalDate endDate);

    // Latest snapshots
    @Query(value = "{ 'bizId': ?0, 'period': ?1 }", sort = "{ 'snapshotDate': -1 }")
    List<DashboardSnapshot> findLatestSnapshotsByPeriod(Long bizId, String period, Pageable pageable);

    @Query(value = "{ 'bizId': ?0, 'branchId': ?1, 'period': ?2 }", sort = "{ 'snapshotDate': -1 }")
    List<DashboardSnapshot> findLatestSnapshotsByBranchAndPeriod(Long bizId, Long branchId, String period, Pageable pageable);

    // Current period snapshots
    @Query("{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': ?1 }")
    Optional<DashboardSnapshot> findDailySnapshot(Long bizId, LocalDate date);

    @Query("{ 'bizId': ?0, 'branchId': ?1, 'period': 'daily', 'snapshotDate': ?2 }")
    Optional<DashboardSnapshot> findDailySnapshotForBranch(Long bizId, Long branchId, LocalDate date);

    // Weekly snapshots (assuming snapshot date is the week ending date)
    @Query("{ 'bizId': ?0, 'period': 'weekly', 'snapshotDate': { $gte: ?1, $lte: ?2 } }")
    List<DashboardSnapshot> findWeeklySnapshotsInRange(Long bizId, LocalDate startDate, LocalDate endDate);

    // Monthly snapshots (assuming snapshot date is the month ending date)
    @Query("{ 'bizId': ?0, 'period': 'monthly', 'snapshotDate': { $gte: ?1, $lte: ?2 } }")
    List<DashboardSnapshot> findMonthlySnapshotsInRange(Long bizId, LocalDate startDate, LocalDate endDate);

    // Trend analysis queries
    @Query(value = "{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2, $lte: ?3 } }", 
           sort = "{ 'snapshotDate': 1 }")
    List<DashboardSnapshot> findSnapshotsForTrendAnalysis(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    @Query(value = "{ 'bizId': ?0, 'branchId': ?1, 'period': ?2, 'snapshotDate': { $gte: ?3, $lte: ?4 } }", 
           sort = "{ 'snapshotDate': 1 }")
    List<DashboardSnapshot> findBranchSnapshotsForTrendAnalysis(Long bizId, Long branchId, String period, LocalDate startDate, LocalDate endDate);

    // Comparison queries
    @Query("{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $in: ?2 } }")
    List<DashboardSnapshot> findSnapshotsForComparison(Long bizId, String period, List<LocalDate> dates);

    // Performance queries for dashboard highlights
    @Query(value = "{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': { $gte: ?1, $lte: ?2 } }", 
           sort = "{ 'grossSales': -1 }")
    List<DashboardSnapshot> findBestPerformingDays(Long bizId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query(value = "{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': { $gte: ?1, $lte: ?2 } }", 
           sort = "{ 'grossSales': 1 }")
    List<DashboardSnapshot> findWorstPerformingDays(Long bizId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    @Query(value = "{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': { $gte: ?1, $lte: ?2 } }", 
           sort = "{ 'bills': -1 }")
    List<DashboardSnapshot> findBusiestDays(Long bizId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Aggregation queries for KPIs
    @Query("{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2, $lte: ?3 } }")
    List<DashboardSnapshot> findSnapshotsForAggregation(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    // Service and staff analytics from snapshots
    @Query(value = "{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2, $lte: ?3 }, 'topServices': { $exists: true, $ne: [] } }", 
           fields = "{ 'topServices': 1, 'snapshotDate': 1 }")
    List<DashboardSnapshot> findSnapshotsWithServiceMetrics(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    @Query(value = "{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2, $lte: ?3 }, 'topStaff': { $exists: true, $ne: [] } }", 
           fields = "{ 'topStaff': 1, 'snapshotDate': 1 }")
    List<DashboardSnapshot> findSnapshotsWithStaffMetrics(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    // Anomaly detection queries
    @Query("{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': { $gte: ?1, $lte: ?2 }, 'grossSales': { $gte: ?3 } }")
    List<DashboardSnapshot> findSalesSpikes(Long bizId, LocalDate startDate, LocalDate endDate, Double threshold);

    @Query("{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': { $gte: ?1, $lte: ?2 }, 'grossSales': { $lte: ?3 } }")
    List<DashboardSnapshot> findSalesDips(Long bizId, LocalDate startDate, LocalDate endDate, Double threshold);

    @Query("{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': { $gte: ?1, $lte: ?2 }, 'newLeads': { $gte: ?3 } }")
    List<DashboardSnapshot> findLeadSpikes(Long bizId, LocalDate startDate, LocalDate endDate, Integer threshold);

    // Branch comparison queries
    @Query("{ 'bizId': ?0, 'period': ?1, 'snapshotDate': ?2, 'branchId': { $exists: true } }")
    List<DashboardSnapshot> findAllBranchSnapshotsForDate(Long bizId, String period, LocalDate date);

    @Query(value = "{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2, $lte: ?3 }, 'branchId': { $exists: true } }", 
           sort = "{ 'branchId': 1, 'snapshotDate': 1 }")
    List<DashboardSnapshot> findAllBranchSnapshotsInRange(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    // Cleanup and maintenance queries
    @Query("{ 'bizId': ?0, 'snapshotDate': { $lt: ?1 } }")
    List<DashboardSnapshot> findOldSnapshots(Long bizId, LocalDate cutoffDate);

    void deleteByBizIdAndSnapshotDateBefore(Long bizId, LocalDate cutoffDate);

    void deleteByBizIdAndPeriodAndSnapshotDateBefore(Long bizId, String period, LocalDate cutoffDate);

    // Validation queries
    boolean existsByIdAndBizId(String id, Long bizId);

    boolean existsByBizIdAndSnapshotDateAndPeriod(Long bizId, LocalDate snapshotDate, String period);

    boolean existsByBizIdAndBranchIdAndSnapshotDateAndPeriod(Long bizId, Long branchId, LocalDate snapshotDate, String period);

    // Count queries for pagination and analytics
    long countByBizIdAndPeriod(Long bizId, String period);

    long countByBizIdAndPeriodAndSnapshotDateBetween(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    long countByBizIdAndBranchIdAndPeriod(Long bizId, Long branchId, String period);

    // Recent snapshots for dashboard loading
    @Query(value = "{ 'bizId': ?0 }", sort = "{ 'createdAt': -1 }")
    List<DashboardSnapshot> findRecentSnapshots(Long bizId, Pageable pageable);

    @Query(value = "{ 'bizId': ?0, 'period': ?1 }", sort = "{ 'createdAt': -1 }")
    List<DashboardSnapshot> findRecentSnapshotsByPeriod(Long bizId, String period, Pageable pageable);

    // Business details queries for specific business types
    @Query("{ 'bizId': ?0, 'period': 'daily', 'snapshotDate': ?1, 'additionalMetrics.businessType': ?2 }")
    List<DashboardSnapshot> findSnapshotsByBusinessTypeOnDate(Long bizId, LocalDate date, String businessType);

    // Custom metrics queries
    @Query(value = "{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2, $lte: ?3 }, 'additionalMetrics': { $exists: true } }", 
           fields = "{ 'additionalMetrics': 1, 'snapshotDate': 1 }")
    List<DashboardSnapshot> findSnapshotsWithAdditionalMetrics(Long bizId, String period, LocalDate startDate, LocalDate endDate);

    // Time-based queries for dashboard optimization
    @Query("{ 'bizId': ?0, 'updatedAt': { $gte: ?1 } }")
    List<DashboardSnapshot> findSnapshotsUpdatedSince(Long bizId, LocalDateTime since);

    @Query(value = "{ 'bizId': ?0, 'period': ?1, 'snapshotDate': { $gte: ?2 } }", 
           sort = "{ 'snapshotDate': -1 }")
    List<DashboardSnapshot> findSnapshotsSinceDate(Long bizId, String period, LocalDate sinceDate, Pageable pageable);
}