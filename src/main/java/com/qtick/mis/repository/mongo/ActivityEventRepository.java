package com.qtick.mis.repository.mongo;

import com.qtick.mis.document.ActivityEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for ActivityEvent document with timeline queries.
 * Provides methods for activity timeline retrieval, event filtering, and analytics.
 */
@Repository
public interface ActivityEventRepository extends MongoRepository<ActivityEvent, String> {

    // Basic tenant-aware queries
    List<ActivityEvent> findByBizIdOrderByCreatedAtDesc(Long bizId);

    Page<ActivityEvent> findByBizIdOrderByCreatedAtDesc(Long bizId, Pageable pageable);

    Optional<ActivityEvent> findByIdAndBizId(String id, Long bizId);

    // Entity-specific timeline queries
    List<ActivityEvent> findByBizIdAndEntityTypeAndEntityIdOrderByCreatedAtDesc(Long bizId, String entityType, Long entityId);

    Page<ActivityEvent> findByBizIdAndEntityTypeAndEntityIdOrderByCreatedAtDesc(Long bizId, String entityType, Long entityId, Pageable pageable);

    // Event type filtering
    List<ActivityEvent> findByBizIdAndEventTypeOrderByCreatedAtDesc(Long bizId, String eventType);

    Page<ActivityEvent> findByBizIdAndEventTypeOrderByCreatedAtDesc(Long bizId, String eventType, Pageable pageable);

    // User-specific queries
    List<ActivityEvent> findByBizIdAndUserIdOrderByCreatedAtDesc(Long bizId, Long userId);

    Page<ActivityEvent> findByBizIdAndUserIdOrderByCreatedAtDesc(Long bizId, Long userId, Pageable pageable);

    // Branch filtering
    List<ActivityEvent> findByBizIdAndBranchIdOrderByCreatedAtDesc(Long bizId, Long branchId);

    Page<ActivityEvent> findByBizIdAndBranchIdOrderByCreatedAtDesc(Long bizId, Long branchId, Pageable pageable);

