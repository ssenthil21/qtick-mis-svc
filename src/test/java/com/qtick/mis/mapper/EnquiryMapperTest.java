package com.qtick.mis.mapper;

import com.qtick.mis.dto.pipeline.CreateEnquiryDto;
import com.qtick.mis.dto.pipeline.EnquiryDetailDto;
import com.qtick.mis.dto.pipeline.EnquiryDto;
import com.qtick.mis.dto.pipeline.EnquiryThreadDto;
import com.qtick.mis.dto.pipeline.UpdateEnquiryDto;
import com.qtick.mis.entity.Enquiry;
import com.qtick.mis.entity.EnquiryStage;
import com.qtick.mis.entity.EnquiryStatus;
import com.qtick.mis.entity.EnquiryThread;
import com.qtick.mis.entity.EnquiryType;
import com.qtick.mis.entity.ThreadType;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class EnquiryMapperTest {
    
    private final EnquiryMapper mapper = Mappers.getMapper(EnquiryMapper.class);
    
    @Test
    void shouldMapEnquiryToDto() {
        // Given
        Enquiry enquiry = createTestEnquiry();
        
        // When
        EnquiryDto dto = mapper.toDto(enquiry);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(enquiry.getId());
        assertThat(dto.getName()).isEqualTo(enquiry.getName());
        assertThat(dto.getPhone()).isEqualTo(enquiry.getPhone());
        assertThat(dto.getEmail()).isEqualTo(enquiry.getEmail());
        assertThat(dto.getStage()).isEqualTo(enquiry.getStage());
        assertThat(dto.getStatus()).isEqualTo(enquiry.getStatus());
    }
    
    @Test
    void shouldMapEnquiryToDetailDto() {
        // Given
        Enquiry enquiry = createTestEnquiry();
        EnquiryThread thread = createTestThread();
        enquiry.setThreads(Arrays.asList(thread));
        
        // When
        EnquiryDetailDto dto = mapper.toDetailDto(enquiry);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(enquiry.getId());
        assertThat(dto.getName()).isEqualTo(enquiry.getName());
        assertThat(dto.getThreads()).hasSize(1);
        assertThat(dto.getThreads().get(0).getMessage()).isEqualTo(thread.getMessage());
    }
    
    @Test
    void shouldMapCreateDtoToEntity() {
        // Given
        CreateEnquiryDto createDto = new CreateEnquiryDto();
        createDto.setName("John Doe");
        createDto.setPhone("+1234567890");
        createDto.setEmail("john@example.com");
        createDto.setEnqType(EnquiryType.SERVICE);
        createDto.setStage(EnquiryStage.NEW);
        createDto.setStatus(EnquiryStatus.OPEN);
        
        // When
        Enquiry entity = mapper.toEntity(createDto);
        
        // Then
        assertThat(entity).isNotNull();
        assertThat(entity.getId()).isNull(); // Should be ignored
        assertThat(entity.getName()).isEqualTo(createDto.getName());
        assertThat(entity.getPhone()).isEqualTo(createDto.getPhone());
        assertThat(entity.getEmail()).isEqualTo(createDto.getEmail());
        assertThat(entity.getEnqType()).isEqualTo(createDto.getEnqType());
        assertThat(entity.getStage()).isEqualTo(createDto.getStage());
        assertThat(entity.getStatus()).isEqualTo(createDto.getStatus());
    }
    
    @Test
    void shouldUpdateEntityFromUpdateDto() {
        // Given
        Enquiry existingEnquiry = createTestEnquiry();
        UpdateEnquiryDto updateDto = new UpdateEnquiryDto();
        updateDto.setStage(EnquiryStage.QUALIFIED);
        updateDto.setStatus(EnquiryStatus.IN_PROGRESS);
        updateDto.setNextAction("Follow up call");
        
        // When
        mapper.updateEntity(updateDto, existingEnquiry);
        
        // Then
        assertThat(existingEnquiry.getStage()).isEqualTo(EnquiryStage.QUALIFIED);
        assertThat(existingEnquiry.getStatus()).isEqualTo(EnquiryStatus.IN_PROGRESS);
        assertThat(existingEnquiry.getNextAction()).isEqualTo("Follow up call");
        // Original values should remain unchanged
        assertThat(existingEnquiry.getName()).isEqualTo("Test Enquiry");
        assertThat(existingEnquiry.getEnqType()).isEqualTo(EnquiryType.SERVICE);
    }
    
    @Test
    void shouldMapThreadToDto() {
        // Given
        EnquiryThread thread = createTestThread();
        
        // When
        EnquiryThreadDto dto = mapper.toThreadDto(thread);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getId()).isEqualTo(thread.getId());
        assertThat(dto.getEnquiryId()).isEqualTo(thread.getEnquiry().getId());
        assertThat(dto.getThreadType()).isEqualTo(thread.getThreadType());
        assertThat(dto.getMessage()).isEqualTo(thread.getMessage());
        assertThat(dto.getUserId()).isEqualTo(thread.getUserId());
    }
    
    @Test
    void shouldMapEnquiryList() {
        // Given
        List<Enquiry> enquiries = Arrays.asList(createTestEnquiry(), createTestEnquiry());
        
        // When
        List<EnquiryDto> dtos = mapper.toDtos(enquiries);
        
        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getName()).isEqualTo("Test Enquiry");
    }
    
    private Enquiry createTestEnquiry() {
        Enquiry enquiry = new Enquiry();
        enquiry.setId(1L);
        enquiry.setBizId(100L);
        enquiry.setName("Test Enquiry");
        enquiry.setPhone("+1234567890");
        enquiry.setEmail("test@example.com");
        enquiry.setEnqType(EnquiryType.SERVICE);
        enquiry.setStage(EnquiryStage.NEW);
        enquiry.setStatus(EnquiryStatus.OPEN);
        enquiry.setSource("Website");
        enquiry.setChannel("Online");
        enquiry.setCreatedOn(LocalDateTime.now());
        enquiry.setUpdatedOn(LocalDateTime.now());
        return enquiry;
    }
    
    private EnquiryThread createTestThread() {
        EnquiryThread thread = new EnquiryThread();
        thread.setId(1L);
        thread.setEnquiry(createTestEnquiry());
        thread.setThreadType(ThreadType.NOTE);
        thread.setMessage("Test message");
        thread.setUserId(1L);
        thread.setCreatedOn(LocalDateTime.now());
        return thread;
    }
}