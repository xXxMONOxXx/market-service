package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.ServiceReviewDto;
import edu.azati.marketservice.model.ServiceReview;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ServiceReviewMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    ServiceReview dtoToReview(ServiceReviewDto request);

    ServiceReviewDto reviewToDto(ServiceReview review);

    @InheritConfiguration
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateReviewFromDto(ServiceReviewDto dto, @MappingTarget ServiceReview entity);
}
