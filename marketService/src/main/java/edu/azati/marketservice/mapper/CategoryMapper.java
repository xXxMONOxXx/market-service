package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface CategoryMapper {
    Category dtoToCategory(CategoryDto request);

    CategoryDto categoryToDto(Category category);

    void updateCategoryFromDto(CategoryDto dto, @MappingTarget Category entity);
}
