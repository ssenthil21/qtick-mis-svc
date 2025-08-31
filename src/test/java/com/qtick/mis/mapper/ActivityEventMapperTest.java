package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.TimelineEventDto;
import com.qtick.mis.document.ActivityEvent;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class ActivityEventMapperTest {
    
    private final ActivityEventMapper mapper = Mappers.getMapper(ActivityEventMapper.class);
    
    @Test
    void shouldMapActivityEventToTimelineEventDto() {
        // Given
        ActivityEvent event = createTestActivityEvent();
        
        // When
        TimelineEventDto dto = mapper.toTimelineEventDto(event);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(event.getId());
        assertThat(dto.getEventType()).isEqualTo(event.getEventType());
        assertThat(dto.getEntityType()).isEqualTo(event.getEntityType());
        assertThat(dto.getEntityId()).isEqualTo(event.getEntityId());
        assertThat(dto.getDescription()).isEqualTo(event.getDescription());
        assertThat(dto.getCreatedAt()).isEqualTo(event.getCreatedAt());
        assertThat(dto.getUserId()).isEqualTo(event.getUserId());
        assertThat(dto.getUserRole()).isEqualTo(event.getUserRole());
        assertThat(dto.getMetadata()).isEqualTo(event.getMetadata());
    }
    
    @Test
    void shouldMapActivityEventList() {
        // Given
        List<ActivityEvent> events = Arrays.asList(createTestActivityEvent(), createTestActivityEvent());
        
        // When
        List<TimelineEventDto> dtos = mapper.toTimelineEventDtos(events);
        
        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getEventType()).isEqualTo("ENQUIRY_CREATED");
    }
    
    @Test
    void shouldMapTimelineEventDtoToActivityEvent() {
        // Given
        TimelineEventDto dto = new TimelineEventDto();
        dto.setId("test-id");
        dto.setEventType("ENQUIRY_CREATED");
        dto.setEntityType("ENQUIRY");
        dto.setEntityId(1L);
        dto.setDescription("New enquiry created");
        dto.setCreatedAt(LocalDateTime.now());
        dto.setUserId(1L);
        dto.setUserRole("SALES_REP");
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("source", "website");
        dto.setMetadata(metadata);
        
        // When
        ActivityEvent event = mapper.toDocument(dto);
        
        // Then
        assertThat(event).isNotNull();
        assertThat(event.getId()).isNull(); // Should be ignored for new documents
        assertThat(event.getEventType()).isEqualTo(dto.getEventType());
        assertThat(event.getEntityType()).isEqualTo(dto.getEntityType());
        assertThat(event.getEntityId()).isEqualTo(dto.getEntityId());
        assertThat(event.getDescription()).isEqualTo(dto.getDescription());
        assertThat(event.getUserId()).isEqualTo(dto.getUserId());
        assertThat(event.getUserRole()).isEqualTo(dto.getUserRole());
        assertThat(event.getMetadata()).isEqualTo(dto.getMetadata());
    }
    
    private ActivityEvent createTestActivityEvent() {
        ActivityEvent event = new ActivityEvent();
        event.setId("test-id");
        event.setBizId(100L);
        event.setBranchId(1L);
        event.setEventType("ENQUIRY_CREATED");
        event.setEntityType("ENQUIRY");
        event.setEntityId(1L);
        event.setDescription("New enquiry created");
        event.setCreatedAt(LocalDateTime.now());
        event.setUserId(1L);
        event.setUserRole("SALES_REP");
        
        Map<String, Object> metadata = new HashMap<>();
        metadata.put("source", "website");
        event.setMetadata(metadata);
        
        return event;
    }
}