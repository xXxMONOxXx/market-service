package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.ProductReviewDto;
import edu.azati.marketservice.model.ProductReview;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ProductReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ProductReview dtoToReview(ProductReviewDto request);

    ProductReviewDto reviewToDto(ProductReview review);

    @InheritConfiguration
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReviewFromDto(ProductReviewDto dto, @MappingTarget ProductReview entity);
}
