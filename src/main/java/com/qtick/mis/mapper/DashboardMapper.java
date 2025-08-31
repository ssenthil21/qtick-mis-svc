package com.qtick.mis.mapper;

import com.qtick.mis.dto.dashboard.DashboardSummaryDto;
import com.qtick.mis.dto.dashboard.TrendDataDto;
import com.qtick.mis.document.DashboardSnapshot;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface DashboardMapper {
    
    /**
     * Convert DashboardSnapshot document to DashboardSummaryDto
     */
    @Mapping(target = "periodStart", source = "snapshotDate")
    @Mapping(target = "periodEnd", source = "snapshotDate")
    @Mapping(target = "grossSalesDelta", ignore = true) // Will be calculated by service layer
    @Mapping(target = "netSalesDelta", ignore = true)
    @Mapping(target = "billsDelta", ignore = true)
    @Mapping(target = "avgBillDelta", ignore = true)
    @Mapping(target = "newLeadsDelta", ignore = true)
    @Mapping(target = "totalLeadsDelta", ignore = true)
    @Mapping(target = "missedLeadsDelta", ignore = true)
    @Mapping(target = "appointmentsDelta", ignore = true)
    @Mapping(target = "returningCustomersDelta", ignore = true)
    DashboardSummaryDto toSummaryDto(DashboardSnapshot snapshot);
    
    /**
     * Convert DashboardSnapshot to TrendDataDto for specific metric
     */
    @Mapping(target = "date", source = "snapshotDate")
    @Mapping(target = "metric", ignore = true) // Will be set by service layer
    @Mapping(target = "value", ignore = true) // Will be set by service layer based on metric
    @Mapping(target = "period", source = "period")
    TrendDataDto toTrendDataDto(DashboardSnapshot snapshot);
    
    /**
     * Convert list of DashboardSnapshot documents to TrendDataDto list
     */
    List<TrendDataDto> toTrendDataDtos(List<DashboardSnapshot> snapshots);
}