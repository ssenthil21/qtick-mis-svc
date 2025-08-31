package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.ClientDetailDto;
import com.qtick.mis.dto.client.ClientSummaryDto;
import com.qtick.mis.entity.Client;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ClientMapperTest {
    
    private final ClientMapper mapper = Mappers.getMapper(ClientMapper.class);
    
    @Test
    void shouldMapClientToSummaryDto() {
        // Given
        Client client = createTestClient();
        
        // When
        ClientSummaryDto dto = mapper.toSummaryDto(client);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getCustId()).isEqualTo(client.getCustId());
        assertThat(dto.getName()).isEqualTo(client.getName());
        assertThat(dto.getEmail()).isEqualTo(client.getEmail());
        assertThat(dto.getPhone()).isEqualTo(client.getPhone());
        assertThat(dto.getCreatedOn()).isEqualTo(client.getCreatedOn());
    }
    
    @Test
    void shouldMapClientToDetailDto() {
        // Given
        Client client = createTestClient();
        
        // When
        ClientDetailDto dto = mapper.toDetailDto(client);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getCustId()).isEqualTo(client.getCustId());
        assertThat(dto.getName()).isEqualTo(client.getName());
        assertThat(dto.getEmail()).isEqualTo(client.getEmail());
        assertThat(dto.getPhone()).isEqualTo(client.getPhone());
        assertThat(dto.getDob()).isEqualTo(client.getDob());
        assertThat(dto.getGender()).isEqualTo(client.getGender());
        assertThat(dto.getAddress()).isEqualTo(client.getAddress());
    }
    
    @Test
    void shouldMapClientList() {
        // Given
        List<Client> clients = Arrays.asList(createTestClient(), createTestClient());
        
        // When
        List<ClientSummaryDto> dtos = mapper.toSummaryDtos(clients);
        
        // Then
        assertThat(dtos).hasSize(2);
        assertThat(dtos.get(0).getName()).isEqualTo("John Doe");
    }
    
    @Test
    void shouldMapClientToBasicDto() {
        // Given
        Client client = createTestClient();
        
        // When
        ClientSummaryDto dto = mapper.toBasicDto(client);
        
        // Then
        assertThat(dto).isNotNull();
        assertThat(dto.getCustId()).isEqualTo(client.getCustId());
        assertThat(dto.getName()).isEqualTo(client.getName());
        assertThat(dto.getEmail()).isEqualTo(client.getEmail());
        assertThat(dto.getPhone()).isEqualTo(client.getPhone());
    }
    
    private Client createTestClient() {
        Client client = new Client();
        client.setCustId(1L);
        client.setBizId(100L);
        client.setName("John Doe");
        client.setEmail("john@example.com");
        client.setPhone("+1234567890");
        client.setDob(LocalDate.of(1990, 1, 1));
        client.setGender("Male");
        client.setAddress("123 Main St, City, State");
        client.setCreatedOn(LocalDateTime.now());
        client.setUpdatedOn(LocalDateTime.now());
        return client;
    }
}