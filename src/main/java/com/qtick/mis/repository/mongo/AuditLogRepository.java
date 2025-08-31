package com.qtick.mis.repository.mongo;

import com.qtick.mis.document.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for AuditLog document with security event queries.
 * Provides methods for audit trail retrieval, security monitoring, and compliance reporting.
 */
@Repository
public interface AuditLogRepository extends MongoRepository<AuditLog, String> {

    // Basic tenant-aware queries
    List<AuditLog> findByBizIdOrderByCreatedAtDesc(Long bizId);

    Page<AuditLog> findByBizIdOrderByCreatedAtDesc(Long bizId, Pageable pageable);

    Optional<AuditLog> findByIdAndBizId(String id, Long bizId);

    // Action-based queries
    List<AuditLog> findByBizIdAndActionOrderByCreatedAtDesc(Long bizId, String action);

    Page<AuditLog> findByBizIdAndActionOrderByCreatedAtDesc(Long bizId, String action, Pageable pageable);

    // Entity-specific audit trails
    List<AuditLog> findByBizIdAndEntityTypeAndEntityIdOrderByCreatedAtDesc(Long bizId, String entityType, Long entityId);

    Page<AuditLog> findByBizIdAndEntityTypeAndEntityIdOrderByCreatedAtDesc(Long bizId, String entityType, Long entityId, Pageable pageable);

    List<AuditLog> findByBizIdAndEntityTypeOrderByCreatedAtDesc(Long bizId, String entityType);

    // User-specific audit trails
    List<AuditLog> findByBizIdAndUserIdOrderByCreatedAtDesc(Long bizId, Long userId);

    Page<AuditLog> findByBizIdAndUserIdOrderByCreatedAtDesc(Long bizId, Long userId, Pageable pageable);

    List<AuditLog> findByBizIdAndUserNameOrderByCreatedAtDesc(Long bizId, String userName);

    // Severity-based queries
    List<AuditLog> findByBizIdAndSeverityOrderByCreatedAtDesc(Long bizId, String severity);

    Page<AuditLog> findByBizIdAndSeverityOrderByCreatedAtDesc(Long bizId, String severity, Pageable pageable);

    List<AuditLog> findByBizIdAndSeverityInOrderByCreatedAtDesc(Long bizId, List<String> severities);

    // Success/failure queries
    List<AuditLog> findByBizIdAndSuccessfulOrderByCreatedAtDesc(Long bizId, Boolean successful);

    Page<AuditLog> findByBizIdAndSuccessfulOrderByCreatedAtDesc(Long bizId, Boolean successful, Pageable pageable);

    List<AuditLog> findByBizIdAndSuccessfulFalseOrderByCreatedAtDesc(Long bizId);

    // Date range queries
    List<AuditLog> findByBizIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    Page<AuditLog> findByBizIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Branch-specific queries
    List<AuditLog> findByBizIdAndBranchIdOrderByCreatedAtDesc(Long bizId, Long branchId);

    Page<AuditLog> findByBizIdAndBranchIdOrderByCreatedAtDesc(Long bizId, Long branchId, Pageable pageable);

    // Security event queries
    @Query("{ 'bizId': ?0, $or: [ " +
           "{ 'entityType': 'SECURITY' }, " +
           "{ 'action': { $in: ['LOGIN', 'LOGOUT', 'ACCESS_DENIED', 'PERMISSION_VIOLATION'] } } " +
           "] }")
    List<AuditLog> findSecurityEvents(Long bizId, Pageable pageable);

