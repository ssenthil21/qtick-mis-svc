package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.TimelineEventDto;
import com.qtick.mis.document.ActivityEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ActivityEventMapper {
    
    /**
     * Convert ActivityEvent document to TimelineEventDto
     */
    @Mapping(target = "userName", ignore = true) // Will be populated by service layer
    TimelineEventDto toTimelineEventDto(ActivityEvent event);
    
    /**
     * Convert list of ActivityEvent documents to TimelineEventDto list
     */
    List<TimelineEventDto> toTimelineEventDtos(List<ActivityEvent> events);
    
    /**
     * Convert TimelineEventDto to ActivityEvent document (for creating events)
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bizId", ignore = true) // Will be set from tenant context
    @Mapping(target = "branchId", ignore = true) // Will be set from tenant context
    @Mapping(target = "createdAt", ignore = true) // Will be set automatically
    ActivityEvent toDocument(TimelineEventDto dto);
}