package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO helper entity for invoice, contains order's products information")
public class InvoiceProductDto {

    private Long productId;

    private String name;

    private Integer quantity;

    private Integer price;
}
