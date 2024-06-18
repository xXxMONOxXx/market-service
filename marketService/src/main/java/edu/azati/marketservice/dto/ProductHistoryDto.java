package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Product history DTO entity")
public class ProductHistoryDto {

    @Schema(description = "Product's price history ID in database",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Old price of the product",
            accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private Integer price;

    @Schema(description = "Timestamp, on which product's price was changed",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;
}
