package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.dto.ServiceDto;
import edu.azati.marketservice.model.Category;
import edu.azati.marketservice.model.Service;
import edu.azati.marketservice.unit.util.constants.ServiceTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ServiceTestUtil {


    public ServiceDto buildDto() {
        return ServiceDto.builder()
                .id(ServiceTestConstants.ID)
                .workerId(ServiceTestConstants.WORKER_ID)
                .name(ServiceTestConstants.NAME)
                .price(ServiceTestConstants.PRICE)
                .description(ServiceTestConstants.DESCRIPTION)
                .createdAt(ServiceTestConstants.CREATED_AT)
                .updatedAt(ServiceTestConstants.UPDATED_AT)
                .categories(createServiceCategories())
                .build();
    }

    public Page<ServiceDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public ServiceDto buildDtoWithoutId() {
        return ServiceDto.builder()
                .workerId(ServiceTestConstants.WORKER_ID)
                .name(ServiceTestConstants.NAME)
                .price(ServiceTestConstants.PRICE)
                .description(ServiceTestConstants.DESCRIPTION)
                .createdAt(ServiceTestConstants.CREATED_AT)
                .updatedAt(ServiceTestConstants.UPDATED_AT)
                .categories(createServiceCategories())
                .build();
    }

    private List<CategoryDto> createServiceCategories() {
        return Collections.singletonList(CategoryTestUtil.buildDto());
    }

    public Service buildEntity() {
        return new Service(
                ServiceTestConstants.ID,
                ServiceTestConstants.WORKER_ID,
                ServiceTestConstants.NAME,
                ServiceTestConstants.DESCRIPTION,
                ServiceTestConstants.PRICE,
                createServiceCategoriesForEntity(),
                new ArrayList<>()
        );
    }

    private List<Category> createServiceCategoriesForEntity() {
        return Collections.singletonList(CategoryTestUtil.buildEntity());
    }

    public ServiceDto buildDtoWithoutTimestampsAndId() {
        return ServiceDto.builder()
                .workerId(ServiceTestConstants.WORKER_ID)
                .name(ServiceTestConstants.NAME)
                .price(ServiceTestConstants.PRICE)
                .description(ServiceTestConstants.DESCRIPTION)
                .categories(createServiceCategories())
                .build();
    }

    public Page<Service> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
