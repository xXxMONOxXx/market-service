package edu.azati.pdfconverter.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {

    private String country;

    private String city;

    private String street;

    private String apartment;

    private String postcode;
}
