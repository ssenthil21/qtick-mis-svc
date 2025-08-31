package com.qtick.mis.mapper;

import com.qtick.mis.dto.client.BillDto;
import com.qtick.mis.dto.client.BillItemDto;
import com.qtick.mis.dto.client.BillPaymentDto;
import com.qtick.mis.entity.Bill;
import com.qtick.mis.entity.BillItem;
import com.qtick.mis.entity.BillPayment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
    componentModel = "spring",
    unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface BillMapper {
    
    /**
     * Convert Bill entity to BillDto with items and payments
     */
    @Mapping(target = "custId", source = "client.custId")
    @Mapping(target = "items", source = "items")
    @Mapping(target = "payments", source = "payments")
    BillDto toDto(Bill bill);
    
    /**
     * Convert Bill entity to BillDto without nested collections (for summary views)
     */
    @Mapping(target = "custId", source = "client.custId")
    @Mapping(target = "items", ignore = true)
    @Mapping(target = "payments", ignore = true)
    BillDto toSummaryDto(Bill bill);
    
    /**
     * Convert BillItem entity to BillItemDto
     */
    @Mapping(target = "serviceName", ignore = true) // Will be populated by service layer
    BillItemDto toItemDto(BillItem item);
    
    /**
     * Convert BillPayment entity to BillPaymentDto
     */
    BillPaymentDto toPaymentDto(BillPayment payment);
    
    /**
     * Convert list of Bill entities to DTOs
     */
    List<BillDto> toDtos(List<Bill> bills);
    
    /**
     * Convert list of Bill entities to summary DTOs
     */
    List<BillDto> toSummaryDtos(List<Bill> bills);
    
    /**
     * Convert list of BillItem entities to DTOs
     */
    List<BillItemDto> toItemDtos(List<BillItem> items);
    
    /**
     * Convert list of BillPayment entities to DTOs
     */
    List<BillPaymentDto> toPaymentDtos(List<BillPayment> payments);
}