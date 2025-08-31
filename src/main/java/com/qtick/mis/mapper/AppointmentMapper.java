package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.AppointmentDto;
import com.qtick.mis.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface AppointmentMapper {
    
    /**
     * Convert Appointment entity to AppointmentDto
     */
    @Mapping(target = "custId", source = "client.custId")
    @Mapping(target = "clientName", source = "client.name")
    @Mapping(target = "serviceName", ignore = true) // Will be populated by service layer
    @Mapping(target = "staffName", ignore = true) // Will be populated by service layer
    AppointmentDto toDto(Appointment appointment);
    
    /**
     * Convert list of Appointment entities to DTOs
     */
    List<AppointmentDto> toDtos(List<Appointment> appointments);
}