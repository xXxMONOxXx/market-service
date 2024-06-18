package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.PaymentDto;
import edu.azati.marketservice.model.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface PaymentMapper {
    Payment dtoToPayment(PaymentDto dto);

    PaymentDto paymentToDto(Payment payment);
}
