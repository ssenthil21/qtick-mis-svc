package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.Bill;
import com.qtick.mis.entity.BillStatus;
import com.qtick.mis.entity.PaymentMode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Bill entity with date range and client filtering.
 * Provides methods for billing analytics, payment tracking, and financial reporting.
 */
@Repository
public interface BillRepository extends JpaRepository<Bill, Long> {

    // Basic tenant-aware queries
    List<Bill> findByBizIdOrderByBillDateDesc(Long bizId);

    Optional<Bill> findByIdAndBizId(Long id, Long bizId);

    Page<Bill> findByBizIdOrderByBillDateDesc(Long bizId, Pageable pageable);

    // Bill number queries
    Optional<Bill> findByBizIdAndBillNumber(Long bizId, String billNumber);

    boolean existsByBizIdAndBillNumber(Long bizId, String billNumber);

    // Client-specific queries
    List<Bill> findByBizIdAndClientCustIdOrderByBillDateDesc(Long bizId, Long custId);

    Page<Bill> findByBizIdAndClientCustIdOrderByBillDateDesc(Long bizId, Long custId, Pageable pageable);

    // Status filtering
    List<Bill> findByBizIdAndStatusOrderByBillDateDesc(Long bizId, BillStatus status);

    Page<Bill> findByBizIdAndStatusOrderByBillDateDesc(Long bizId, BillStatus status, Pageable pageable);

    // Branch filtering
    List<Bill> findByBizIdAndBranchIdOrderByBillDateDesc(Long bizId, Long branchId);

    Page<Bill> findByBizIdAndBranchIdOrderByBillDateDesc(Long bizId, Long branchId, Pageable pageable);

    // Date range queries
    List<Bill> findByBizIdAndBillDateBetweenOrderByBillDateDesc(Long bizId, LocalDate startDate, LocalDate endDate);

    Page<Bill> findByBizIdAndBillDateBetweenOrderByBillDateDesc(Long bizId, LocalDate startDate, LocalDate endDate, Pageable pageable);

    List<Bill> findByBizIdAndCreatedOnBetweenOrderByCreatedOnDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // Payment-related queries
    List<Bill> findByBizIdAndPaymentModeOrderByBillDateDesc(Long bizId, PaymentMode paymentMode);

    List<Bill> findByBizIdAndPaymentDateBetweenOrderByPaymentDateDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // Amount-based queries
    List<Bill> findByBizIdAndNetAmountGreaterThanOrderByNetAmountDesc(Long bizId, BigDecimal minAmount);

    List<Bill> findByBizIdAndNetAmountBetweenOrderByNetAmountDesc(Long bizId, BigDecimal minAmount, BigDecimal maxAmount);

    List<Bill> findByBizIdAndBalanceAmountGreaterThanOrderByBillDateDesc(Long bizId, BigDecimal minBalance);

    // Staff-related queries
    List<Bill> findByBizIdAndStaffIdOrderByBillDateDesc(Long bizId, Long staffId);

    Page<Bill> findByBizIdAndStaffIdOrderByBillDateDesc(Long bizId, Long staffId, Pageable pageable);

    // Multi-criteria search query
    @Query("SELECT b FROM Bill b WHERE b.bizId = :bizId " +
           "AND (:clientId IS NULL OR b.client.custId = :clientId) " +
           "AND (:status IS NULL OR b.status = :status) " +
           "AND (:branchId IS NULL OR b.branchId = :branchId) " +
           "AND (:staffId IS NULL OR b.staffId = :staffId) " +
           "AND (:paymentMode IS NULL OR b.paymentMode = :paymentMode) " +
           "AND (:startDate IS NULL OR b.billDate >= :startDate) " +
           "AND (:endDate IS NULL OR b.billDate <= :endDate) " +
           "AND (:minAmount IS NULL OR b.netAmount >= :minAmount) " +
           "AND (:maxAmount IS NULL OR b.netAmount <= :maxAmount) " +
           "AND (:searchTerm IS NULL OR " +
           "     LOWER(b.billNumber) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(b.client.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(b.client.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY b.billDate DESC")
    Page<Bill> findBillsWithFilters(
        @Param("bizId") Long bizId,
        @Param("clientId") Long clientId,
        @Param("status") BillStatus status,
        @Param("branchId") Long branchId,
        @Param("staffId") Long staffId,
        @Param("paymentMode") PaymentMode paymentMode,
        @Param("startDate") LocalDate startDate,
        @Param("endDate") LocalDate endDate,
        @Param("minAmount") BigDecimal minAmount,
        @Param("maxAmount") BigDecimal maxAmount,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );

    // Financial analytics queries
    @Query("SELECT SUM(b.grossAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalGrossSales(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalNetSales(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT COUNT(b) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    Long getTotalBillCount(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT AVG(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal getAverageBillAmount(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(b.discountAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalDiscounts(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(b.taxAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalTax(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(b.paidAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal getTotalPaidAmount(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT SUM(b.balanceAmount) FROM Bill b WHERE b.bizId = :bizId AND b.status IN ('PENDING', 'PARTIAL')")
    BigDecimal getTotalOutstandingAmount(@Param("bizId") Long bizId);

