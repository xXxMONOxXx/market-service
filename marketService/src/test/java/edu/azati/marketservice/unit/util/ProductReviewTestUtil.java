package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.ProductReviewDto;
import edu.azati.marketservice.model.ProductReview;
import edu.azati.marketservice.unit.util.constants.ProductReviewTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@UtilityClass
public class ProductReviewTestUtil {

    public ProductReviewDto buildDto() {
        return ProductReviewDto.builder()
                .id(ProductReviewTestConstants.ID)
                .productId(ProductReviewTestConstants.PRODUCT_ID)
                .comment(ProductReviewTestConstants.COMMENT)
                .rating(ProductReviewTestConstants.RATING)
                .createdAt(ProductReviewTestConstants.CREATED_AT)
                .updatedAt(ProductReviewTestConstants.UPDATED_AT)
                .createdBy(ProductReviewTestConstants.CREATED_BY)
                .build();
    }

    public Page<ProductReviewDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public ProductReviewDto buildDtoWithoutId() {
        return ProductReviewDto.builder()
                .productId(ProductReviewTestConstants.PRODUCT_ID)
                .comment(ProductReviewTestConstants.COMMENT)
                .rating(ProductReviewTestConstants.RATING)
                .createdAt(ProductReviewTestConstants.CREATED_AT)
                .updatedAt(ProductReviewTestConstants.UPDATED_AT)
                .createdBy(ProductReviewTestConstants.CREATED_BY)
                .build();
    }

    public ProductReviewDto buildDtoWithoutTimestampsAndId() {
        return ProductReviewDto.builder()
                .productId(ProductReviewTestConstants.PRODUCT_ID)
                .comment(ProductReviewTestConstants.COMMENT)
                .rating(ProductReviewTestConstants.RATING)
                .createdBy(ProductReviewTestConstants.CREATED_BY)
                .build();
    }

    public ProductReview buildEntity() {
        return new ProductReview(
                ProductReviewTestConstants.ID,
                ProductReviewTestConstants.COMMENT,
                ProductReviewTestConstants.RATING,
                ProductReviewTestConstants.PRODUCT_ID,
                true,
                ProductReviewTestConstants.CREATED_BY
        );
    }

    public Page<ProductReview> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
