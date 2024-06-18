package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.model.Product;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProductMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Product dtoToProduct(ProductDto request);

    ProductDto productToDto(Product product);

    @InheritConfiguration
    @Mapping(target = "sellerId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}
