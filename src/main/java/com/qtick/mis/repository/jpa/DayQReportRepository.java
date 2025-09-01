package com.qtick.mis.repository.jpa;

import com.qtick.mis.entity.DayQReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DayQReportRepository extends JpaRepository<DayQReport, Long> {

    @Query("SELECT SUM(r.served), SUM(r.sales), SUM(r.netSales), SUM(r.queued), SUM(r.missed), SUM(r.leftQ), SUM(r.cancelled), SUM(r.peeped) " +
            "FROM DayQReport r WHERE r.bizId = :bizId AND r.periodType = :periodType AND r.periodId BETWEEN :startPeriod AND :endPeriod")
    Object[] aggregateStats(@Param("bizId") Long bizId,
                            @Param("periodType") String periodType,
                            @Param("startPeriod") Integer startPeriod,
                            @Param("endPeriod") Integer endPeriod);

    @Query("SELECT r.periodId, SUM(r.peeped) FROM DayQReport r WHERE r.bizId = :bizId AND r.periodType = :periodType AND r.periodId BETWEEN :startPeriod AND :endPeriod GROUP BY r.periodId ORDER BY r.periodId")
    List<Object[]> findDailyViewCounts(@Param("bizId") Long bizId,
                                       @Param("periodType") String periodType,
                                       @Param("startPeriod") Integer startPeriod,
                                       @Param("endPeriod") Integer endPeriod);
}