    @Query("{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 }, $or: [ " +
           "{ 'entityType': 'SECURITY' }, " +
           "{ 'action': { $in: ['LOGIN', 'LOGOUT', 'ACCESS_DENIED', 'PERMISSION_VIOLATION'] } } " +
           "] }")
    List<AuditLog> findSecurityEventsInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Failed operations
    @Query("{ 'bizId': ?0, 'successful': false, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<AuditLog> findFailedOperationsInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Multi-criteria search
    @Query("{ 'bizId': ?0, " +
           "$and: [ " +
           "{ $or: [ { 'action': { $exists: false } }, { 'action': { $in: ?1 } } ] }, " +
           "{ $or: [ { 'entityType': { $exists: false } }, { 'entityType': { $in: ?2 } } ] }, " +
           "{ $or: [ { 'userId': { $exists: false } }, { 'userId': { $in: ?3 } } ] }, " +
           "{ $or: [ { 'severity': { $exists: false } }, { 'severity': { $in: ?4 } } ] }, " +
           "{ $or: [ { 'successful': { $exists: false } }, { 'successful': ?5 } ] }, " +
           "{ $or: [ { 'createdAt': { $exists: false } }, { 'createdAt': { $gte: ?6 } } ] }, " +
           "{ $or: [ { 'createdAt': { $exists: false } }, { 'createdAt': { $lte: ?7 } } ] } " +
           "] }")
    Page<AuditLog> findAuditLogsWithFilters(
        Long bizId,
        List<String> actions,
        List<String> entityTypes,
        List<Long> userIds,
        List<String> severities,
        Boolean successful,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Pageable pageable
    );

    // Text search across description and error messages
    @Query("{ 'bizId': ?0, $or: [ " +
           "{ 'description': { $regex: ?1, $options: 'i' } }, " +
           "{ 'errorMessage': { $regex: ?1, $options: 'i' } }, " +
           "{ 'entityName': { $regex: ?1, $options: 'i' } } " +
           "] }")
    List<AuditLog> findByBizIdAndSearchTerm(Long bizId, String searchTerm, Pageable pageable);

    // IP address and session tracking
    List<AuditLog> findByBizIdAndIpAddressOrderByCreatedAtDesc(Long bizId, String ipAddress);

    List<AuditLog> findByBizIdAndSessionIdOrderByCreatedAtDesc(Long bizId, String sessionId);

    @Query("{ 'bizId': ?0, 'ipAddress': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<AuditLog> findByBizIdAndIpAddressInDateRange(Long bizId, String ipAddress, LocalDateTime startDate, LocalDateTime endDate);

    // Correlation ID tracking
    List<AuditLog> findByBizIdAndCorrelationIdOrderByCreatedAtDesc(Long bizId, String correlationId);

    // JWT and tenant context queries
    List<AuditLog> findByBizIdAndJwtSubjectOrderByCreatedAtDesc(Long bizId, String jwtSubject);

    List<AuditLog> findByBizIdAndTenantContextOrderByCreatedAtDesc(Long bizId, String tenantContext);

    // HTTP method and path analytics
    @Query("{ 'bizId': ?0, 'httpMethod': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<AuditLog> findByBizIdAndHttpMethodInDateRange(Long bizId, String httpMethod, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'requestPath': { $regex: ?1 }, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<AuditLog> findByBizIdAndRequestPathPatternInDateRange(Long bizId, String pathPattern, LocalDateTime startDate, LocalDateTime endDate);

    // Analytics queries
    @Query("{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    long countAuditLogsInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'action': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    long countAuditLogsByActionInDateRange(Long bizId, String action, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'userId': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    long countAuditLogsByUserInDateRange(Long bizId, Long userId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'successful': false, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    long countFailedOperationsInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // User activity analytics
    @Query(value = "{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }", 
           fields = "{ 'userId': 1, 'userName': 1, 'action': 1 }")
    List<AuditLog> getUserActivityForAnalytics(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }", 
           fields = "{ 'action': 1, '_id': 0 }")
    List<AuditLog> getActionsForAnalytics(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // Error pattern analysis
    @Query("{ 'bizId': ?0, 'successful': false, 'errorMessage': { $exists: true, $ne: null }, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<AuditLog> findErrorsInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    @Query(value = "{ 'bizId': ?0, 'successful': false, 'createdAt': { $gte: ?1, $lte: ?2 } }", 
           fields = "{ 'errorMessage': 1, 'action': 1, 'entityType': 1, 'createdAt': 1 }")
    List<AuditLog> getErrorPatternsForAnalysis(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // Recent activities for monitoring
    @Query(value = "{ 'bizId': ?0 }", sort = "{ 'createdAt': -1 }")
    List<AuditLog> findRecentAuditLogs(Long bizId, Pageable pageable);

    @Query(value = "{ 'bizId': ?0, 'severity': { $in: ['ERROR', 'CRITICAL'] } }", sort = "{ 'createdAt': -1 }")
    List<AuditLog> findRecentCriticalEvents(Long bizId, Pageable pageable);

    // Compliance and retention queries
    @Query("{ 'bizId': ?0, 'createdAt': { $lt: ?1 } }")
    List<AuditLog> findOldAuditLogs(Long bizId, LocalDateTime cutoffDate);

    void deleteByBizIdAndCreatedAtBefore(Long bizId, LocalDateTime cutoffDate);

    // Data change tracking
    @Query("{ 'bizId': ?0, 'entityType': ?1, 'entityId': ?2, 'oldValues': { $exists: true, $ne: {} } }")
    List<AuditLog> findDataChangesForEntity(Long bizId, String entityType, Long entityId, Pageable pageable);

    @Query("{ 'bizId': ?0, 'action': { $in: ['CREATE', 'UPDATE', 'DELETE'] }, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<AuditLog> findDataChangesInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Validation queries
    boolean existsByIdAndBizId(String id, Long bizId);

    boolean existsByBizIdAndCorrelationId(Long bizId, String correlationId);

    // Bulk operations for cleanup
    @Query("{ 'bizId': ?0, 'severity': 'INFO', 'createdAt': { $lt: ?1 } }")
    List<AuditLog> findOldInfoLogs(Long bizId, LocalDateTime cutoffDate);

    void deleteByBizIdAndSeverityAndCreatedAtBefore(Long bizId, String severity, LocalDateTime cutoffDate);

    // Performance monitoring
    @Query("{ 'bizId': ?0, 'requestPath': { $exists: true }, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<AuditLog> findApiCallsInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // Branch-specific security monitoring
    @Query("{ 'bizId': ?0, 'branchId': ?1, 'severity': { $in: ['ERROR', 'CRITICAL'] }, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<AuditLog> findBranchSecurityEventsInDateRange(Long bizId, Long branchId, LocalDateTime startDate, LocalDateTime endDate);
}