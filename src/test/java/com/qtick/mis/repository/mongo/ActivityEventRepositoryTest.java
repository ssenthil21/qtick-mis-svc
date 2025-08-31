package com.qtick.mis.repository.mongo;

import com.qtick.mis.document.ActivityEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for ActivityEventRepository.
 */
@DataMongoTest
@ActiveProfiles("test")
class ActivityEventRepositoryTest {

    @Autowired
    private ActivityEventRepository activityEventRepository;

    private ActivityEvent testEvent1;
    private ActivityEvent testEvent2;
    private ActivityEvent testEvent3;

    @BeforeEach
    void setUp() {
        // Clear the collection
        activityEventRepository.deleteAll();

        // Create test events
        testEvent1 = new ActivityEvent(123L, "ENQUIRY_CREATED", "ENQUIRY", 456L, 
                                     "New enquiry created", "John Doe submitted a new enquiry", 
                                     101L, "Agent Smith");
        testEvent1.setBranchId(201L);
        testEvent1.setClientName("John Doe");
        testEvent1.setClientPhone("9876543210");
        testEvent1.setServiceName("Haircut");
        testEvent1.setSource("Website");
        testEvent1.setChannel("Online");
        testEvent1.addMetadata("enquiryId", 456L);
        testEvent1.addMetadata("priority", "HIGH");

        testEvent2 = new ActivityEvent(123L, "BILL_CREATED", "BILL", 789L, 
                                     "Bill generated", "Bill created for client", 
                                     102L, "Staff Member");
        testEvent2.setBranchId(201L);
        testEvent2.setClientName("Jane Smith");
        testEvent2.setClientPhone("9876543211");
        testEvent2.setServiceName("Massage");
        testEvent2.setSource("Walk-in");
        testEvent2.setChannel("Direct");
        testEvent2.addMetadata("billAmount", 1500.00);
        testEvent2.addMetadata("clientId", 999L);

        testEvent3 = new ActivityEvent(456L, "APPOINTMENT_SCHEDULED", "APPOINTMENT", 321L, 
                                     "Appointment scheduled", "New appointment booked", 
                                     103L, "Receptionist");
        testEvent3.setBranchId(202L);
        testEvent3.setClientName("Bob Johnson");
        testEvent3.setClientPhone("9876543212");
        testEvent3.setServiceName("Facial");
        testEvent3.setSource("Phone");
        testEvent3.setChannel("Phone");

        activityEventRepository.save(testEvent1);
        activityEventRepository.save(testEvent2);
        activityEventRepository.save(testEvent3);
    }

    @Test
    void shouldFindByBizIdOrderByCreatedAtDesc() {
        // When
        List<ActivityEvent> events = activityEventRepository.findByBizIdOrderByCreatedAtDesc(123L);

        // Then
        assertEquals(2, events.size());
        assertTrue(events.stream().anyMatch(e -> e.getId().equals(testEvent1.getId())));
        assertTrue(events.stream().anyMatch(e -> e.getId().equals(testEvent2.getId())));
        assertFalse(events.stream().anyMatch(e -> e.getId().equals(testEvent3.getId()))); // Different bizId
    }

