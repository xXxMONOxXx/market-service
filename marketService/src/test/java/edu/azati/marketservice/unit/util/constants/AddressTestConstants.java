package edu.azati.marketservice.unit.util.constants;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class AddressTestConstants {

    public final Long ID = 1L;

    public final String COUNTRY = "country";

    public final String CITY = "city";

    public final String STREET = "street";

    public final String APARTMENT = "apartment";

    public final String POSTCODE = "postcode";

    public final LocalDateTime CREATED_AT = LocalDateTime.now();

    public final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public final Long CREATED_BY = 1L;
}
