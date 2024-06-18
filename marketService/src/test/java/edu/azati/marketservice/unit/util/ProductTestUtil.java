package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.dto.ProductHistoryDto;
import edu.azati.marketservice.model.Product;
import edu.azati.marketservice.model.ProductPriceHistory;
import edu.azati.marketservice.unit.util.constants.ProductTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class ProductTestUtil {

    public ProductDto buildDto() {
        return ProductDto.builder()
                .id(ProductTestConstants.ID)
                .sellerId(ProductTestConstants.SELLER_ID)
                .name(ProductTestConstants.NAME)
                .createdAt(ProductTestConstants.CREATED_AT)
                .updatedAt(ProductTestConstants.UPDATED_AT)
                .updatedBy(ProductTestConstants.UPDATED_BY)
                .categories(buildCategories())
                .priceHistory(buildPriceHistory())
                .build();
    }

    public Page<ProductDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public ProductDto buildDtoWithoutId() {
        return ProductDto.builder()
                .sellerId(ProductTestConstants.SELLER_ID)
                .name(ProductTestConstants.NAME)
                .createdAt(ProductTestConstants.CREATED_AT)
                .updatedAt(ProductTestConstants.UPDATED_AT)
                .updatedBy(ProductTestConstants.UPDATED_BY)
                .categories(buildCategories())
                .priceHistory(buildPriceHistory())
                .build();
    }

    private List<CategoryDto> buildCategories() {
        return Collections.singletonList(CategoryTestUtil.buildDto());
    }

    private List<ProductHistoryDto> buildPriceHistory() {
        return Collections.singletonList(ProductHistoryDto.builder()
                .id(ProductTestConstants.ID)
                .createdAt(ProductTestConstants.CREATED_AT)
                .price(ProductTestConstants.PRICE)
                .build());
    }

    public Product buildEntity() {
        return new Product(
                ProductTestConstants.ID,
                ProductTestConstants.SELLER_ID,
                ProductTestConstants.NAME,
                ProductTestConstants.QUANTITY,
                ProductTestConstants.DESCRIPTION,
                ProductTestConstants.PRICE,
                Collections.singletonList(CategoryTestUtil.buildEntity()),
                buildPriceHistoryForEntity(),
                new ArrayList<>(),
                ProductTestConstants.JSON_EXAMPLE
        );
    }

    private List<ProductPriceHistory> buildPriceHistoryForEntity() {
        return Collections.singletonList(new ProductPriceHistory(
                ProductTestConstants.ID,
                ProductTestConstants.ID,
                ProductTestConstants.PRICE,
                ProductTestConstants.CREATED_AT
        ));
    }

    public ProductDto buildDtoWithoutTimestampsAndId() {
        return ProductDto.builder()
                .sellerId(ProductTestConstants.SELLER_ID)
                .name(ProductTestConstants.NAME)
                .updatedBy(ProductTestConstants.UPDATED_BY)
                .categories(buildCategories())
                .priceHistory(buildPriceHistory())
                .build();
    }

    public Page<Product> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
