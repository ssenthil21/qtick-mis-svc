package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.Client;
import com.qtick.mis.entity.ClientStatus;
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
 * Repository interface for Client entity with search and filtering capabilities.
 * Provides methods for client management, search, and analytics operations.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    // Basic tenant-aware queries
    List<Client> findByBizIdOrderByCreatedOnDesc(Long bizId);

    Optional<Client> findByCustIdAndBizId(Long custId, Long bizId);

    Page<Client> findByBizIdOrderByCreatedOnDesc(Long bizId, Pageable pageable);

    // Status filtering
    List<Client> findByBizIdAndStatusOrderByCreatedOnDesc(Long bizId, ClientStatus status);

    Page<Client> findByBizIdAndStatusOrderByCreatedOnDesc(Long bizId, ClientStatus status, Pageable pageable);

    // Contact information queries
    Optional<Client> findByBizIdAndPhone(Long bizId, String phone);

    Optional<Client> findByBizIdAndEmail(Long bizId, String email);

    List<Client> findByBizIdAndPhoneContainingOrderByCreatedOnDesc(Long bizId, String phonePattern);

    List<Client> findByBizIdAndEmailContainingOrderByCreatedOnDesc(Long bizId, String emailPattern);

    // Name-based search
    List<Client> findByBizIdAndNameContainingIgnoreCaseOrderByName(Long bizId, String namePattern);

    Page<Client> findByBizIdAndNameContainingIgnoreCaseOrderByName(Long bizId, String namePattern, Pageable pageable);

    // Business client queries
    List<Client> findByBizIdAndBusinessNameIsNotNullOrderByBusinessName(Long bizId);

    List<Client> findByBizIdAndBusinessTypeOrderByBusinessName(Long bizId, String businessType);

    Page<Client> findByBizIdAndBusinessTypeOrderByBusinessName(Long bizId, String businessType, Pageable pageable);

    // Loyalty and engagement queries
    List<Client> findByBizIdAndLoyaltyPointsGreaterThanOrderByLoyaltyPointsDesc(Long bizId, Integer minPoints);

    List<Client> findByBizIdAndLastVisitDateAfterOrderByLastVisitDateDesc(Long bizId, LocalDateTime afterDate);

    List<Client> findByBizIdAndLastVisitDateBeforeOrderByLastVisitDateAsc(Long bizId, LocalDateTime beforeDate);

    // Tags-based filtering
    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId AND c.tags LIKE %:tag% ORDER BY c.name")
    List<Client> findByBizIdAndTagsContaining(@Param("bizId") Long bizId, @Param("tag") String tag);

    // Multi-criteria search query
    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId " +
           "AND (:status IS NULL OR c.status = :status) " +
           "AND (:businessType IS NULL OR c.businessType = :businessType) " +
           "AND (:minPoints IS NULL OR c.loyaltyPoints >= :minPoints) " +
           "AND (:maxPoints IS NULL OR c.loyaltyPoints <= :maxPoints) " +
           "AND (:lastVisitAfter IS NULL OR c.lastVisitDate >= :lastVisitAfter) " +
           "AND (:lastVisitBefore IS NULL OR c.lastVisitDate <= :lastVisitBefore) " +
           "AND (:searchTerm IS NULL OR " +
           "     LOWER(c.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(c.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(c.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(c.businessName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY c.name")
    Page<Client> findClientsWithFilters(
        @Param("bizId") Long bizId,
        @Param("status") ClientStatus status,
        @Param("businessType") String businessType,
        @Param("minPoints") Integer minPoints,
        @Param("maxPoints") Integer maxPoints,
        @Param("lastVisitAfter") LocalDateTime lastVisitAfter,
        @Param("lastVisitBefore") LocalDateTime lastVisitBefore,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );

    // Analytics queries
    @Query("SELECT COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.createdOn >= :startDate")
    Long countNewClients(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.status = 'ACTIVE'")
    Long countActiveClients(@Param("bizId") Long bizId);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.lastVisitDate >= :startDate")
    Long countReturningClients(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT c.businessType, COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.businessType IS NOT NULL GROUP BY c.businessType")
    List<Object[]> countClientsByBusinessType(@Param("bizId") Long bizId);

    @Query("SELECT c.preferredChannel, COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.preferredChannel IS NOT NULL GROUP BY c.preferredChannel")
    List<Object[]> countClientsByPreferredChannel(@Param("bizId") Long bizId);

    // Loyalty analytics
    @Query("SELECT AVG(c.loyaltyPoints) FROM Client c WHERE c.bizId = :bizId AND c.status = 'ACTIVE'")
    Double getAverageLoyaltyPoints(@Param("bizId") Long bizId);

    @Query("SELECT SUM(c.loyaltyPoints) FROM Client c WHERE c.bizId = :bizId AND c.status = 'ACTIVE'")
    Long getTotalLoyaltyPoints(@Param("bizId") Long bizId);

    @Query("SELECT COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.loyaltyPoints BETWEEN :minPoints AND :maxPoints")
    Long countClientsByPointsRange(@Param("bizId") Long bizId, @Param("minPoints") Integer minPoints, @Param("maxPoints") Integer maxPoints);

    // Time-based analytics
    @Query("SELECT DATE(c.createdOn), COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.createdOn >= :startDate GROUP BY DATE(c.createdOn) ORDER BY DATE(c.createdOn)")
    List<Object[]> countClientsByDate(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    @Query("SELECT YEAR(c.createdOn), MONTH(c.createdOn), COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.createdOn >= :startDate GROUP BY YEAR(c.createdOn), MONTH(c.createdOn) ORDER BY YEAR(c.createdOn), MONTH(c.createdOn)")
    List<Object[]> countClientsByMonth(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate);

    // Geographic analytics
    @Query("SELECT c.city, COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.city IS NOT NULL GROUP BY c.city ORDER BY COUNT(c) DESC")
    List<Object[]> countClientsByCity(@Param("bizId") Long bizId);

    @Query("SELECT c.state, COUNT(c) FROM Client c WHERE c.bizId = :bizId AND c.state IS NOT NULL GROUP BY c.state ORDER BY COUNT(c) DESC")
    List<Object[]> countClientsByState(@Param("bizId") Long bizId);

    // Client engagement queries
    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId AND c.lastVisitDate IS NULL ORDER BY c.createdOn DESC")
    List<Client> findClientsWithNoVisits(@Param("bizId") Long bizId);

    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId AND c.lastVisitDate < :cutoffDate ORDER BY c.lastVisitDate ASC")
    List<Client> findInactiveClients(@Param("bizId") Long bizId, @Param("cutoffDate") LocalDateTime cutoffDate);

    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId AND c.loyaltyPoints > 0 ORDER BY c.loyaltyPoints DESC")
    List<Client> findClientsWithLoyaltyPoints(@Param("bizId") Long bizId);

    // Top clients queries
    @Query("SELECT c FROM Client c LEFT JOIN c.bills b WHERE c.bizId = :bizId GROUP BY c ORDER BY SUM(COALESCE(b.netAmount, 0)) DESC")
    List<Client> findTopClientsByRevenue(@Param("bizId") Long bizId, Pageable pageable);

    @Query("SELECT c FROM Client c LEFT JOIN c.bills b WHERE c.bizId = :bizId GROUP BY c ORDER BY COUNT(b) DESC")
    List<Client> findTopClientsByBillCount(@Param("bizId") Long bizId, Pageable pageable);

    @Query("SELECT c FROM Client c LEFT JOIN c.appointments a WHERE c.bizId = :bizId GROUP BY c ORDER BY COUNT(a) DESC")
    List<Client> findTopClientsByAppointmentCount(@Param("bizId") Long bizId, Pageable pageable);

    // Duplicate detection
    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId AND (c.phone = :phone OR c.email = :email) AND c.custId != :excludeId")
    List<Client> findPotentialDuplicates(@Param("bizId") Long bizId, @Param("phone") String phone, @Param("email") String email, @Param("excludeId") Long excludeId);

    // Validation queries
    boolean existsByCustIdAndBizId(Long custId, Long bizId);

    boolean existsByBizIdAndPhone(Long bizId, String phone);

    boolean existsByBizIdAndEmail(Long bizId, String email);

    boolean existsByBizIdAndPhoneAndCustIdNot(Long bizId, String phone, Long custId);

    boolean existsByBizIdAndEmailAndCustIdNot(Long bizId, String email, Long custId);

    // Bulk operations
    @Query("UPDATE Client c SET c.status = :newStatus WHERE c.bizId = :bizId AND c.lastVisitDate < :cutoffDate AND c.status = 'ACTIVE'")
    int markInactiveClients(@Param("bizId") Long bizId, @Param("cutoffDate") LocalDateTime cutoffDate, @Param("newStatus") ClientStatus newStatus);

    // Business details queries for dashboard
    @Query("SELECT c FROM Client c WHERE c.bizId = :bizId AND c.businessType = :businessType ORDER BY c.lastVisitDate DESC")
    List<Client> findBusinessClientsByType(@Param("bizId") Long bizId, @Param("businessType") String businessType);

    @Query("SELECT c FROM Client c LEFT JOIN c.bills b WHERE c.bizId = :bizId AND c.businessType = :businessType " +
           "AND DATE(b.billDate) = DATE(:date) GROUP BY c ORDER BY c.businessName")
    List<Client> findBusinessClientsWithBillsOnDate(@Param("bizId") Long bizId, @Param("businessType") String businessType, @Param("date") LocalDateTime date);

    @Query("SELECT c FROM Client c LEFT JOIN c.appointments a WHERE c.bizId = :bizId AND c.businessType = :businessType " +
           "AND DATE(a.appointmentDate) = DATE(:date) GROUP BY c ORDER BY c.businessName")
    List<Client> findBusinessClientsWithAppointmentsOnDate(@Param("bizId") Long bizId, @Param("businessType") String businessType, @Param("date") LocalDateTime date);
}