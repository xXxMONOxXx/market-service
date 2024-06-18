package edu.azati.marketservice.dto;

import edu.azati.marketservice.model.enums.Status;
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
@Schema(description = "Order DTO entity, that contains products")
public class ProductOrderDto {

    @Schema(description = "ID of the order in database",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID of the order in database")
    private Status currentStatus;

    @Schema(description = "Description of the order, could contain some additional information",
            example = "Description")
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, hidden = true)
    private Boolean orderHasProducts = true;

    @Schema(description = "Address, where order should go to, in this request only ID needed")
    @NotBlank
    private AddressDto address;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "ID of user, that created this order",
            example = "1")
    private Long createdBy;

    @Schema(description = "List of products, that are in this order")
    private List<ProductOrderProductDto> products;
}
