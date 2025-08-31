package com.qtick.mis.mapper;

import com.qtick.mis.dto.dashboard.DashboardSummaryDto;
import com.qtick.mis.dto.dashboard.TrendDataDto;
import com.qtick.mis.document.DashboardSnapshot;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class DashboardMapperTest {
    
    private final DashboardMapper mapper = Mappers.getMapper(DashboardMapper.class);
    
    @Test
    void shouldMapDashboardSnapshotToSummaryDto() {
        // Given
        DashboardSnapshot snapshot = createTestSnapshot();
        
        // When
        DashboardSummaryDto dto = mapper.toSummaryDto(snapshot);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getGrossSales()).isEqualTo(snapshot.getGrossSales());
        assertThat(dto.getNetSales()).isEqualTo(snapshot.getNetSales());
        assertThat(dto.getBills()).isEqualTo(snapshot.getBills());
        assertThat(dto.getAvgBill()).isEqualTo(snapshot.getAvgBill());
        assertThat(dto.getNewLeads()).isEqualTo(snapshot.getNewLeads());
        assertThat(dto.getTotalLeads()).isEqualTo(snapshot.getTotalLeads());
        assertThat(dto.getMissedLeads()).isEqualTo(snapshot.getMissedLeads());
        assertThat(dto.getAppointments()).isEqualTo(snapshot.getAppointments());
        assertThat(dto.getReturningCustomers()).isEqualTo(snapshot.getReturningCustomers());
        assertThat(dto.getPeriodStart()).isEqualTo(snapshot.getSnapshotDate());
        assertThat(dto.getPeriodEnd()).isEqualTo(snapshot.getSnapshotDate());
    }
    
    @Test
    void shouldMapDashboardSnapshotToTrendDataDto() {
        // Given
        DashboardSnapshot snapshot = createTestSnapshot();
        
        // When
        TrendDataDto dto = mapper.toTrendDataDto(snapshot);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getDate()).isEqualTo(snapshot.getSnapshotDate());
        assertThat(dto.getPeriod()).isEqualTo(snapshot.getPeriod());
        // metric and value should be set by service layer
    }
    
    @Test
    void shouldMapSnapshotList() {
        // Given
        List<DashboardSnapshot> snapshots = Arrays.asList(createTestSnapshot(), createTestSnapshot());
        
        // When
        List<TrendDataDto> dtos = mapper.toTrendDataDtos(snapshots);
        
        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getPeriod()).isEqualTo("daily");
    }
    
    private DashboardSnapshot createTestSnapshot() {
        DashboardSnapshot snapshot = new DashboardSnapshot();
        snapshot.setId("test-id");
        snapshot.setBizId(100L);
        snapshot.setBranchId(1L);
        snapshot.setSnapshotDate(LocalDate.now());
        snapshot.setPeriod("daily");
        snapshot.setGrossSales(new BigDecimal("1000.00"));
        snapshot.setNetSales(new BigDecimal("900.00"));
        snapshot.setBills(10);
        snapshot.setAvgBill(new BigDecimal("90.00"));
        snapshot.setNewLeads(5);
        snapshot.setTotalLeads(15);
        snapshot.setMissedLeads(2);
        snapshot.setAppointments(8);
        snapshot.setReturningCustomers(3);
        snapshot.setCreatedAt(LocalDateTime.now());
        return snapshot;
    }
}