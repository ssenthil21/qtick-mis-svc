package com.qtick.mis.controller;

import com.qtick.mis.dto.dashboard.*;
import com.qtick.mis.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * REST controller for dashboard related endpoints.
 */
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    /**
     * Get dashboard summary for a period and optional comparison period.
     */
    @GetMapping("/summary")
    public DashboardSummaryDto getSummary(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate comparisonStartDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate comparisonEndDate) {
        return dashboardService.getSummary(startDate, endDate, comparisonStartDate, comparisonEndDate);
    }

    /**
     * Get trend data for a given metric and period.
     */
    @GetMapping("/trends")
    public List<TrendDataDto> getTrends(
            @RequestParam String metric,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam String period) {
        return dashboardService.getTrends(metric, startDate, endDate, period);
    }

    /**
     * Get top performing services in a date range.
     */
    @GetMapping("/top-services")
    public List<TopServiceDto> getTopServices(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "revenue") String sortBy,
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return dashboardService.getTopServices(startDate, endDate, sortBy, limit);
    }

    /**
     * Get top staff performance in a date range.
     */
    @GetMapping("/top-staff")
    public List<TopStaffDto> getTopStaff(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false, defaultValue = "revenue") String sortBy,
            @RequestParam(required = false, defaultValue = "5") Integer limit) {
        return dashboardService.getTopStaff(startDate, endDate, sortBy, limit);
    }

    /**
     * Get business details and daily job statistics for clients of a given type.
     */
    @GetMapping("/business-details")
    public List<BusinessDetailsDto> getBusinessDetails(
            @RequestParam String businessType,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return dashboardService.getBusinessDetails(businessType, date);
    }

    /**
     * Get aggregated business details and view count for a period.
     */
    @GetMapping("/business-view-count")
    public BusinessViewCountDto getBusinessViewCount(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return dashboardService.getBusinessViewCount(startDate, endDate);
    }
}

