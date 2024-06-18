package edu.azati.marketservice.unit.util.constants;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ProductTestConstants {

    public final Long ID = 1L;

    public final Long SELLER_ID = 1L;

    public final String NAME = "name";

    public final Integer PRICE = 1;

    public final Integer QUANTITY = 1;

    public final String DESCRIPTION = "description";

    public final LocalDateTime CREATED_AT = LocalDateTime.now();

    public final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public final Long UPDATED_BY = 1L;

    public final String JSON_EXAMPLE = """
            {"json":"yes"}
            """;
}
