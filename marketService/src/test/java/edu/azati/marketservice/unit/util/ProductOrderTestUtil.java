package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.dto.ProductOrderProductDto;
import edu.azati.marketservice.model.ProductOrder;
import edu.azati.marketservice.model.ProductOrderProduct;
import edu.azati.marketservice.model.enums.Status;
import edu.azati.marketservice.unit.util.constants.ProductOrderTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

@UtilityClass
public class ProductOrderTestUtil {

    public ProductOrderDto buildDto() {
        return ProductOrderDto.builder()
                .id(ProductOrderTestConstants.ID)
                .orderHasProducts(true)
                .description(ProductOrderTestConstants.DESCRIPTION)
                .createdAt(ProductOrderTestConstants.CREATED_AT)
                .updatedAt(ProductOrderTestConstants.UPDATED_AT)
                .createdBy(ProductOrderTestConstants.CREATED_BY)
                .products(buildProductsForOrder())
                .build();
    }

    public Page<ProductOrderDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public ProductOrderDto buildDtoWithoutId() {
        return ProductOrderDto.builder()
                .description(ProductOrderTestConstants.DESCRIPTION)
                .orderHasProducts(true)
                .createdAt(ProductOrderTestConstants.CREATED_AT)
                .updatedAt(ProductOrderTestConstants.UPDATED_AT)
                .createdBy(ProductOrderTestConstants.CREATED_BY)
                .products(buildProductsForOrder())
                .build();
    }

    private List<ProductOrderProductDto> buildProductsForOrder() {
        return Collections.singletonList(ProductOrderProductDto.builder()
                .productId(ProductOrderTestConstants.ID)
                .quantity(ProductOrderTestConstants.QUANTITY)
                .build());
    }

    public ProductOrderDto buildDtoWithoutTimestampsAndId() {
        return ProductOrderDto.builder()
                .description(ProductOrderTestConstants.DESCRIPTION)
                .orderHasProducts(true)
                .createdBy(ProductOrderTestConstants.CREATED_BY)
                .products(buildProductsForOrder())
                .build();
    }

    public ProductOrder buildEntity() {
        return new ProductOrder(
                ProductOrderTestConstants.ID,
                true,
                Status.PAYMENT_DUE,
                ProductOrderTestConstants.DESCRIPTION,
                AddressTestUtil.buildEntity(),
                ProductOrderTestConstants.CREATED_BY,
                Collections.singletonList(new ProductOrderProduct(
                        ProductOrderTestConstants.ID,
                        ProductOrderTestConstants.ID,
                        ProductOrderTestConstants.PRICE
                ))
        );
    }

    public Page<ProductOrder> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
