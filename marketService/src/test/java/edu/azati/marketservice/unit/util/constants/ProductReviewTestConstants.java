package edu.azati.marketservice.unit.util.constants;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ProductReviewTestConstants {

    public final Long ID = 1L;

    public final Long PRODUCT_ID = 1L;

    public final String COMMENT = "comment";

    public final Byte RATING = 10;

    public final LocalDateTime CREATED_AT = LocalDateTime.now();

    public final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public final Long CREATED_BY = 1L;
}
