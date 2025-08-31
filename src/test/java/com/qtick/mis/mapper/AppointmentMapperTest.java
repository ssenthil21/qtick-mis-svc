package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.AppointmentDto;
import com.qtick.mis.entity.Appointment;
import com.qtick.mis.entity.Client;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class AppointmentMapperTest {
    
    private final AppointmentMapper mapper = Mappers.getMapper(AppointmentMapper.class);
    
    @Test
    void shouldMapAppointmentToDto() {
        // Given
        Appointment appointment = createTestAppointment();
        
        // When
        AppointmentDto dto = mapper.toDto(appointment);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getAppointmentId()).isEqualTo(appointment.getAppointmentId());
        assertThat(dto.getCustId()).isEqualTo(appointment.getClient().getCustId());
        assertThat(dto.getClientName()).isEqualTo(appointment.getClient().getName());
        assertThat(dto.getAppointmentDate()).isEqualTo(appointment.getAppointmentDate());
        assertThat(dto.getStatus()).isEqualTo(appointment.getStatus());
        assertThat(dto.getServiceId()).isEqualTo(appointment.getServiceId());
        assertThat(dto.getStaffId()).isEqualTo(appointment.getStaffId());
        assertThat(dto.getNotes()).isEqualTo(appointment.getNotes());
        assertThat(dto.getCreatedOn()).isEqualTo(appointment.getCreatedOn());
    }
    
    @Test
    void shouldMapAppointmentList() {
        // Given
        List<Appointment> appointments = Arrays.asList(createTestAppointment(), createTestAppointment());
        
        // When
        List<AppointmentDto> dtos = mapper.toDtos(appointments);
        
        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getClientName()).isEqualTo("John Doe");
        assertThat(dtos.get(0).getStatus()).isEqualTo("SCHEDULED");
    }
    
    private Appointment createTestAppointment() {
        Client client = new Client();
        client.setCustId(1L);
        client.setName("John Doe");
        
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(1L);
        appointment.setClient(client);
        appointment.setAppointmentDate(LocalDateTime.now().plusDays(1));
        appointment.setStatus("SCHEDULED");
        appointment.setServiceId(1L);
        appointment.setStaffId(1L);
        appointment.setNotes("Regular checkup");
        appointment.setCreatedOn(LocalDateTime.now());
        return appointment;
    }
}