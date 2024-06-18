package edu.azati.marketservice.unit.util.constants;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ServiceOrderTestConstants {

    public final Long ID = 1L;

    public final String DESCRIPTION = "description";

    public final Long CREATED_BY = 1L;

    public final LocalDateTime CREATED_AT = LocalDateTime.now();

    public final LocalDateTime UPDATED_AT = LocalDateTime.now();
}
