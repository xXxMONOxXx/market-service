package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.ServiceReviewDto;
import edu.azati.marketservice.model.ServiceReview;
import edu.azati.marketservice.unit.util.constants.ServiceReviewTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@UtilityClass
public class ServiceReviewTestUtil {
    public ServiceReviewDto buildDto() {
        return ServiceReviewDto.builder()
                .id(ServiceReviewTestConstants.ID)
                .serviceId(ServiceReviewTestConstants.SERVICE_ID)
                .comment(ServiceReviewTestConstants.COMMENT)
                .rating(ServiceReviewTestConstants.RATING)
                .createdAt(ServiceReviewTestConstants.CREATED_AT)
                .updatedAt(ServiceReviewTestConstants.UPDATED_AT)
                .createdBy(ServiceReviewTestConstants.CREATED_BY)
                .build();
    }

    public Page<ServiceReviewDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public ServiceReviewDto buildDtoWithoutId() {
        return ServiceReviewDto.builder()
                .serviceId(ServiceReviewTestConstants.SERVICE_ID)
                .comment(ServiceReviewTestConstants.COMMENT)
                .rating(ServiceReviewTestConstants.RATING)
                .createdAt(ServiceReviewTestConstants.CREATED_AT)
                .updatedAt(ServiceReviewTestConstants.UPDATED_AT)
                .createdBy(ServiceReviewTestConstants.CREATED_BY)
                .build();
    }

    public ServiceReviewDto buildDtoWithoutTimestampsAndId() {
        return ServiceReviewDto.builder()
                .id(ServiceReviewTestConstants.ID)
                .serviceId(ServiceReviewTestConstants.SERVICE_ID)
                .comment(ServiceReviewTestConstants.COMMENT)
                .rating(ServiceReviewTestConstants.RATING)
                .createdBy(ServiceReviewTestConstants.CREATED_BY)
                .build();
    }

    public ServiceReview buildEntity() {
        return new ServiceReview(
                ServiceReviewTestConstants.ID,
                ServiceReviewTestConstants.COMMENT,
                ServiceReviewTestConstants.RATING,
                ServiceReviewTestConstants.SERVICE_ID,
                false,
                ServiceReviewTestConstants.CREATED_BY
        );
    }

    public Page<ServiceReview> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}