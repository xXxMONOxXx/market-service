package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product DTO entity")
public class ProductDto {

    @Schema(description = "ID of the product in database",
            example = "1")
    private Long id;

    @Schema(description = "ID of the product owner",
            example = "1")
    @NotBlank
    private Long sellerId;

    @Schema(description = "Product name",
            example = "Mug")
    @NotBlank
    private String name;

    @Schema(description = "Product price",
            example = "100")
    @NotBlank
    private Integer price;

    @Schema(description = "Number of left products",
            example = "100")
    @NotBlank
    private Integer quantity;

    @Schema(description = "Description of the product",
            example = "This is a mug, people drink from it")
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "ID of the user, that updated this product",
            example = "1")
    private Long updatedBy;

    @Schema(description = "List of product's categories")
    private List<CategoryDto> categories;

    @Schema(description = "Product's price history",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<ProductHistoryDto> priceHistory;

    @Schema(description = "Product's images",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<ImageResponseDto> images;

    @Schema(description = "Product's additional details in json format",
            example = """
                    {\
                        "main":{\
                            "type": "phone",\
                            "size": "big",\
                            "resolution": "1920x1080"\
                        }\
                        "processor":{\
                            "platform": "qualcomm",\
                            "gpu": "adreno"\
                        }\
                    }\
                    """)
    private String productDetails;
}
