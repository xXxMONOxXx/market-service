package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.model.ProductOrder;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProductOrderMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductOrder dtoToProductOrder(ProductOrderDto dto);

    ProductOrderDto productOrderToDto(ProductOrder productOrder);

    @InheritConfiguration
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductOrderFromDto(ProductOrderDto dto, @MappingTarget ProductOrder entity);
}
