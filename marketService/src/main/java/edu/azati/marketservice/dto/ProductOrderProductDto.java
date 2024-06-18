package edu.azati.marketservice.dto;


import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Part of product order dto, contains id of product and quantity")
public class ProductOrderProductDto {

    @Schema(description = "ID of the product in database",
            example = "1")
    @NotBlank
    private Long productId;

    @Hidden
    private String name;

    @Schema(description = "Number of particular product in order",
            example = "3")
    @NotBlank
    private Integer quantity;
}
