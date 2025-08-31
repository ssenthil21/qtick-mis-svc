package com.qtick.mis.mapper;

import com.qtick.mis.dto.pipeline.CreateEnquiryDto;
import com.qtick.mis.dto.pipeline.EnquiryDetailDto;
import com.qtick.mis.dto.pipeline.EnquiryDto;
import com.qtick.mis.dto.pipeline.EnquiryThreadDto;
import com.qtick.mis.dto.pipeline.UpdateEnquiryDto;
import com.qtick.mis.entity.Enquiry;
import com.qtick.mis.entity.EnquiryThread;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE,
    nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface EnquiryMapper {
    
    /**
     * Convert Enquiry entity to EnquiryDto
     */
    @Mapping(target = "assigneeName", ignore = true) // Will be populated by service layer
    EnquiryDto toDto(Enquiry enquiry);
    
    /**
     * Convert Enquiry entity to EnquiryDetailDto with threads
     */
    @Mapping(target = "assigneeName", ignore = true)
    @Mapping(target = "threads", source = "threads")
    @Mapping(target = "attributes", ignore = true) // Will be populated by service layer
    @Mapping(target = "tags", ignore = true) // Will be populated by service layer
    EnquiryDetailDto toDetailDto(Enquiry enquiry);
    
    /**
     * Convert CreateEnquiryDto to Enquiry entity
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bizId", ignore = true) // Will be set from tenant context
    @Mapping(target = "lastTouchDate", ignore = true)
    @Mapping(target = "closureDate", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "threads", ignore = true)
    Enquiry toEntity(CreateEnquiryDto createDto);
    
    /**
     * Update existing Enquiry entity with UpdateEnquiryDto
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "bizId", ignore = true)
    @Mapping(target = "branchId", ignore = true)
    @Mapping(target = "custId", ignore = true)
    @Mapping(target = "name", ignore = true)
    @Mapping(target = "enqType", ignore = true)
    @Mapping(target = "srvcEnq", ignore = true)
    @Mapping(target = "source", ignore = true)
    @Mapping(target = "channel", ignore = true)
    @Mapping(target = "lastTouchDate", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    @Mapping(target = "updatedOn", ignore = true)
    @Mapping(target = "threads", ignore = true)
    void updateEntity(UpdateEnquiryDto updateDto, @MappingTarget Enquiry enquiry);
    
    /**
     * Convert EnquiryThread entity to EnquiryThreadDto
     */
    @Mapping(target = "userName", ignore = true) // Will be populated by service layer
    EnquiryThreadDto toThreadDto(EnquiryThread thread);
    
    /**
     * Convert list of EnquiryThread entities to DTOs
     */
    List<EnquiryThreadDto> toThreadDtos(List<EnquiryThread> threads);
    
    /**
     * Convert list of Enquiry entities to DTOs
     */
    List<EnquiryDto> toDtos(List<Enquiry> enquiries);
}