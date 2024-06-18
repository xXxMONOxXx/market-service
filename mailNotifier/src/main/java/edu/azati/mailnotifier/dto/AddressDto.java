package edu.azati.mailnotifier.dto;

import lombok.Data;

@Data
public class AddressDto {

    private String country;

    private String city;

    private String street;

    private String apartment;

    private String postcode;

    @Override
    public String toString() {
        return String.format("""
                County: %s
                City: %s
                Street: %s
                Apartment: %s
                Postcode: %s
                """, country, city, street, apartment, postcode);
    }
}
