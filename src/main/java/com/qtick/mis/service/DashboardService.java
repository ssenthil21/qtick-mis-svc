package com.qtick.mis.service;

import com.qtick.mis.dto.dashboard.BusinessDetailsDto;
import com.qtick.mis.dto.dashboard.DailyJobStatsDto;
import com.qtick.mis.dto.dashboard.DashboardSummaryDto;
import com.qtick.mis.dto.dashboard.BusinessViewCountDto;
import com.qtick.mis.dto.dashboard.ServiceBreakdownDto;
import com.qtick.mis.dto.dashboard.TopServiceDto;
import com.qtick.mis.dto.dashboard.TopStaffDto;
import com.qtick.mis.dto.dashboard.TrendDataDto;
import com.qtick.mis.document.DashboardSnapshot;
import com.qtick.mis.mapper.DashboardMapper;
import com.qtick.mis.repository.jpa.AppointmentRepository;
import com.qtick.mis.repository.jpa.BillRepository;
import com.qtick.mis.repository.jpa.ClientRepository;
import com.qtick.mis.repository.jpa.EnquiryRepository;
import com.qtick.mis.repository.jpa.DayQReportRepository;
import com.qtick.mis.repository.mongo.DashboardSnapshotRepository;
import com.qtick.mis.security.TenantContext;
import com.qtick.mis.security.TenantContextHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DashboardService {

    private static final Logger logger = LoggerFactory.getLogger(DashboardService.class);

    @Value("${app.use-mock-data:false}")
    private boolean useMockData;

    @Autowired
    private MockDashboardService mockDashboardService;
    
    @Autowired
    private DashboardSnapshotRepository snapshotRepository;
    
    @Autowired
    private BillRepository billRepository;
    
    @Autowired
    private EnquiryRepository enquiryRepository;
    
    @Autowired
    private AppointmentRepository appointmentRepository;
    
    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private DashboardMapper dashboardMapper;

    @Autowired
    private DayQReportRepository dayQReportRepository;
    
    /**
     * Get dashboard summary with KPI calculations and comparison logic
     */
    public DashboardSummaryDto getSummary(LocalDate startDate, LocalDate endDate,
                                         LocalDate comparisonStartDate, LocalDate comparisonEndDate) {
        if (useMockData) {
            return mockDashboardService.getSummary(startDate, endDate, comparisonStartDate, comparisonEndDate);
        }
        TenantContext context = TenantContextHolder.getContext();
        Long bizId = context.getBizId();
        
        logger.info("Getting dashboard summary for bizId: {}, period: {} to {}", 
                   bizId, startDate, endDate);
        
        // Try to get from snapshot first
        Optional<DashboardSnapshot> snapshot = snapshotRepository
            .findByBizIdAndSnapshotDateBetween(bizId, startDate, endDate)
            .stream()
            .findFirst();
        
        DashboardSummaryDto summary;
        if (snapshot.isPresent()) {
            logger.debug("Using cached snapshot for dashboard summary");
            summary = dashboardMapper.toSummaryDto(snapshot.get());
        } else {
            logger.debug("Computing dashboard summary on-the-fly");
            summary = computeSummaryFromDatabase(bizId, startDate, endDate);
        }
        
        // Calculate comparison deltas if comparison period provided
        if (comparisonStartDate != null && comparisonEndDate != null) {
            DashboardSummaryDto comparisonSummary = computeSummaryFromDatabase(
                bizId, comparisonStartDate, comparisonEndDate);
            calculateDeltas(summary, comparisonSummary);
        }
        
        summary.setPeriodStart(startDate);
        summary.setPeriodEnd(endDate);
        
        return summary;
    }
    
    /**
     * Get trend data with time series data aggregation
     */
    public List<TrendDataDto> getTrends(String metric, LocalDate startDate, LocalDate endDate, String period) {
        if (useMockData) {
            return mockDashboardService.getTrends(metric, startDate, endDate, period);
        }
        TenantContext context = TenantContextHolder.getContext();
        Long bizId = context.getBizId();
        
        logger.info("Getting trends for metric: {}, bizId: {}, period: {}", metric, bizId, period);
        
        List<DashboardSnapshot> snapshots = snapshotRepository
            .findByBizIdAndSnapshotDateBetweenOrderBySnapshotDateAsc(bizId, startDate, endDate);
        
        List<TrendDataDto> trends = new ArrayList<>();
        
        for (DashboardSnapshot snapshot : snapshots) {
            TrendDataDto trend = new TrendDataDto();
            trend.setDate(snapshot.getSnapshotDate());
            trend.setMetric(metric);
            trend.setPeriod(period);
            
            // Set value based on metric type
            switch (metric.toLowerCase()) {
                case "grosssales":
                    trend.setValue(snapshot.getGrossSales());
                    break;
                case "netsales":
                    trend.setValue(snapshot.getNetSales());
                    break;
                case "bills":
                    trend.setValue(new BigDecimal(snapshot.getBills()));
                    break;
                case "avgbill":
                    trend.setValue(snapshot.getAvgBill());
                    break;
                case "newleads":
                    trend.setValue(new BigDecimal(snapshot.getNewLeads()));
                    break;
                case "totalleads":
                    trend.setValue(new BigDecimal(snapshot.getTotalLeads()));
                    break;
                case "appointments":
                    trend.setValue(new BigDecimal(snapshot.getAppointments()));
                    break;
                case "returningcustomers":
                    trend.setValue(new BigDecimal(snapshot.getReturningCustomers()));
                    break;
                default:
                    trend.setValue(BigDecimal.ZERO);
            }
            
            trends.add(trend);
        }
        
        return trends;
    }
    
    /**
     * Get top services with ranking algorithms
     */
    public List<TopServiceDto> getTopServices(LocalDate startDate, LocalDate endDate,
                                            String sortBy, Integer limit) {
        if (useMockData) {
            return mockDashboardService.getTopServices(startDate, endDate, sortBy, limit);
        }
        TenantContext context = TenantContextHolder.getContext();
        Long bizId = context.getBizId();
        
        logger.info("Getting top services for bizId: {}, sortBy: {}, limit: {}", bizId, sortBy, limit);
        
        // Get service performance data from bills
        List<Object[]> serviceData = billRepository.findTopServicesByRevenue(
            bizId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay(), limit);
        
        List<TopServiceDto> topServices = new ArrayList<>();
        BigDecimal totalRevenue = getTotalRevenue(bizId, startDate, endDate);
        
        int rank = 1;
        for (Object[] data : serviceData) {
            TopServiceDto service = new TopServiceDto();
            service.setServiceId((Long) data[0]);
            service.setServiceName((String) data[1]);
            service.setRevenue((BigDecimal) data[2]);
            service.setJobs(((Number) data[3]).intValue());
            
            // Calculate contribution percentage
            if (totalRevenue.compareTo(BigDecimal.ZERO) > 0) {
                BigDecimal contribution = service.getRevenue()
                    .divide(totalRevenue, 4, RoundingMode.HALF_UP)
                    .multiply(new BigDecimal("100"));
                service.setContribution(contribution);
            } else {
                service.setContribution(BigDecimal.ZERO);
            }
            
            service.setRank(rank++);
            topServices.add(service);
        }
        
        return topServices;
    }
    
    /**
     * Get top staff with performance metrics
     */
    public List<TopStaffDto> getTopStaff(LocalDate startDate, LocalDate endDate,
                                       String sortBy, Integer limit) {
        if (useMockData) {
            return mockDashboardService.getTopStaff(startDate, endDate, sortBy, limit);
        }
        TenantContext context = TenantContextHolder.getContext();
        Long bizId = context.getBizId();
        
        logger.info("Getting top staff for bizId: {}, sortBy: {}, limit: {}", bizId, sortBy, limit);
        
        // Get staff performance data from appointments and bills
        List<Object[]> staffData = appointmentRepository.findTopStaffByRevenue(
            bizId, startDate.atStartOfDay(), endDate.plusDays(1).atStartOfDay(), limit);
        
        List<TopStaffDto> topStaff = new ArrayList<>();
        
        int rank = 1;
        for (Object[] data : staffData) {
            TopStaffDto staff = new TopStaffDto();
            staff.setStaffId((Long) data[0]);
            staff.setStaffName((String) data[1]);
            staff.setRevenue((BigDecimal) data[2]);
            staff.setJobs(((Number) data[3]).intValue());
            staff.setRating((BigDecimal) data[4]);
            staff.setRank(rank++);
            topStaff.add(staff);
        }
        
        return topStaff;
    }
    
    /**
     * Get business details with daily job statistics
     */
    public List<BusinessDetailsDto> getBusinessDetails(String businessType, LocalDate date) {
        if (useMockData) {
            return mockDashboardService.getBusinessDetails(businessType, date);
        }
        TenantContext context = TenantContextHolder.getContext();
        Long bizId = context.getBizId();
        
        logger.info("Getting business details for bizId: {}, businessType: {}, date: {}", 
                   bizId, businessType, date);
        
        // Get business clients filtered by business type
        List<Object[]> businessData = clientRepository.findBusinessClientsByType(bizId, businessType);
        
        List<BusinessDetailsDto> businessDetails = new ArrayList<>();
        
        for (Object[] data : businessData) {
            BusinessDetailsDto business = new BusinessDetailsDto();
            business.setCustId((Long) data[0]);
            business.setBusinessName((String) data[1]);
            business.setBusinessType((String) data[2]);
            business.setContactPerson((String) data[3]);
            business.setPhone((String) data[4]);
            business.setEmail((String) data[5]);
            business.setLastVisit((LocalDate) data[6]);
            
            // Get daily job statistics for this business
            DailyJobStatsDto dailyStats = getDailyJobStats(bizId, business.getCustId(), date);
            business.setDailyStats(dailyStats);
            
            businessDetails.add(business);
        }
        
        return businessDetails;
    }

    public BusinessViewCountDto getBusinessViewCount(LocalDate startDate, LocalDate endDate) {
        if (useMockData) {
            return mockDashboardService.getBusinessViewCount(startDate, endDate);
        }
        TenantContext context = TenantContextHolder.getContext();
        Long bizId = context.getBizId();

        logger.info("Getting business view count for bizId: {}, period: {} to {}", bizId, startDate, endDate);

        int startPeriod = toPeriodId(startDate);
        int endPeriod = toPeriodId(endDate);

        Object[] stats = dayQReportRepository.aggregateStats(bizId, "D", startPeriod, endPeriod);

        BusinessViewCountDto dto = new BusinessViewCountDto();
        dto.setBizId(bizId);
        dto.setStartDate(startDate);
        dto.setEndDate(endDate);

        if (stats != null) {
            dto.setServed(((Number) stats[0]).intValue());
            dto.setSales(stats[1] != null ? ((Number) stats[1]).doubleValue() : 0.0);
            dto.setNetSales(stats[2] != null ? ((Number) stats[2]).doubleValue() : 0.0);
            dto.setQueued(((Number) stats[3]).intValue());
            dto.setMissed(((Number) stats[4]).intValue());
            dto.setLeftQ(((Number) stats[5]).intValue());
            dto.setCancelled(((Number) stats[6]).intValue());
            dto.setViewCount(((Number) stats[7]).intValue());
        }

        return dto;
    }

    private int toPeriodId(LocalDate date) {
        return Integer.parseInt(date.format(java.time.format.DateTimeFormatter.BASIC_ISO_DATE));
    }
    
    /**
     * Compute dashboard summary from database when snapshot not available
     */
    private DashboardSummaryDto computeSummaryFromDatabase(Long bizId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        
        // Calculate KPIs from database
        BigDecimal grossSales = billRepository.sumTotalAmountByBizIdAndDateRange(bizId, start, end);
        BigDecimal netSales = billRepository.sumPaidAmountByBizIdAndDateRange(bizId, start, end);
        Integer bills = billRepository.countByBizIdAndDateRange(bizId, start, end);
        Integer newLeads = enquiryRepository.countNewEnquiriesByBizIdAndDateRange(bizId, start, end);
        Integer totalLeads = enquiryRepository.countByBizIdAndDateRange(bizId, start, end);
        Integer missedLeads = enquiryRepository.countMissedEnquiriesByBizIdAndDateRange(bizId, start, end);
        Integer appointments = appointmentRepository.countByBizIdAndDateRange(bizId, start, end);
        Integer returningCustomers = clientRepository.countReturningCustomersByBizIdAndDateRange(bizId, start, end);
        
        // Calculate average bill
        BigDecimal avgBill = BigDecimal.ZERO;
        if (bills > 0 && grossSales != null) {
            avgBill = grossSales.divide(new BigDecimal(bills), 2, RoundingMode.HALF_UP);
        }
        
        // Handle null values
        grossSales = grossSales != null ? grossSales : BigDecimal.ZERO;
        netSales = netSales != null ? netSales : BigDecimal.ZERO;
        bills = bills != null ? bills : 0;
        newLeads = newLeads != null ? newLeads : 0;
        totalLeads = totalLeads != null ? totalLeads : 0;
        missedLeads = missedLeads != null ? missedLeads : 0;
        appointments = appointments != null ? appointments : 0;
        returningCustomers = returningCustomers != null ? returningCustomers : 0;
        
        return new DashboardSummaryDto(grossSales, netSales, bills, avgBill, 
                                     newLeads, totalLeads, missedLeads, 
                                     appointments, returningCustomers);
    }
    
    /**
     * Calculate percentage deltas between current and comparison periods
     */
    private void calculateDeltas(DashboardSummaryDto current, DashboardSummaryDto comparison) {
        current.setGrossSalesDelta(calculatePercentageDelta(current.getGrossSales(), comparison.getGrossSales()));
        current.setNetSalesDelta(calculatePercentageDelta(current.getNetSales(), comparison.getNetSales()));
        current.setBillsDelta(calculatePercentageDelta(new BigDecimal(current.getBills()), new BigDecimal(comparison.getBills())));
        current.setAvgBillDelta(calculatePercentageDelta(current.getAvgBill(), comparison.getAvgBill()));
        current.setNewLeadsDelta(calculatePercentageDelta(new BigDecimal(current.getNewLeads()), new BigDecimal(comparison.getNewLeads())));
        current.setTotalLeadsDelta(calculatePercentageDelta(new BigDecimal(current.getTotalLeads()), new BigDecimal(comparison.getTotalLeads())));
        current.setMissedLeadsDelta(calculatePercentageDelta(new BigDecimal(current.getMissedLeads()), new BigDecimal(comparison.getMissedLeads())));
        current.setAppointmentsDelta(calculatePercentageDelta(new BigDecimal(current.getAppointments()), new BigDecimal(comparison.getAppointments())));
        current.setReturningCustomersDelta(calculatePercentageDelta(new BigDecimal(current.getReturningCustomers()), new BigDecimal(comparison.getReturningCustomers())));
    }
    
    /**
     * Calculate percentage delta between two values
     */
    private BigDecimal calculatePercentageDelta(BigDecimal current, BigDecimal previous) {
        if (previous.compareTo(BigDecimal.ZERO) == 0) {
            return current.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : new BigDecimal("100");
        }
        
        return current.subtract(previous)
                     .divide(previous, 4, RoundingMode.HALF_UP)
                     .multiply(new BigDecimal("100"));
    }
    
    /**
     * Get total revenue for contribution calculations
     */
    private BigDecimal getTotalRevenue(Long bizId, LocalDate startDate, LocalDate endDate) {
        LocalDateTime start = startDate.atStartOfDay();
        LocalDateTime end = endDate.plusDays(1).atStartOfDay();
        
        BigDecimal total = billRepository.sumTotalAmountByBizIdAndDateRange(bizId, start, end);
        return total != null ? total : BigDecimal.ZERO;
    }
    
    /**
     * Get daily job statistics for a specific business client
     */
    private DailyJobStatsDto getDailyJobStats(Long bizId, Long custId, LocalDate date) {
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.plusDays(1).atStartOfDay();
        
        // Get daily statistics for this client
        Object[] stats = appointmentRepository.findDailyJobStatsByClient(bizId, custId, start, end);
        
        DailyJobStatsDto dailyStats = new DailyJobStatsDto();
        dailyStats.setDate(date);
        
        if (stats != null) {
            dailyStats.setTotalAppointments(((Number) stats[0]).intValue());
            dailyStats.setTotalWalkIns(((Number) stats[1]).intValue());
            dailyStats.setTotalBills(((Number) stats[2]).intValue());
            dailyStats.setTotalRevenue((BigDecimal) stats[3]);
        } else {
            dailyStats.setTotalAppointments(0);
            dailyStats.setTotalWalkIns(0);
            dailyStats.setTotalBills(0);
            dailyStats.setTotalRevenue(BigDecimal.ZERO);
        }
        
        // Get service breakdown
        List<Object[]> serviceBreakdown = billRepository.findServiceBreakdownByClient(bizId, custId, start, end);
        List<ServiceBreakdownDto> breakdown = new ArrayList<>();
        
        for (Object[] service : serviceBreakdown) {
            ServiceBreakdownDto serviceDto = new ServiceBreakdownDto();
            serviceDto.setServiceName((String) service[0]);
            serviceDto.setAppointmentCount(((Number) service[1]).intValue());
            serviceDto.setWalkInCount(((Number) service[2]).intValue());
            serviceDto.setBillCount(((Number) service[3]).intValue());
            serviceDto.setRevenue((BigDecimal) service[4]);
            breakdown.add(serviceDto);
        }
        
        dailyStats.setServiceBreakdown(breakdown);
        
        return dailyStats;
    }
}