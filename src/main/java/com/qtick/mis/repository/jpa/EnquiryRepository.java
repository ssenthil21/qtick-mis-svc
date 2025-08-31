package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.Enquiry;
import com.qtick.mis.entity.EnquiryStage;
import com.qtick.mis.entity.EnquiryStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Enquiry entity with custom query methods for pipeline filtering.
 * Provides methods for multi-criteria search, pipeline analytics, and tenant-aware operations.
 */
@Repository
public interface EnquiryRepository extends JpaRepository<Enquiry, Long> {

    // Basic tenant-aware queries
    List<Enquiry> findByBizIdOrderByCreatedOnDesc(Long bizId);

    Optional<Enquiry> findByIdAndBizId(Long id, Long bizId);

    Page<Enquiry> findByBizIdOrderByCreatedOnDesc(Long bizId, Pageable pageable);

    // Stage and status filtering
    List<Enquiry> findByBizIdAndStageOrderByLastTouchDateDesc(Long bizId, EnquiryStage stage);

    List<Enquiry> findByBizIdAndStatusOrderByCreatedOnDesc(Long bizId, EnquiryStatus status);

    List<Enquiry> findByBizIdAndStageAndStatusOrderByLastTouchDateDesc(Long bizId, EnquiryStage stage, EnquiryStatus status);

    // Assignee filtering
    List<Enquiry> findByBizIdAndAssigneeIdOrderByLastTouchDateDesc(Long bizId, Long assigneeId);

    Page<Enquiry> findByBizIdAndAssigneeIdOrderByLastTouchDateDesc(Long bizId, Long assigneeId, Pageable pageable);

    // Branch filtering
    List<Enquiry> findByBizIdAndBranchIdOrderByCreatedOnDesc(Long bizId, Long branchId);

    Page<Enquiry> findByBizIdAndBranchIdOrderByCreatedOnDesc(Long bizId, Long branchId, Pageable pageable);

    // Source and channel filtering
    List<Enquiry> findByBizIdAndSourceOrderByCreatedOnDesc(Long bizId, String source);

    List<Enquiry> findByBizIdAndChannelOrderByCreatedOnDesc(Long bizId, String channel);

    // Date range queries
    List<Enquiry> findByBizIdAndCreatedOnBetweenOrderByCreatedOnDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    List<Enquiry> findByBizIdAndLastTouchDateBetweenOrderByLastTouchDateDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // SLA and follow-up queries
    List<Enquiry> findByBizIdAndReContactOnBeforeAndStatusOrderByReContactOnAsc(Long bizId, LocalDateTime date, EnquiryStatus status);