    // Date range queries
    List<ActivityEvent> findByBizIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    Page<ActivityEvent> findByBizIdAndCreatedAtBetweenOrderByCreatedAtDesc(Long bizId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Recent activities
    @Query("{ 'bizId': ?0 }")
    List<ActivityEvent> findRecentActivities(Long bizId, Pageable pageable);

    // Client timeline queries (for client profile pages)
    @Query("{ 'bizId': ?0, $or: [ " +
           "{ 'entityType': 'CLIENT', 'entityId': ?1 }, " +
           "{ 'entityType': 'BILL', 'metadata.clientId': ?1 }, " +
           "{ 'entityType': 'APPOINTMENT', 'metadata.clientId': ?1 }, " +
           "{ 'entityType': 'ENQUIRY', 'metadata.clientId': ?1 } " +
           "] }")
    List<ActivityEvent> findClientTimeline(Long bizId, Long clientId, Pageable pageable);

    @Query("{ 'bizId': ?0, $or: [ " +
           "{ 'entityType': 'CLIENT', 'entityId': ?1 }, " +
           "{ 'entityType': 'BILL', 'metadata.clientId': ?1 }, " +
           "{ 'entityType': 'APPOINTMENT', 'metadata.clientId': ?1 }, " +
           "{ 'entityType': 'ENQUIRY', 'metadata.clientId': ?1 } " +
           "], 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<ActivityEvent> findClientTimelineInDateRange(Long bizId, Long clientId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Multi-criteria search
    @Query("{ 'bizId': ?0, " +
           "$and: [ " +
           "{ $or: [ { 'entityType': { $exists: false } }, { 'entityType': { $in: ?1 } } ] }, " +
           "{ $or: [ { 'eventType': { $exists: false } }, { 'eventType': { $in: ?2 } } ] }, " +
           "{ $or: [ { 'userId': { $exists: false } }, { 'userId': { $in: ?3 } } ] }, " +
           "{ $or: [ { 'branchId': { $exists: false } }, { 'branchId': { $in: ?4 } } ] }, " +
           "{ $or: [ { 'createdAt': { $exists: false } }, { 'createdAt': { $gte: ?5 } } ] }, " +
           "{ $or: [ { 'createdAt': { $exists: false } }, { 'createdAt': { $lte: ?6 } } ] } " +
           "] }")
    Page<ActivityEvent> findActivitiesWithFilters(
        Long bizId,
        List<String> entityTypes,
        List<String> eventTypes,
        List<Long> userIds,
        List<Long> branchIds,
        LocalDateTime startDate,
        LocalDateTime endDate,
        Pageable pageable
    );

    // Text search across title and description
    @Query("{ 'bizId': ?0, $text: { $search: ?1 } }")
    List<ActivityEvent> findByBizIdAndTextSearch(Long bizId, String searchText, Pageable pageable);

    // Analytics queries
    @Query(value = "{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }", 
           fields = "{ 'eventType': 1 }")
    List<ActivityEvent> findEventTypesInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    long countActivitiesInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'eventType': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    long countActivitiesByEventTypeInDateRange(Long bizId, String eventType, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'userId': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    long countActivitiesByUserInDateRange(Long bizId, Long userId, LocalDateTime startDate, LocalDateTime endDate);

    // Entity-specific analytics
    @Query("{ 'bizId': ?0, 'entityType': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    long countActivitiesByEntityTypeInDateRange(Long bizId, String entityType, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'entityType': ?1, 'entityId': ?2 }")
    long countActivitiesForEntity(Long bizId, String entityType, Long entityId);

    // Service and staff related queries
    List<ActivityEvent> findByBizIdAndServiceNameOrderByCreatedAtDesc(Long bizId, String serviceName);

    List<ActivityEvent> findByBizIdAndStaffNameOrderByCreatedAtDesc(Long bizId, String staffName);

    // Source and channel analytics
    List<ActivityEvent> findByBizIdAndSourceOrderByCreatedAtDesc(Long bizId, String source);

    List<ActivityEvent> findByBizIdAndChannelOrderByCreatedAtDesc(Long bizId, String channel);

    @Query("{ 'bizId': ?0, 'source': { $exists: true, $ne: null }, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<ActivityEvent> findActivitiesWithSourceInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    @Query("{ 'bizId': ?0, 'channel': { $exists: true, $ne: null }, 'createdAt': { $gte: ?1, $lte: ?2 } }")
    List<ActivityEvent> findActivitiesWithChannelInDateRange(Long bizId, LocalDateTime startDate, LocalDateTime endDate);

    // Client-specific queries for timeline
    List<ActivityEvent> findByBizIdAndClientNameOrderByCreatedAtDesc(Long bizId, String clientName);

    List<ActivityEvent> findByBizIdAndClientPhoneOrderByCreatedAtDesc(Long bizId, String clientPhone);

    @Query("{ 'bizId': ?0, $or: [ " +
           "{ 'clientName': { $regex: ?1, $options: 'i' } }, " +
           "{ 'clientPhone': { $regex: ?1, $options: 'i' } }, " +
           "{ 'title': { $regex: ?1, $options: 'i' } }, " +
           "{ 'description': { $regex: ?1, $options: 'i' } } " +
           "] }")
    List<ActivityEvent> findByBizIdAndSearchTerm(Long bizId, String searchTerm, Pageable pageable);

    // Bulk operations
    @Query("{ 'bizId': ?0, 'entityType': ?1, 'entityId': ?2 }")
    List<ActivityEvent> findAllActivitiesForEntity(Long bizId, String entityType, Long entityId);

    void deleteByBizIdAndEntityTypeAndEntityId(Long bizId, String entityType, Long entityId);

    // Validation queries
    boolean existsByIdAndBizId(String id, Long bizId);

    boolean existsByBizIdAndEntityTypeAndEntityId(Long bizId, String entityType, Long entityId);

    // Cleanup queries
    @Query("{ 'bizId': ?0, 'createdAt': { $lt: ?1 } }")
    List<ActivityEvent> findOldActivities(Long bizId, LocalDateTime cutoffDate);

    void deleteByBizIdAndCreatedAtBefore(Long bizId, LocalDateTime cutoffDate);

    // Dashboard queries for activity highlights
    @Query(value = "{ 'bizId': ?0, 'createdAt': { $gte: ?1 } }", 
           sort = "{ 'createdAt': -1 }")
    List<ActivityEvent> findRecentActivitiesForDashboard(Long bizId, LocalDateTime since, Pageable pageable);

    // Branch-specific timeline
    @Query("{ 'bizId': ?0, 'branchId': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<ActivityEvent> findBranchActivitiesInDateRange(Long bizId, Long branchId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // User activity tracking
    @Query("{ 'bizId': ?0, 'userId': ?1, 'createdAt': { $gte: ?2, $lte: ?3 } }")
    List<ActivityEvent> findUserActivitiesInDateRange(Long bizId, Long userId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // Event type statistics
    @Query(value = "{ 'bizId': ?0, 'createdAt': { $gte: ?1, $lte: ?2 } }", 
           fields = "{ 'eventType': 1, '_id': 0 }")
    List<ActivityEvent> getEventTypesForAnalytics(Long bizId, LocalDateTime startDate, LocalDateTime endDate);
}