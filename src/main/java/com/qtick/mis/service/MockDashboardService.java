package com.qtick.mis.service;

import com.qtick.mis.dto.dashboard.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * Provides in-memory mock data for dashboard endpoints when database access is disabled.
 */
@Service
public class MockDashboardService {

    private final DashboardSummaryDto summary;
    private final List<TrendDataDto> trends;
    private final List<TopServiceDto> topServices;
    private final List<TopStaffDto> topStaff;
    private final List<BusinessDetailsDto> businessDetails;
    private final BusinessViewCountDto viewCount;

    public MockDashboardService() {
        summary = new DashboardSummaryDto(
                new BigDecimal("12500.00"),
                new BigDecimal("11250.00"),
                150,
                new BigDecimal("83.33"),
                25,
                200,
                12,
                75,
                40
        );

        trends = List.of(
                new TrendDataDto(LocalDate.now().minusDays(2), "grosssales", new BigDecimal("3000"), "day"),
                new TrendDataDto(LocalDate.now().minusDays(1), "grosssales", new BigDecimal("4000"), "day"),
                new TrendDataDto(LocalDate.now(), "grosssales", new BigDecimal("5000"), "day")
        );

        TopServiceDto service1 = new TopServiceDto(1L, "Consultation", new BigDecimal("3000"), 30, new BigDecimal("100"));
        service1.setRank(1);
        TopServiceDto service2 = new TopServiceDto(2L, "Therapy Session", new BigDecimal("2500"), 25, new BigDecimal("100"));
        service2.setRank(2);
        TopServiceDto service3 = new TopServiceDto(3L, "Medication", new BigDecimal("2000"), 20, new BigDecimal("100"));
        service3.setRank(3);
        topServices = List.of(service1, service2, service3);

        TopStaffDto staff1 = new TopStaffDto(1L, "Alice", new BigDecimal("3500"), 35, new BigDecimal("4.9"));
        staff1.setRank(1);
        TopStaffDto staff2 = new TopStaffDto(2L, "Bob", new BigDecimal("3000"), 30, new BigDecimal("4.7"));
        staff2.setRank(2);
        TopStaffDto staff3 = new TopStaffDto(3L, "Charlie", new BigDecimal("2500"), 25, new BigDecimal("4.5"));
        staff3.setRank(3);
        topStaff = List.of(staff1, staff2, staff3);

        List<ServiceBreakdownDto> breakdown1 = List.of(
                new ServiceBreakdownDto("Consultation", 10, 5, 12, new BigDecimal("1200")),
                new ServiceBreakdownDto("Therapy Session", 8, 4, 10, new BigDecimal("1000"))
        );
        DailyJobStatsDto stats1 = new DailyJobStatsDto(LocalDate.now(), 15, 10, 20, new BigDecimal("2200"));
        stats1.setServiceBreakdown(breakdown1);
        BusinessDetailsDto business1 = new BusinessDetailsDto(101L, "Acme Clinic", "Healthcare");
        business1.setContactPerson("Dr. Jane");
        business1.setPhone("123456789");
        business1.setEmail("jane@acme.com");
        business1.setLastVisit(LocalDate.now().minusDays(1));
        business1.setDailyStats(stats1);

        List<ServiceBreakdownDto> breakdown2 = List.of(
                new ServiceBreakdownDto("Medication", 5, 3, 7, new BigDecimal("700")),
                new ServiceBreakdownDto("Consultation", 6, 2, 8, new BigDecimal("800"))
        );
        DailyJobStatsDto stats2 = new DailyJobStatsDto(LocalDate.now(), 12, 6, 15, new BigDecimal("1500"));
        stats2.setServiceBreakdown(breakdown2);
        BusinessDetailsDto business2 = new BusinessDetailsDto(102L, "Globex Pharmacy", "Retail");
        business2.setContactPerson("Mr. Smith");
        business2.setPhone("987654321");
        business2.setEmail("smith@globex.com");
        business2.setLastVisit(LocalDate.now().minusDays(3));
        business2.setDailyStats(stats2);

        businessDetails = List.of(business1, business2);

        viewCount = new BusinessViewCountDto();
        viewCount.setBizId(1L);
        viewCount.setStartDate(LocalDate.now().minusDays(2));
        viewCount.setEndDate(LocalDate.now());
        viewCount.setServed(120);
        viewCount.setSales(5000.0);
        viewCount.setNetSales(4500.0);
        viewCount.setQueued(30);
        viewCount.setMissed(5);
        viewCount.setLeftQ(2);
        viewCount.setCancelled(1);
        viewCount.setViewCount(150);
    }

    public DashboardSummaryDto getSummary(LocalDate startDate, LocalDate endDate,
                                          LocalDate comparisonStartDate, LocalDate comparisonEndDate) {
        return summary;
    }

    public List<TrendDataDto> getTrends(String metric, LocalDate startDate, LocalDate endDate, String period) {
        return trends;
    }

    public List<TopServiceDto> getTopServices(LocalDate startDate, LocalDate endDate,
                                              String sortBy, Integer limit) {
        return topServices;
    }

    public List<TopStaffDto> getTopStaff(LocalDate startDate, LocalDate endDate,
                                         String sortBy, Integer limit) {
        return topStaff;
    }

    public List<BusinessDetailsDto> getBusinessDetails(String businessType, LocalDate date) {
        return businessDetails;
    }

    public BusinessViewCountDto getBusinessViewCount(LocalDate startDate, LocalDate endDate) {
        return viewCount;
    }
}
