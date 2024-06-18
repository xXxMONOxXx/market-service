package edu.azati.pdfconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InvoiceDto {

    private Long amount;

    private List<PaymentDto> payments;

    private List<ProductDto> products;

    private ServiceDto service;

    private AddressDto address;

    private String filename;
}
