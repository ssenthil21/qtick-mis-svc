package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.ClientDetailDto;
import com.qtick.mis.dto.client.ClientSummaryDto;
import com.qtick.mis.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface ClientMapper {
    
    /**
     * Convert Client entity to ClientSummaryDto
     */
    @Mapping(target = "lastVisit", ignore = true) // Will be calculated by service layer
    @Mapping(target = "totalSpending", ignore = true) // Will be calculated by service layer
    @Mapping(target = "loyaltyPoints", ignore = true) // Will be calculated by service layer
    @Mapping(target = "loyaltyStatus", ignore = true) // Will be calculated by service layer
    @Mapping(target = "tags", ignore = true) // Will be populated by service layer
    ClientSummaryDto toSummaryDto(Client client);
    
    /**
     * Convert Client entity to ClientDetailDto
     */
    @Mapping(target = "lastVisit", ignore = true)
    @Mapping(target = "totalSpending", ignore = true)
    @Mapping(target = "loyaltyPoints", ignore = true)
    @Mapping(target = "loyaltyStatus", ignore = true)
    @Mapping(target = "tags", ignore = true)
    @Mapping(target = "city", ignore = true) // Will be extracted from address
    @Mapping(target = "state", ignore = true) // Will be extracted from address
    @Mapping(target = "country", ignore = true) // Will be extracted from address
    @Mapping(target = "zipCode", ignore = true) // Will be extracted from address
    @Mapping(target = "preferences", ignore = true) // Will be populated by service layer
    @Mapping(target = "demographics", ignore = true) // Will be populated by service layer
    @Mapping(target = "totalVisits", ignore = true) // Will be calculated by service layer
    @Mapping(target = "totalAppointments", ignore = true) // Will be calculated by service layer
    @Mapping(target = "totalBills", ignore = true) // Will be calculated by service layer
    @Mapping(target = "averageRating", ignore = true) // Will be calculated by service layer
    @Mapping(target = "preferredServices", ignore = true) // Will be calculated by service layer
    ClientDetailDto toDetailDto(Client client);
    
    /**
     * Convert list of Client entities to ClientSummaryDto list
     */
    List<ClientSummaryDto> toSummaryDtos(List<Client> clients);
    
    /**
     * Convert Client entity to basic DTO (for nested mappings)
     */
    @Mapping(target = "lastVisit", ignore = true)
    @Mapping(target = "totalSpending", ignore = true)
    @Mapping(target = "loyaltyPoints", ignore = true)
    @Mapping(target = "loyaltyStatus", ignore = true)
    @Mapping(target = "tags", ignore = true)
    ClientSummaryDto toBasicDto(Client client);
}