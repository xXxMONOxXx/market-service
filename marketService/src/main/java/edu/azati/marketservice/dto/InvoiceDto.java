package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Invoice DTO entity")
public class InvoiceDto {

    private Long amount;

    private List<PaymentDto> payments;

    private List<InvoiceProductDto> products;

    private ServiceDto service;

    private AddressDto address;

    private String filename;
}
