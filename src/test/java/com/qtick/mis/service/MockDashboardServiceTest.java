package com.qtick.mis.service;

import com.qtick.mis.dto.dashboard.*;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for {@link MockDashboardService} and the mock data toggle in {@link DashboardService}.
 */
public class MockDashboardServiceTest {

    @Test
    void getSummaryReturnsMockData() {
        MockDashboardService service = new MockDashboardService();
        DashboardSummaryDto dto = service.getSummary(LocalDate.now(), LocalDate.now(), null, null);
        assertEquals(new BigDecimal("12500.00"), dto.getGrossSales());
        assertEquals(150, dto.getBills());
    }

    @Test
    void getTopServicesReturnsMockData() {
        MockDashboardService service = new MockDashboardService();
        List<TopServiceDto> services = service.getTopServices(LocalDate.now().minusDays(2), LocalDate.now(), "revenue", 5);
        assertEquals(3, services.size());
        assertEquals("Consultation", services.get(0).getServiceName());
    }

    @Test
    void businessDetailsIncludeServiceBreakdown() {
        MockDashboardService service = new MockDashboardService();
        List<BusinessDetailsDto> details = service.getBusinessDetails("Healthcare", LocalDate.now());
        assertFalse(details.isEmpty());
        DailyJobStatsDto stats = details.get(0).getDailyStats();
        assertNotNull(stats);
        assertEquals(2, stats.getServiceBreakdown().size());
    }

    @Test
    void getBusinessViewCountReturnsMockData() {
        MockDashboardService service = new MockDashboardService();
        BusinessViewCountDto dto = service.getBusinessViewCount(LocalDate.now().minusDays(2), LocalDate.now());
        assertEquals(120, dto.getServed());
        assertEquals(150, dto.getViewCount());
    }

    @Test
    void dashboardServiceUsesMockWhenEnabled() {
        MockDashboardService mockService = new MockDashboardService();
        DashboardService service = new DashboardService();
        ReflectionTestUtils.setField(service, "useMockData", true);
        ReflectionTestUtils.setField(service, "mockDashboardService", mockService);

        DashboardSummaryDto dto = service.getSummary(LocalDate.now(), LocalDate.now(), null, null);
        assertEquals(new BigDecimal("12500.00"), dto.getGrossSales());

        BusinessViewCountDto viewDto = service.getBusinessViewCount(LocalDate.now().minusDays(2), LocalDate.now());
        assertEquals(120, viewDto.getServed());
    }
}