    @Test
    void shouldFindByIdAndBizId() {
        // When
        Optional<ActivityEvent> found = activityEventRepository.findByIdAndBizId(testEvent1.getId(), 123L);
        Optional<ActivityEvent> notFound = activityEventRepository.findByIdAndBizId(testEvent1.getId(), 456L);

        // Then
        assertTrue(found.isPresent());
        assertEquals(testEvent1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndEntityTypeAndEntityId() {
        // When
        List<ActivityEvent> enquiryEvents = activityEventRepository.findByBizIdAndEntityTypeAndEntityIdOrderByCreatedAtDesc(123L, "ENQUIRY", 456L);
        List<ActivityEvent> billEvents = activityEventRepository.findByBizIdAndEntityTypeAndEntityIdOrderByCreatedAtDesc(123L, "BILL", 789L);

        // Then
        assertEquals(1, enquiryEvents.size());
        assertEquals(testEvent1.getId(), enquiryEvents.get(0).getId());
        assertEquals(1, billEvents.size());
        assertEquals(testEvent2.getId(), billEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndEventType() {
        // When
        List<ActivityEvent> enquiryCreatedEvents = activityEventRepository.findByBizIdAndEventTypeOrderByCreatedAtDesc(123L, "ENQUIRY_CREATED");
        List<ActivityEvent> billCreatedEvents = activityEventRepository.findByBizIdAndEventTypeOrderByCreatedAtDesc(123L, "BILL_CREATED");

        // Then
        assertEquals(1, enquiryCreatedEvents.size());
        assertEquals(testEvent1.getId(), enquiryCreatedEvents.get(0).getId());
        assertEquals(1, billCreatedEvents.size());
        assertEquals(testEvent2.getId(), billCreatedEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndUserId() {
        // When
        List<ActivityEvent> user101Events = activityEventRepository.findByBizIdAndUserIdOrderByCreatedAtDesc(123L, 101L);
        List<ActivityEvent> user102Events = activityEventRepository.findByBizIdAndUserIdOrderByCreatedAtDesc(123L, 102L);

        // Then
        assertEquals(1, user101Events.size());
        assertEquals(testEvent1.getId(), user101Events.get(0).getId());
        assertEquals(1, user102Events.size());
        assertEquals(testEvent2.getId(), user102Events.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndBranchId() {
        // When
        List<ActivityEvent> branch201Events = activityEventRepository.findByBizIdAndBranchIdOrderByCreatedAtDesc(123L, 201L);
        List<ActivityEvent> branch202Events = activityEventRepository.findByBizIdAndBranchIdOrderByCreatedAtDesc(123L, 202L);

        // Then
        assertEquals(2, branch201Events.size());
        assertEquals(0, branch202Events.size()); // testEvent3 has different bizId
    }

    @Test
    void shouldFindByBizIdAndCreatedAtBetween() {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusHours(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        // When
        List<ActivityEvent> eventsInRange = activityEventRepository.findByBizIdAndCreatedAtBetweenOrderByCreatedAtDesc(123L, startDate, endDate);

        // Then
        assertEquals(2, eventsInRange.size()); // Both testEvent1 and testEvent2 should be in range
    }

    @Test
    void shouldFindByBizIdAndServiceName() {
        // When
        List<ActivityEvent> haircutEvents = activityEventRepository.findByBizIdAndServiceNameOrderByCreatedAtDesc(123L, "Haircut");
        List<ActivityEvent> massageEvents = activityEventRepository.findByBizIdAndServiceNameOrderByCreatedAtDesc(123L, "Massage");

        // Then
        assertEquals(1, haircutEvents.size());
        assertEquals(testEvent1.getId(), haircutEvents.get(0).getId());
        assertEquals(1, massageEvents.size());
        assertEquals(testEvent2.getId(), massageEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndStaffName() {
        // When
        List<ActivityEvent> agentEvents = activityEventRepository.findByBizIdAndStaffNameOrderByCreatedAtDesc(123L, "Agent Smith");
        List<ActivityEvent> staffEvents = activityEventRepository.findByBizIdAndStaffNameOrderByCreatedAtDesc(123L, "Staff Member");

        // Then
        assertEquals(1, agentEvents.size());
        assertEquals(testEvent1.getId(), agentEvents.get(0).getId());
        assertEquals(1, staffEvents.size());
        assertEquals(testEvent2.getId(), staffEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndSource() {
        // When
        List<ActivityEvent> websiteEvents = activityEventRepository.findByBizIdAndSourceOrderByCreatedAtDesc(123L, "Website");
        List<ActivityEvent> walkinEvents = activityEventRepository.findByBizIdAndSourceOrderByCreatedAtDesc(123L, "Walk-in");

        // Then
        assertEquals(1, websiteEvents.size());
        assertEquals(testEvent1.getId(), websiteEvents.get(0).getId());
        assertEquals(1, walkinEvents.size());
        assertEquals(testEvent2.getId(), walkinEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndChannel() {
        // When
        List<ActivityEvent> onlineEvents = activityEventRepository.findByBizIdAndChannelOrderByCreatedAtDesc(123L, "Online");
        List<ActivityEvent> directEvents = activityEventRepository.findByBizIdAndChannelOrderByCreatedAtDesc(123L, "Direct");

        // Then
        assertEquals(1, onlineEvents.size());
        assertEquals(testEvent1.getId(), onlineEvents.get(0).getId());
        assertEquals(1, directEvents.size());
        assertEquals(testEvent2.getId(), directEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndClientName() {
        // When
        List<ActivityEvent> johnEvents = activityEventRepository.findByBizIdAndClientNameOrderByCreatedAtDesc(123L, "John Doe");
        List<ActivityEvent> janeEvents = activityEventRepository.findByBizIdAndClientNameOrderByCreatedAtDesc(123L, "Jane Smith");

        // Then
        assertEquals(1, johnEvents.size());
        assertEquals(testEvent1.getId(), johnEvents.get(0).getId());
        assertEquals(1, janeEvents.size());
        assertEquals(testEvent2.getId(), janeEvents.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndClientPhone() {
        // When
        List<ActivityEvent> phone1Events = activityEventRepository.findByBizIdAndClientPhoneOrderByCreatedAtDesc(123L, "9876543210");
        List<ActivityEvent> phone2Events = activityEventRepository.findByBizIdAndClientPhoneOrderByCreatedAtDesc(123L, "9876543211");

        // Then
        assertEquals(1, phone1Events.size());
        assertEquals(testEvent1.getId(), phone1Events.get(0).getId());
        assertEquals(1, phone2Events.size());
        assertEquals(testEvent2.getId(), phone2Events.get(0).getId());
    }

    @Test
    void shouldCountActivitiesInDateRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusHours(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        // When
        long count = activityEventRepository.countActivitiesInDateRange(123L, startDate, endDate);

        // Then
        assertEquals(2L, count); // Both testEvent1 and testEvent2 should be in range
    }

    @Test
    void shouldCountActivitiesByEventTypeInDateRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusHours(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        // When
        long enquiryCount = activityEventRepository.countActivitiesByEventTypeInDateRange(123L, "ENQUIRY_CREATED", startDate, endDate);
        long billCount = activityEventRepository.countActivitiesByEventTypeInDateRange(123L, "BILL_CREATED", startDate, endDate);

        // Then
        assertEquals(1L, enquiryCount);
        assertEquals(1L, billCount);
    }

    @Test
    void shouldCountActivitiesByUserInDateRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusHours(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        // When
        long user101Count = activityEventRepository.countActivitiesByUserInDateRange(123L, 101L, startDate, endDate);
        long user102Count = activityEventRepository.countActivitiesByUserInDateRange(123L, 102L, startDate, endDate);

        // Then
        assertEquals(1L, user101Count);
        assertEquals(1L, user102Count);
    }

    @Test
    void shouldCountActivitiesByEntityTypeInDateRange() {
        // Given
        LocalDateTime startDate = LocalDateTime.now().minusHours(1);
        LocalDateTime endDate = LocalDateTime.now().plusHours(1);

        // When
        long enquiryEntityCount = activityEventRepository.countActivitiesByEntityTypeInDateRange(123L, "ENQUIRY", startDate, endDate);
        long billEntityCount = activityEventRepository.countActivitiesByEntityTypeInDateRange(123L, "BILL", startDate, endDate);

        // Then
        assertEquals(1L, enquiryEntityCount);
        assertEquals(1L, billEntityCount);
    }

    @Test
    void shouldCountActivitiesForEntity() {
        // When
        long enquiry456Count = activityEventRepository.countActivitiesForEntity(123L, "ENQUIRY", 456L);
        long bill789Count = activityEventRepository.countActivitiesForEntity(123L, "BILL", 789L);

        // Then
        assertEquals(1L, enquiry456Count);
        assertEquals(1L, bill789Count);
    }

    @Test
    void shouldCheckExistsByIdAndBizId() {
        // When
        boolean exists = activityEventRepository.existsByIdAndBizId(testEvent1.getId(), 123L);
        boolean notExists = activityEventRepository.existsByIdAndBizId(testEvent1.getId(), 456L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndEntityTypeAndEntityId() {
        // When
        boolean exists = activityEventRepository.existsByBizIdAndEntityTypeAndEntityId(123L, "ENQUIRY", 456L);
        boolean notExists = activityEventRepository.existsByBizIdAndEntityTypeAndEntityId(123L, "ENQUIRY", 999L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldFindWithPagination() {
        // When
        Page<ActivityEvent> page = activityEventRepository.findByBizIdOrderByCreatedAtDesc(123L, PageRequest.of(0, 1));

        // Then
        assertEquals(2, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertEquals(1, page.getContent().size());
    }

    @Test
    void shouldFindRecentActivities() {
        // When
        List<ActivityEvent> recentActivities = activityEventRepository.findRecentActivities(123L, PageRequest.of(0, 5));

        // Then
        assertEquals(2, recentActivities.size());
        assertTrue(recentActivities.stream().anyMatch(e -> e.getId().equals(testEvent1.getId())));
        assertTrue(recentActivities.stream().anyMatch(e -> e.getId().equals(testEvent2.getId())));
    }

    @Test
    void shouldFindAllActivitiesForEntity() {
        // When
        List<ActivityEvent> enquiryActivities = activityEventRepository.findAllActivitiesForEntity(123L, "ENQUIRY", 456L);
        List<ActivityEvent> billActivities = activityEventRepository.findAllActivitiesForEntity(123L, "BILL", 789L);

        // Then
        assertEquals(1, enquiryActivities.size());
        assertEquals(testEvent1.getId(), enquiryActivities.get(0).getId());
        assertEquals(1, billActivities.size());
        assertEquals(testEvent2.getId(), billActivities.get(0).getId());
    }
}