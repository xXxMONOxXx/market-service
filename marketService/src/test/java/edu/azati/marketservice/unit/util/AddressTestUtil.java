package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.AddressDto;
import edu.azati.marketservice.model.Address;
import edu.azati.marketservice.unit.util.constants.AddressTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@UtilityClass
public class AddressTestUtil {


    public AddressDto buildDto() {
        return AddressDto.builder()
                .id(AddressTestConstants.ID)
                .country(AddressTestConstants.COUNTRY)
                .city(AddressTestConstants.CITY)
                .street(AddressTestConstants.STREET)
                .apartment(AddressTestConstants.APARTMENT)
                .postcode(AddressTestConstants.POSTCODE)
                .createdAt(AddressTestConstants.CREATED_AT)
                .updatedAt(AddressTestConstants.UPDATED_AT)
                .createdBy(AddressTestConstants.CREATED_BY)
                .build();
    }

    public AddressDto buildDtoWithoutTimestampsAndId() {
        return AddressDto.builder()
                .country(AddressTestConstants.COUNTRY)
                .city(AddressTestConstants.CITY)
                .street(AddressTestConstants.STREET)
                .apartment(AddressTestConstants.APARTMENT)
                .postcode(AddressTestConstants.POSTCODE)
                .createdBy(AddressTestConstants.CREATED_BY)
                .build();
    }

    public Page<AddressDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public AddressDto buildDtoWithoutId() {
        return AddressDto.builder()
                .country(AddressTestConstants.COUNTRY)
                .city(AddressTestConstants.CITY)
                .street(AddressTestConstants.STREET)
                .apartment(AddressTestConstants.APARTMENT)
                .postcode(AddressTestConstants.POSTCODE)
                .createdAt(AddressTestConstants.CREATED_AT)
                .updatedAt(AddressTestConstants.UPDATED_AT)
                .createdBy(AddressTestConstants.CREATED_BY)
                .build();
    }

    public Address buildEntity() {
        return new Address(
                AddressTestConstants.ID,
                AddressTestConstants.COUNTRY,
                AddressTestConstants.CITY,
                AddressTestConstants.STREET,
                AddressTestConstants.APARTMENT,
                AddressTestConstants.POSTCODE,
                AddressTestConstants.CREATED_BY
        );
    }

    public Page<Address> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
