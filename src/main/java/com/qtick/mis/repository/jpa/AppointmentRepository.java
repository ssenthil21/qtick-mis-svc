package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.Appointment;
import com.qtick.mis.entity.AppointmentStatus;
import com.qtick.mis.entity.AppointmentType;
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
 * Repository interface for Appointment entity with scheduling queries.
 * Provides methods for appointment management, scheduling analytics, and staff utilization.
 */
@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    // Basic tenant-aware queries
    List<Appointment> findByBizIdOrderByAppointmentDateDesc(Long bizId);

    Optional<Appointment> findByIdAndBizId(Long id, Long bizId);

    Page<Appointment> findByBizIdOrderByAppointmentDateDesc(Long bizId, Pageable pageable);

    // Client-specific queries
    List<Appointment> findByBizIdAndClientCustIdOrderByAppointmentDateDesc(Long bizId, Long custId);

    Page<Appointment> findByBizIdAndClientCustIdOrderByAppointmentDateDesc(Long bizId, Long custId, Pageable pageable);

    // Status filtering
    List<Appointment> findByBizIdAndStatusOrderByAppointmentDateDesc(Long bizId, AppointmentStatus status);

    Page<Appointment> findByBizIdAndStatusOrderByAppointmentDateDesc(Long bizId, AppointmentStatus status, Pageable pageable);

    // Type filtering
    List<Appointment> findByBizIdAndAppointmentTypeOrderByAppointmentDateDesc(Long bizId, AppointmentType appointmentType);

    // Staff-specific queries
    List<Appointment> findByBizIdAndStaffIdOrderByAppointmentDateDesc(Long bizId, Long staffId);

    Page<Appointment> findByBizIdAndStaffIdOrderByAppointmentDateDesc(Long bizId, Long staffId, Pageable pageable);

    List<Appointment> findByBizIdAndStaffIdAndStatusOrderByAppointmentDateDesc(Long bizId, Long staffId, AppointmentStatus status);

    // Branch filtering
    List<Appointment> findByBizIdAndBranchIdOrderByAppointmentDateDesc(Long bizId, Long branchId);

    Page<Appointment> findByBizIdAndBranchIdOrderByAppointmentDateDesc(Long bizId, Long branchId, Pageable pageable);

    // Service-specific queries
    List<Appointment> findByBizIdAndServiceIdOrderByAppointmentDateDesc(Long bizId, Long serviceId);

    // Date and time range queries
    List<Appointment> findByBizIdAndAppointmentDateBetweenOrderByAppointmentDateAsc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    Page<Appointment> findByBizIdAndAppointmentDateBetweenOrderByAppointmentDateAsc(Long bizId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Today's appointments
    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId AND DATE(a.appointmentDate) = DATE(:date) ORDER BY a.appointmentDate ASC")
    List<Appointment> findAppointmentsForDate(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId AND a.staffId = :staffId AND DATE(a.appointmentDate) = DATE(:date) ORDER BY a.appointmentDate ASC")
    List<Appointment> findAppointmentsForStaffOnDate(@Param("bizId") Long bizId, @Param("staffId") Long staffId, @Param("date") LocalDateTime date);

    // Upcoming appointments
    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate > :currentTime AND a.status IN ('SCHEDULED', 'CONFIRMED') ORDER BY a.appointmentDate ASC")
    List<Appointment> findUpcomingAppointments(@Param("bizId") Long bizId, @Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId AND a.staffId = :staffId AND a.appointmentDate > :currentTime AND a.status IN ('SCHEDULED', 'CONFIRMED') ORDER BY a.appointmentDate ASC")
    List<Appointment> findUpcomingAppointmentsForStaff(@Param("bizId") Long bizId, @Param("staffId") Long staffId, @Param("currentTime") LocalDateTime currentTime);

    // Past appointments
    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate < :currentTime ORDER BY a.appointmentDate DESC")
    List<Appointment> findPastAppointments(@Param("bizId") Long bizId, @Param("currentTime") LocalDateTime currentTime);

    // Multi-criteria search query
    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId " +
           "AND (:clientId IS NULL OR a.client.custId = :clientId) " +
           "AND (:status IS NULL OR a.status = :status) " +
           "AND (:appointmentType IS NULL OR a.appointmentType = :appointmentType) " +
           "AND (:staffId IS NULL OR a.staffId = :staffId) " +
           "AND (:branchId IS NULL OR a.branchId = :branchId) " +
           "AND (:serviceId IS NULL OR a.serviceId = :serviceId) " +
           "AND (:startDate IS NULL OR a.appointmentDate >= :startDate) " +
           "AND (:endDate IS NULL OR a.appointmentDate <= :endDate) " +
           "AND (:searchTerm IS NULL OR " +
           "     LOWER(a.client.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(a.client.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(a.serviceName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "     LOWER(a.staffName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "ORDER BY a.appointmentDate DESC")
    Page<Appointment> findAppointmentsWithFilters(
        @Param("bizId") Long bizId,
        @Param("clientId") Long clientId,
        @Param("status") AppointmentStatus status,
        @Param("appointmentType") AppointmentType appointmentType,
        @Param("staffId") Long staffId,
        @Param("branchId") Long branchId,
        @Param("serviceId") Long serviceId,
        @Param("startDate") LocalDateTime startDate,
        @Param("endDate") LocalDateTime endDate,
        @Param("searchTerm") String searchTerm,
        Pageable pageable
    );

    // Scheduling analytics
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    Long getTotalAppointmentCount(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a.status, COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.status")
    List<Object[]> countAppointmentsByStatus(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a.appointmentType, COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.appointmentType")
    List<Object[]> countAppointmentsByType(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Staff utilization analytics
    @Query("SELECT a.staffId, a.staffName, COUNT(a), AVG(a.durationMinutes) FROM Appointment a WHERE a.bizId = :bizId AND a.staffId IS NOT NULL AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.staffId, a.staffName ORDER BY COUNT(a) DESC")
    List<Object[]> getStaffUtilizationAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a.staffId, a.staffName, COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.staffId IS NOT NULL AND a.status = 'COMPLETED' AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.staffId, a.staffName ORDER BY COUNT(a) DESC")
    List<Object[]> getStaffCompletedAppointments(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Service analytics
    @Query("SELECT a.serviceId, a.serviceName, COUNT(a), AVG(a.durationMinutes) FROM Appointment a WHERE a.bizId = :bizId AND a.serviceId IS NOT NULL AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.serviceId, a.serviceName ORDER BY COUNT(a) DESC")
    List<Object[]> getServiceUtilizationAnalytics(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Time-based analytics
    @Query("SELECT DATE(a.appointmentDate), COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY DATE(a.appointmentDate) ORDER BY DATE(a.appointmentDate)")
    List<Object[]> countAppointmentsByDate(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT HOUR(a.appointmentDate), COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY HOUR(a.appointmentDate) ORDER BY HOUR(a.appointmentDate)")
    List<Object[]> countAppointmentsByHour(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT DAYOFWEEK(a.appointmentDate), COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY DAYOFWEEK(a.appointmentDate) ORDER BY DAYOFWEEK(a.appointmentDate)")
    List<Object[]> countAppointmentsByDayOfWeek(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Branch analytics
    @Query("SELECT a.branchId, COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.branchId IS NOT NULL AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.branchId ORDER BY COUNT(a) DESC")
    List<Object[]> countAppointmentsByBranch(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Client analytics
    @Query("SELECT COUNT(DISTINCT a.client.custId) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate")
    Long getUniqueClientCount(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT a.client.custId, a.client.name, COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.client.custId, a.client.name ORDER BY COUNT(a) DESC")
    List<Object[]> getTopClientsByAppointmentCount(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate, Pageable pageable);

    // No-show and cancellation analytics
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.status = 'NO_SHOW' AND a.appointmentDate BETWEEN :startDate AND :endDate")
    Long getNoShowCount(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.status = 'CANCELLED' AND a.appointmentDate BETWEEN :startDate AND :endDate")
    Long getCancellationCount(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Booking source analytics
    @Query("SELECT a.bookingSource, COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.bookingSource IS NOT NULL AND a.appointmentDate BETWEEN :startDate AND :endDate GROUP BY a.bookingSource")
    List<Object[]> countAppointmentsByBookingSource(@Param("bizId") Long bizId, @Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);

    // Availability and scheduling conflicts
    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId AND a.staffId = :staffId AND a.status IN ('SCHEDULED', 'CONFIRMED', 'IN_PROGRESS') AND " +
           "((a.appointmentDate <= :startTime AND a.endTime > :startTime) OR " +
           "(a.appointmentDate < :endTime AND a.endTime >= :endTime) OR " +
           "(a.appointmentDate >= :startTime AND a.endTime <= :endTime))")
    List<Appointment> findConflictingAppointments(@Param("bizId") Long bizId, @Param("staffId") Long staffId, 
                                                 @Param("startTime") LocalDateTime startTime, @Param("endTime") LocalDateTime endTime);

    // Recent appointments
    @Query("SELECT a FROM Appointment a WHERE a.bizId = :bizId ORDER BY a.createdOn DESC")
    List<Appointment> findRecentAppointments(@Param("bizId") Long bizId, Pageable pageable);

    // Walk-in appointments
    List<Appointment> findByBizIdAndAppointmentTypeOrderByAppointmentDateDesc(Long bizId, AppointmentType appointmentType);

    // Validation queries
    boolean existsByIdAndBizId(Long id, Long bizId);

    // Business details queries for dashboard
    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND DATE(a.appointmentDate) = DATE(:date)")
    Long countAppointmentsOnDate(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT COUNT(a) FROM Appointment a WHERE a.bizId = :bizId AND a.appointmentType = 'WALK_IN' AND DATE(a.appointmentDate) = DATE(:date)")
    Long countWalkInsOnDate(@Param("bizId") Long bizId, @Param("date") LocalDateTime date);

    @Query("SELECT a FROM Appointment a JOIN a.client c WHERE a.bizId = :bizId AND c.businessType = :businessType AND DATE(a.appointmentDate) = DATE(:date) ORDER BY a.appointmentDate DESC")
    List<Appointment> findAppointmentsByBusinessTypeOnDate(@Param("bizId") Long bizId, @Param("businessType") String businessType, @Param("date") LocalDateTime date);
}