    @Query("SELECT e FROM Enquiry e WHERE e.bizId = :bizId AND e.reContactOn <= :date AND e.status = 'ACTIVE' ORDER BY e.reContactOn ASC")
    List<Enquiry> findOverdueEnquiries(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT e FROM Enquiry e WHERE e.bizId = :bizId AND DATE(e.reContactOn) = DATE(:date) AND e.status = 'ACTIVE' ORDER BY e.reContactOn ASC")
    List<Enquiry> findEnquiriesDueToday(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT e FROM Enquiry e WHERE e.bizId = :bizId AND e.reContactOn > :date AND e.status = 'ACTIVE' ORDER BY e.reContactOn ASC")
    List<Enquiry> findUpcomingEnquiries(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    // Multi-criteria search query
    @Query("SELECT e FROM Enquiry e WHERE e.bizId = :bizId " +
           "AND (:stage IS NULL OR e.stage = :stage) " +
           "AND (:status IS NULL OR e.status = :status) " +
           "AND (:assigneeId IS NULL OR e.assigneeId = :assigneeId) " +
           "AND (:branchId IS NULL OR e.branchId = :branchId) " +
           "AND (:source IS NULL OR e.source = :source) " +
           "AND (:channel IS NULL OR e.channel = :channel) " +
           "AND (:srvcEnq IS NULL OR e.srvcEnq = :srvcEnq) " +
           "AND (:searchTerm IS NULL OR LOWER(e.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "     OR LOWER(e.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "     OR LOWER(e.email) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY e.lastTouchDate DESC")
    Page<Enquiry> findEnquiriesWithFilters(
        @Param("bizId") Long bizId,
        @Param("stage") EnquiryStage stage,
        @Param("status") EnquiryStatus status,
        @Param("assigneeId") Long assigneeId,
        @Param("branchId") Long branchId,
        @Param("source") String source,
        @Param("channel") String channel,
        @Param("srvcEnq") Long srvcEnq,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );

    // Pipeline analytics queries
    @Query("SELECT e.stage, COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.status = 'ACTIVE' GROUP BY e.stage")
    List<Object[]> countEnquiriesByStage(@Param("bizId") Long bizId);

    @Query("SELECT e.source, COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.createdOn >= :startDate GROUP BY e.source")
    List<Object[]> countEnquiriesBySource(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT e.channel, COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.createdOn >= :startDate GROUP BY e.channel")
    List<Object[]> countEnquiriesByChannel(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT e.assigneeId, COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.assigneeId IS NOT NULL AND e.status = 'ACTIVE' GROUP BY e.assigneeId")
    List<Object[]> countEnquiriesByAssignee(@Param("bizId") Long bizId);

    // Conversion and performance metrics
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.stage = 'CLOSED_WON' AND e.closureDate >= :startDate")
    Long countConvertedEnquiries(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.stage = 'CLOSED_LOST' AND e.closureDate >= :startDate")
    Long countLostEnquiries(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.createdOn >= :startDate")
    Long countNewEnquiries(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.lastTouchDate < :cutoffDate AND e.status = 'ACTIVE'")
    Long countMissedEnquiries(@Param("bizId") Long bizId, @Param("cutoffDate") LocalDateTime cutoffDate);

    // Branch-specific queries
    @Query("SELECT e.stage, COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.branchId = :branchId AND e.status = 'ACTIVE' GROUP BY e.stage")
    List<Object[]> countEnquiriesByStageAndBranch(@Param("bizId") Long bizId, @Param("branchId") Long branchId);

    // Customer-related queries
    List<Enquiry> findByBizIdAndCustIdOrderByCreatedOnDesc(Long bizId, Long custId);

    @Query("SELECT COUNT(DISTINCT e.custId) FROM Enquiry e WHERE e.bizId = :bizId AND e.custId IS NOT NULL AND e.createdOn >= :startDate")
    Long countUniqueCustomersWithEnquiries(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    // Service-specific queries
    @Query("SELECT e.srvcEnq, COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.srvcEnq IS NOT NULL AND e.createdOn >= :startDate GROUP BY e.srvcEnq")
    List<Object[]> countEnquiriesByService(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    // Time-based analytics
    @Query("SELECT DATE(e.createdOn), COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.createdOn >= :startDate GROUP BY DATE(e.createdOn) ORDER BY DATE(e.createdOn)")
    List<Object[]> countEnquiriesByDate(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT HOUR(e.createdOn), COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.createdOn >= :startDate GROUP BY HOUR(e.createdOn) ORDER BY HOUR(e.createdOn)")
    List<Object[]> countEnquiriesByHour(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    // Bulk operations
    @Query("UPDATE Enquiry e SET e.assigneeId = :newAssigneeId WHERE e.bizId = :bizId AND e.assigneeId = :oldAssigneeId AND e.status = 'ACTIVE'")
    int reassignEnquiries(@Param("bizId") Long bizId, @Param("oldAssigneeId") Long oldAssigneeId, @Param("newAssigneeId") Long newAssigneeId);

    // Exists queries for validation
    boolean existsByIdAndBizId(Long id, Long bizId);

    boolean existsByBizIdAndPhoneAndStatus(Long bizId, String phone, EnquiryStatus status);

    boolean existsByBizIdAndEmailAndStatus(Long bizId, String email, EnquiryStatus status);
    
    // Dashboard service methods
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.stage = 'NEW' AND e.createdOn BETWEEN :startDate AND :endDate")
    Integer countNewEnquiriesByBizIdAndDateRange(@Param("bizId") Long bizId, 
                                               @Param("startDate") LocalDateTime startDate, 
                                               @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.createdOn BETWEEN :startDate AND :endDate")
    Integer countByBizIdAndDateRange(@Param("bizId") Long bizId, 
                                   @Param("startDate") LocalDateTime startDate, 
                                   @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(e) FROM Enquiry e WHERE e.bizId = :bizId AND e.status = 'MISSED' AND e.createdOn BETWEEN :startDate AND :endDate")
    Integer countMissedEnquiriesByBizIdAndDateRange(@Param("bizId") Long bizId, 
                                                  @Param("startDate") LocalDateTime startDate, 
                                                  @Param("endDate") LocalDateTime endDate);
}