    // Status-based analytics
    @Query("SELECT b.status, COUNT(b) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate GROUP BY b.status")
    List<Object[]> countBillsByStatus(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b.paymentMode, COUNT(b), SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate AND b.paymentMode IS NOT NULL GROUP BY b.paymentMode")
    List<Object[]> getPaymentModeAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Time-based analytics
    @Query("SELECT b.billDate, COUNT(b), SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate GROUP BY b.billDate ORDER BY b.billDate")
    List<Object[]> getDailySalesAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT YEAR(b.billDate), MONTH(b.billDate), COUNT(b), SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate >= :startDate GROUP BY YEAR(b.billDate), MONTH(b.billDate) ORDER BY YEAR(b.billDate), MONTH(b.billDate)")
    List<Object[]> getMonthlySalesAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate);

    @Query("SELECT HOUR(b.createdOn), COUNT(b), SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate GROUP BY HOUR(b.createdOn) ORDER BY HOUR(b.createdOn)")
    List<Object[]> getHourlySalesAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Staff performance analytics
    @Query("SELECT b.staffId, b.staffName, COUNT(b), SUM(b.netAmount), AVG(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.staffId IS NOT NULL AND b.billDate BETWEEN :startDate AND :endDate GROUP BY b.staffId, b.staffName ORDER BY SUM(b.netAmount) DESC")
    List<Object[]> getStaffPerformanceAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Branch performance analytics
    @Query("SELECT b.branchId, COUNT(b), SUM(b.netAmount), AVG(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.branchId IS NOT NULL AND b.billDate BETWEEN :startDate AND :endDate GROUP BY b.branchId ORDER BY SUM(b.netAmount) DESC")
    List<Object[]> getBranchPerformanceAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Client analytics
    @Query("SELECT COUNT(DISTINCT b.client.custId) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    Long getUniqueClientCount(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Query("SELECT b.client.custId, b.client.name, COUNT(b), SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate GROUP BY b.client.custId, b.client.name ORDER BY SUM(b.netAmount) DESC")
    List<Object[]> getTopClientsByRevenue(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    @Query("SELECT b.client.custId, b.client.name, COUNT(b), SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate GROUP BY b.client.custId, b.client.name ORDER BY COUNT(b) DESC")
    List<Object[]> getTopClientsByBillCount(@Param("bizId") Long bizId, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate, Pageable pageable);

    // Outstanding bills queries
    List<Bill> findByBizIdAndStatusInOrderByBillDateAsc(Long bizId, List<BillStatus> statuses);

    @Query("SELECT b FROM Bill b WHERE b.bizId = :bizId AND b.balanceAmount > 0 AND b.billDate < :cutoffDate ORDER BY b.billDate ASC")
    List<Bill> findOverdueBills(@Param("bizId") Long bizId, @Param("cutoffDate") LocalDate cutoffDate);

    // Recent bills
    @Query("SELECT b FROM Bill b WHERE b.bizId = :bizId ORDER BY b.createdOn DESC")
    List<Bill> findRecentBills(@Param("bizId") Long bizId, Pageable pageable);

    // Validation queries
    boolean existsByIdAndBizId(Long id, Long bizId);

    // Business details queries for dashboard
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.bizId = :bizId AND DATE(b.billDate) = DATE(:date)")
    Long countBillsOnDate(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT SUM(b.netAmount) FROM Bill b WHERE b.bizId = :bizId AND DATE(b.billDate) = DATE(:date)")
    BigDecimal getTotalRevenueOnDate(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT b FROM Bill b JOIN b.client c WHERE b.bizId = :bizId AND c.businessType = :businessType AND DATE(b.billDate) = DATE(:date) ORDER BY b.billDate DESC")
    List<Bill> findBillsByBusinessTypeOnDate(@Param("bizId") Long bizId, @Param("businessType") String businessType, @Param("date") LocalDateTime date);
    
    // Additional methods for DashboardService
    @Query("SELECT COALESCE(SUM(b.grossAmount), 0) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal sumTotalAmountByBizIdAndDateRange(@Param("bizId") Long bizId, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COALESCE(SUM(b.paidAmount), 0) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    BigDecimal sumPaidAmountByBizIdAndDateRange(@Param("bizId") Long bizId, 
                                              @Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(b) FROM Bill b WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate")
    Integer countByBizIdAndDateRange(@Param("bizId") Long bizId, 
                                   @Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT bi.serviceId, 'Service Name', SUM(bi.totalPrice), COUNT(bi) " +
           "FROM BillItem bi JOIN bi.bill b " +
           "WHERE b.bizId = :bizId AND b.billDate BETWEEN :startDate AND :endDate " +
           "GROUP BY bi.serviceId " +
           "ORDER BY SUM(bi.totalPrice) DESC")
    List<Object[]> findTopServicesByRevenue(@Param("bizId") Long bizId, 
                                          @Param("startDate") LocalDateTime startDate, 
                                          @Param("endDate") LocalDateTime endDate, 
                                          @Param("limit") Integer limit);
    
    @Query("SELECT 'Service Name', " +
           "COUNT(CASE WHEN 'APPOINTMENT' = 'APPOINTMENT' THEN 1 END), " +
           "COUNT(CASE WHEN 'WALK_IN' = 'WALK_IN' THEN 1 END), " +
           "COUNT(bi), SUM(bi.totalPrice) " +
           "FROM BillItem bi " +
           "JOIN bi.bill b " +
           "WHERE b.bizId = :bizId AND b.client.custId = :custId AND b.billDate BETWEEN :startDate AND :endDate " +
           "GROUP BY bi.serviceId")
    List<Object[]> findServiceBreakdownByClient(@Param("bizId") Long bizId, 
                                              @Param("custId") Long custId,
                                              @Param("startDate") LocalDateTime startDate, 
                                              @Param("endDate") LocalDateTime endDate);
}