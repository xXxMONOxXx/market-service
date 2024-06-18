package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.model.Category;
import edu.azati.marketservice.unit.util.constants.CategoryTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@UtilityClass
public class CategoryTestUtil {

    public CategoryDto buildDto() {
        return CategoryDto.builder()
                .id(CategoryTestConstants.ID)
                .name(CategoryTestConstants.NAME)
                .description(CategoryTestConstants.DESCRIPTION)
                .build();
    }

    public Page<CategoryDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public CategoryDto buildDtoWithoutId() {
        return CategoryDto.builder()
                .name(CategoryTestConstants.NAME)
                .description(CategoryTestConstants.DESCRIPTION)
                .build();
    }

    public Category buildEntity() {
        return new Category(
                CategoryTestConstants.ID,
                CategoryTestConstants.NAME,
                CategoryTestConstants.DESCRIPTION
        );
    }

    public Page<Category> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
