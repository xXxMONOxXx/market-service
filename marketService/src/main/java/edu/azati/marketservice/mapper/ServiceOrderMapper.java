package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.ServiceOrderDto;
import edu.azati.marketservice.model.ServiceOrder;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ServiceOrderMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServiceOrder dtoToServiceOrder(ServiceOrderDto dto);

    ServiceOrderDto serviceOrderToDto(ServiceOrder order);

    @InheritConfiguration
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateServiceOrderFromDto(ServiceOrderDto dto, @MappingTarget ServiceOrder entity);
}
