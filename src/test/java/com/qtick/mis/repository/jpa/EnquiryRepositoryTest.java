package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.Enquiry;
import com.qtick.mis.entity.EnquiryStage;
import com.qtick.mis.entity.EnquiryStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for EnquiryRepository.
 */
@DataJpaTest
@ActiveProfiles("test")
class EnquiryRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private EnquiryRepository enquiryRepository;

    private Enquiry testEnquiry1;
    private Enquiry testEnquiry2;
    private Enquiry testEnquiry3;

    @BeforeEach
    void setUp() {
        // Create test enquiries
        testEnquiry1 = new Enquiry(123L, "John Doe", "9876543210", "john@example.com", 
                                  EnquiryStage.LEAD, EnquiryStatus.ACTIVE);
        testEnquiry1.setSource("Website");
        testEnquiry1.setChannel("Online");
        testEnquiry1.setAssigneeId(101L);
        testEnquiry1.setBranchId(201L);
        testEnquiry1.setSrvcEnq(301L);

        testEnquiry2 = new Enquiry(123L, "Jane Smith", "9876543211", "jane@example.com", 
                                  EnquiryStage.QUALIFIED, EnquiryStatus.ACTIVE);
        testEnquiry2.setSource("Referral");
        testEnquiry2.setChannel("Phone");
        testEnquiry2.setAssigneeId(102L);
        testEnquiry2.setBranchId(201L);
        testEnquiry2.setSrvcEnq(302L);

        testEnquiry3 = new Enquiry(456L, "Bob Johnson", "9876543212", "bob@example.com", 
                                  EnquiryStage.CLOSED_WON, EnquiryStatus.COMPLETED);
        testEnquiry3.setSource("Walk-in");
        testEnquiry3.setChannel("Direct");
        testEnquiry3.setAssigneeId(103L);
        testEnquiry3.setBranchId(202L);
        testEnquiry3.setSrvcEnq(303L);
        testEnquiry3.setClosureDate(LocalDateTime.now());

        entityManager.persistAndFlush(testEnquiry1);
        entityManager.persistAndFlush(testEnquiry2);
        entityManager.persistAndFlush(testEnquiry3);
    }

    @Test
    void shouldFindByBizIdOrderByCreatedOnDesc() {
        // When
        List<Enquiry> enquiries = enquiryRepository.findByBizIdOrderByCreatedOnDesc(123L);

        // Then
        assertEquals(2, enquiries.size());
        assertTrue(enquiries.contains(testEnquiry1));
        assertTrue(enquiries.contains(testEnquiry2));
        assertFalse(enquiries.contains(testEnquiry3)); // Different bizId
    }

    @Test
    void shouldFindByIdAndBizId() {
        // When
        Optional<Enquiry> found = enquiryRepository.findByIdAndBizId(testEnquiry1.getId(), 123L);
        Optional<Enquiry> notFound = enquiryRepository.findByIdAndBizId(testEnquiry1.getId(), 456L);

        // Then
        assertTrue(found.isPresent());
        assertEquals(testEnquiry1.getId(), found.get().getId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndStage() {
        // When
        List<Enquiry> leadEnquiries = enquiryRepository.findByBizIdAndStageOrderByLastTouchDateDesc(123L, EnquiryStage.LEAD);
        List<Enquiry> qualifiedEnquiries = enquiryRepository.findByBizIdAndStageOrderByLastTouchDateDesc(123L, EnquiryStage.QUALIFIED);

        // Then
        assertEquals(1, leadEnquiries.size());
        assertEquals(testEnquiry1.getId(), leadEnquiries.get(0).getId());
        assertEquals(1, qualifiedEnquiries.size());
        assertEquals(testEnquiry2.getId(), qualifiedEnquiries.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndStatus() {
        // When
        List<Enquiry> activeEnquiries = enquiryRepository.findByBizIdAndStatusOrderByCreatedOnDesc(123L, EnquiryStatus.ACTIVE);
        List<Enquiry> completedEnquiries = enquiryRepository.findByBizIdAndStatusOrderByCreatedOnDesc(123L, EnquiryStatus.COMPLETED);

        // Then
        assertEquals(2, activeEnquiries.size());
        assertEquals(0, completedEnquiries.size()); // testEnquiry3 has different bizId
    }

    @Test
    void shouldFindByBizIdAndAssigneeId() {
        // When
        List<Enquiry> assignee101Enquiries = enquiryRepository.findByBizIdAndAssigneeIdOrderByLastTouchDateDesc(123L, 101L);
        List<Enquiry> assignee102Enquiries = enquiryRepository.findByBizIdAndAssigneeIdOrderByLastTouchDateDesc(123L, 102L);

        // Then
        assertEquals(1, assignee101Enquiries.size());
        assertEquals(testEnquiry1.getId(), assignee101Enquiries.get(0).getId());
        assertEquals(1, assignee102Enquiries.size());
        assertEquals(testEnquiry2.getId(), assignee102Enquiries.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndBranchId() {
        // When
        List<Enquiry> branch201Enquiries = enquiryRepository.findByBizIdAndBranchIdOrderByCreatedOnDesc(123L, 201L);
        List<Enquiry> branch202Enquiries = enquiryRepository.findByBizIdAndBranchIdOrderByCreatedOnDesc(123L, 202L);

        // Then
        assertEquals(2, branch201Enquiries.size());
        assertEquals(0, branch202Enquiries.size()); // testEnquiry3 has different bizId
    }

    @Test
    void shouldFindByBizIdAndSource() {
        // When
        List<Enquiry> websiteEnquiries = enquiryRepository.findByBizIdAndSourceOrderByCreatedOnDesc(123L, "Website");
        List<Enquiry> referralEnquiries = enquiryRepository.findByBizIdAndSourceOrderByCreatedOnDesc(123L, "Referral");

        // Then
        assertEquals(1, websiteEnquiries.size());
        assertEquals(testEnquiry1.getId(), websiteEnquiries.get(0).getId());
        assertEquals(1, referralEnquiries.size());
        assertEquals(testEnquiry2.getId(), referralEnquiries.get(0).getId());
    }

    @Test
    void shouldFindByBizIdAndChannel() {
        // When
        List<Enquiry> onlineEnquiries = enquiryRepository.findByBizIdAndChannelOrderByCreatedOnDesc(123L, "Online");
        List<Enquiry> phoneEnquiries = enquiryRepository.findByBizIdAndChannelOrderByCreatedOnDesc(123L, "Phone");

        // Then
        assertEquals(1, onlineEnquiries.size());
        assertEquals(testEnquiry1.getId(), onlineEnquiries.get(0).getId());
        assertEquals(1, phoneEnquiries.size());
        assertEquals(testEnquiry2.getId(), phoneEnquiries.get(0).getId());
    }

    @Test
    void shouldFindEnquiriesWithFilters() {
        // When
        Page<Enquiry> allEnquiries = enquiryRepository.findEnquiriesWithFilters(
            123L, null, null, null, null, null, null, null, null, PageRequest.of(0, 10));

        Page<Enquiry> leadEnquiries = enquiryRepository.findEnquiriesWithFilters(
            123L, EnquiryStage.LEAD, null, null, null, null, null, null, null, PageRequest.of(0, 10));

        Page<Enquiry> searchByName = enquiryRepository.findEnquiriesWithFilters(
            123L, null, null, null, null, null, null, null, "John", PageRequest.of(0, 10));

        Page<Enquiry> searchByPhone = enquiryRepository.findEnquiriesWithFilters(
            123L, null, null, null, null, null, null, null, "9876543210", PageRequest.of(0, 10));

        // Then
        assertEquals(2, allEnquiries.getTotalElements());
        assertEquals(1, leadEnquiries.getTotalElements());
        assertEquals(testEnquiry1.getId(), leadEnquiries.getContent().get(0).getId());
        assertEquals(1, searchByName.getTotalElements());
        assertEquals(testEnquiry1.getId(), searchByName.getContent().get(0).getId());
        assertEquals(1, searchByPhone.getTotalElements());
        assertEquals(testEnquiry1.getId(), searchByPhone.getContent().get(0).getId());
    }

    @Test
    void shouldCountEnquiriesByStage() {
        // When
        List<Object[]> stageCount = enquiryRepository.countEnquiriesByStage(123L);

        // Then
        assertEquals(2, stageCount.size());
        // Results should contain LEAD and QUALIFIED stages with count 1 each
        boolean foundLead = false, foundQualified = false;
        for (Object[] result : stageCount) {
            EnquiryStage stage = (EnquiryStage) result[0];
            Long count = (Long) result[1];
            if (stage == EnquiryStage.LEAD) {
                foundLead = true;
                assertEquals(1L, count);
            } else if (stage == EnquiryStage.QUALIFIED) {
                foundQualified = true;
                assertEquals(1L, count);
            }
        }
        assertTrue(foundLead);
        assertTrue(foundQualified);
    }

    @Test
    void shouldCountNewEnquiries() {
        // When
        Long count = enquiryRepository.countNewEnquiries(123L, LocalDateTime.now().minusDays(1));

        // Then
        assertEquals(2L, count); // Both testEnquiry1 and testEnquiry2 for bizId 123
    }

    @Test
    void shouldCountConvertedEnquiries() {
        // When
        Long convertedCount = enquiryRepository.countConvertedEnquiries(123L, LocalDateTime.now().minusDays(1));
        Long convertedCountDifferentBiz = enquiryRepository.countConvertedEnquiries(456L, LocalDateTime.now().minusDays(1));

        // Then
        assertEquals(0L, convertedCount); // No CLOSED_WON enquiries for bizId 123
        assertEquals(1L, convertedCountDifferentBiz); // testEnquiry3 is CLOSED_WON for bizId 456
    }

    @Test
    void shouldCheckExistsByIdAndBizId() {
        // When
        boolean exists = enquiryRepository.existsByIdAndBizId(testEnquiry1.getId(), 123L);
        boolean notExists = enquiryRepository.existsByIdAndBizId(testEnquiry1.getId(), 456L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndPhoneAndStatus() {
        // When
        boolean exists = enquiryRepository.existsByBizIdAndPhoneAndStatus(123L, "9876543210", EnquiryStatus.ACTIVE);
        boolean notExists = enquiryRepository.existsByBizIdAndPhoneAndStatus(123L, "9876543210", EnquiryStatus.COMPLETED);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndEmailAndStatus() {
        // When
        boolean exists = enquiryRepository.existsByBizIdAndEmailAndStatus(123L, "john@example.com", EnquiryStatus.ACTIVE);
        boolean notExists = enquiryRepository.existsByBizIdAndEmailAndStatus(123L, "john@example.com", EnquiryStatus.COMPLETED);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldFindWithPagination() {
        // When
        Page<Enquiry> page = enquiryRepository.findByBizIdOrderByCreatedOnDesc(123L, PageRequest.of(0, 1));

        // Then
        assertEquals(2, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertEquals(1, page.getContent().size());
    }
}