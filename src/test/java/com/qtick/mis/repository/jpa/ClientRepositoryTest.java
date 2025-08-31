package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.Client;
import com.qtick.mis.entity.ClientStatus;
import com.qtick.mis.entity.Gender;
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
 * Unit tests for ClientRepository.
 */
@DataJpaTest
@ActiveProfiles("test")
class ClientRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ClientRepository clientRepository;

    private Client testClient1;
    private Client testClient2;
    private Client testClient3;

    @BeforeEach
    void setUp() {
        // Create test clients
        testClient1 = new Client(123L, "John Doe", "9876543210", "john@example.com");
        testClient1.setGender(Gender.MALE);
        testClient1.setCity("Mumbai");
        testClient1.setState("Maharashtra");
        testClient1.setLoyaltyPoints(100);
        testClient1.setPreferredChannel("WhatsApp");
        testClient1.setTags("VIP,Regular");
        testClient1.setLastVisitDate(LocalDateTime.now().minusDays(5));

        testClient2 = new Client(123L, "Jane Smith", "9876543211", "jane@example.com");
        testClient2.setGender(Gender.FEMALE);
        testClient2.setCity("Delhi");
        testClient2.setState("Delhi");
        testClient2.setLoyaltyPoints(250);
        testClient2.setPreferredChannel("Email");
        testClient2.setTags("Premium");
        testClient2.setBusinessName("Jane's Boutique");
        testClient2.setBusinessType("Retail");
        testClient2.setLastVisitDate(LocalDateTime.now().minusDays(2));

        testClient3 = new Client(456L, "Bob Johnson", "9876543212", "bob@example.com");
        testClient3.setGender(Gender.MALE);
        testClient3.setCity("Bangalore");
        testClient3.setState("Karnataka");
        testClient3.setLoyaltyPoints(50);
        testClient3.setStatus(ClientStatus.INACTIVE);
        testClient3.setLastVisitDate(LocalDateTime.now().minusDays(30));

        entityManager.persistAndFlush(testClient1);
        entityManager.persistAndFlush(testClient2);
        entityManager.persistAndFlush(testClient3);
    }

    @Test
    void shouldFindByBizIdOrderByCreatedOnDesc() {
        // When
        List<Client> clients = clientRepository.findByBizIdOrderByCreatedOnDesc(123L);

        // Then
        assertEquals(2, clients.size());
        assertTrue(clients.contains(testClient1));
        assertTrue(clients.contains(testClient2));
        assertFalse(clients.contains(testClient3)); // Different bizId
    }

    @Test
    void shouldFindByCustIdAndBizId() {
        // When
        Optional<Client> found = clientRepository.findByCustIdAndBizId(testClient1.getCustId(), 123L);
        Optional<Client> notFound = clientRepository.findByCustIdAndBizId(testClient1.getCustId(), 456L);

        // Then
        assertTrue(found.isPresent());
        assertEquals(testClient1.getCustId(), found.get().getCustId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndStatus() {
        // When
        List<Client> activeClients = clientRepository.findByBizIdAndStatusOrderByCreatedOnDesc(123L, ClientStatus.ACTIVE);
        List<Client> inactiveClients = clientRepository.findByBizIdAndStatusOrderByCreatedOnDesc(123L, ClientStatus.INACTIVE);

        // Then
        assertEquals(2, activeClients.size());
        assertEquals(0, inactiveClients.size()); // testClient3 has different bizId
    }

    @Test
    void shouldFindByBizIdAndPhone() {
        // When
        Optional<Client> found = clientRepository.findByBizIdAndPhone(123L, "9876543210");
        Optional<Client> notFound = clientRepository.findByBizIdAndPhone(123L, "1234567890");

        // Then
        assertTrue(found.isPresent());
        assertEquals(testClient1.getCustId(), found.get().getCustId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndEmail() {
        // When
        Optional<Client> found = clientRepository.findByBizIdAndEmail(123L, "jane@example.com");
        Optional<Client> notFound = clientRepository.findByBizIdAndEmail(123L, "notfound@example.com");

        // Then
        assertTrue(found.isPresent());
        assertEquals(testClient2.getCustId(), found.get().getCustId());
        assertFalse(notFound.isPresent());
    }

    @Test
    void shouldFindByBizIdAndNameContaining() {
        // When
        List<Client> johnClients = clientRepository.findByBizIdAndNameContainingIgnoreCaseOrderByName(123L, "john");
        List<Client> janeClients = clientRepository.findByBizIdAndNameContainingIgnoreCaseOrderByName(123L, "Jane");

        // Then
        assertEquals(1, johnClients.size());
        assertEquals(testClient1.getCustId(), johnClients.get(0).getCustId());
        assertEquals(1, janeClients.size());
        assertEquals(testClient2.getCustId(), janeClients.get(0).getCustId());
    }

    @Test
    void shouldFindBusinessClients() {
        // When
        List<Client> businessClients = clientRepository.findByBizIdAndBusinessNameIsNotNullOrderByBusinessName(123L);
        List<Client> retailClients = clientRepository.findByBizIdAndBusinessTypeOrderByBusinessName(123L, "Retail");

        // Then
        assertEquals(1, businessClients.size());
        assertEquals(testClient2.getCustId(), businessClients.get(0).getCustId());
        assertEquals(1, retailClients.size());
        assertEquals(testClient2.getCustId(), retailClients.get(0).getCustId());
    }

    @Test
    void shouldFindByLoyaltyPoints() {
        // When
        List<Client> highPointsClients = clientRepository.findByBizIdAndLoyaltyPointsGreaterThanOrderByLoyaltyPointsDesc(123L, 150);

        // Then
        assertEquals(1, highPointsClients.size());
        assertEquals(testClient2.getCustId(), highPointsClients.get(0).getCustId());
        assertEquals(250, highPointsClients.get(0).getLoyaltyPoints());
    }

    @Test
    void shouldFindByLastVisitDate() {
        // When
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(3);
        List<Client> recentClients = clientRepository.findByBizIdAndLastVisitDateAfterOrderByLastVisitDateDesc(123L, cutoffDate);
        List<Client> oldClients = clientRepository.findByBizIdAndLastVisitDateBeforeOrderByLastVisitDateAsc(123L, cutoffDate);

        // Then
        assertEquals(1, recentClients.size());
        assertEquals(testClient2.getCustId(), recentClients.get(0).getCustId());
        assertEquals(1, oldClients.size());
        assertEquals(testClient1.getCustId(), oldClients.get(0).getCustId());
    }

    @Test
    void shouldFindByTagsContaining() {
        // When
        List<Client> vipClients = clientRepository.findByBizIdAndTagsContaining(123L, "VIP");
        List<Client> premiumClients = clientRepository.findByBizIdAndTagsContaining(123L, "Premium");

        // Then
        assertEquals(1, vipClients.size());
        assertEquals(testClient1.getCustId(), vipClients.get(0).getCustId());
        assertEquals(1, premiumClients.size());
        assertEquals(testClient2.getCustId(), premiumClients.get(0).getCustId());
    }

    @Test
    void shouldFindClientsWithFilters() {
        // When
        Page<Client> allClients = clientRepository.findClientsWithFilters(
            123L, null, null, null, null, null, null, null, PageRequest.of(0, 10));

        Page<Client> activeClients = clientRepository.findClientsWithFilters(
            123L, ClientStatus.ACTIVE, null, null, null, null, null, null, PageRequest.of(0, 10));

        Page<Client> retailClients = clientRepository.findClientsWithFilters(
            123L, null, "Retail", null, null, null, null, null, PageRequest.of(0, 10));

        Page<Client> highPointsClients = clientRepository.findClientsWithFilters(
            123L, null, null, 200, null, null, null, null, PageRequest.of(0, 10));

        Page<Client> searchByName = clientRepository.findClientsWithFilters(
            123L, null, null, null, null, null, null, "John", PageRequest.of(0, 10));

        // Then
        assertEquals(2, allClients.getTotalElements());
        assertEquals(2, activeClients.getTotalElements());
        assertEquals(1, retailClients.getTotalElements());
        assertEquals(testClient2.getCustId(), retailClients.getContent().get(0).getCustId());
        assertEquals(1, highPointsClients.getTotalElements());
        assertEquals(testClient2.getCustId(), highPointsClients.getContent().get(0).getCustId());
        assertEquals(1, searchByName.getTotalElements());
        assertEquals(testClient1.getCustId(), searchByName.getContent().get(0).getCustId());
    }

    @Test
    void shouldCountNewClients() {
        // When
        Long count = clientRepository.countNewClients(123L, LocalDateTime.now().minusDays(1));

        // Then
        assertEquals(2L, count); // Both testClient1 and testClient2 for bizId 123
    }

    @Test
    void shouldCountActiveClients() {
        // When
        Long count = clientRepository.countActiveClients(123L);

        // Then
        assertEquals(2L, count); // Both testClient1 and testClient2 are active
    }

    @Test
    void shouldCountReturningClients() {
        // When
        Long count = clientRepository.countReturningClients(123L, LocalDateTime.now().minusDays(10));

        // Then
        assertEquals(2L, count); // Both clients have recent visits
    }

    @Test
    void shouldGetAverageLoyaltyPoints() {
        // When
        Double average = clientRepository.getAverageLoyaltyPoints(123L);

        // Then
        assertEquals(175.0, average); // (100 + 250) / 2
    }

    @Test
    void shouldGetTotalLoyaltyPoints() {
        // When
        Long total = clientRepository.getTotalLoyaltyPoints(123L);

        // Then
        assertEquals(350L, total); // 100 + 250
    }

    @Test
    void shouldCountClientsByPointsRange() {
        // When
        Long count = clientRepository.countClientsByPointsRange(123L, 100, 200);

        // Then
        assertEquals(1L, count); // Only testClient1 has points in range 100-200
    }

    @Test
    void shouldFindClientsWithNoVisits() {
        // Given
        Client noVisitClient = new Client(123L, "No Visit Client", "9999999999", "novisit@example.com");
        entityManager.persistAndFlush(noVisitClient);

        // When
        List<Client> clientsWithNoVisits = clientRepository.findClientsWithNoVisits(123L);

        // Then
        assertEquals(1, clientsWithNoVisits.size());
        assertEquals(noVisitClient.getCustId(), clientsWithNoVisits.get(0).getCustId());
    }

    @Test
    void shouldFindInactiveClients() {
        // When
        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(10);
        List<Client> inactiveClients = clientRepository.findInactiveClients(123L, cutoffDate);

        // Then
        assertEquals(0, inactiveClients.size()); // No clients older than 10 days for bizId 123
    }

    @Test
    void shouldFindClientsWithLoyaltyPoints() {
        // When
        List<Client> clientsWithPoints = clientRepository.findClientsWithLoyaltyPoints(123L);

        // Then
        assertEquals(2, clientsWithPoints.size());
        // Should be ordered by points descending
        assertEquals(testClient2.getCustId(), clientsWithPoints.get(0).getCustId()); // 250 points
        assertEquals(testClient1.getCustId(), clientsWithPoints.get(1).getCustId()); // 100 points
    }

    @Test
    void shouldCheckExistsByCustIdAndBizId() {
        // When
        boolean exists = clientRepository.existsByCustIdAndBizId(testClient1.getCustId(), 123L);
        boolean notExists = clientRepository.existsByCustIdAndBizId(testClient1.getCustId(), 456L);

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndPhone() {
        // When
        boolean exists = clientRepository.existsByBizIdAndPhone(123L, "9876543210");
        boolean notExists = clientRepository.existsByBizIdAndPhone(123L, "1234567890");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldCheckExistsByBizIdAndEmail() {
        // When
        boolean exists = clientRepository.existsByBizIdAndEmail(123L, "john@example.com");
        boolean notExists = clientRepository.existsByBizIdAndEmail(123L, "notfound@example.com");

        // Then
        assertTrue(exists);
        assertFalse(notExists);
    }

    @Test
    void shouldFindWithPagination() {
        // When
        Page<Client> page = clientRepository.findByBizIdOrderByCreatedOnDesc(123L, PageRequest.of(0, 1));

        // Then
        assertEquals(2, page.getTotalElements());
        assertEquals(2, page.getTotalPages());
        assertEquals(1, page.getContent().size());
    }
}