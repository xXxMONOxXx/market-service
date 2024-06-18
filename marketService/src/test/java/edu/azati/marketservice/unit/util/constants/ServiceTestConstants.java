package edu.azati.marketservice.unit.util.constants;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;

@UtilityClass
public class ServiceTestConstants {

    public final Long ID = 1L;

    public final Long WORKER_ID = 1L;

    public final String NAME = "name";

    public final String DESCRIPTION = "description";

    public final Integer PRICE = 1;

    public final LocalDateTime CREATED_AT = LocalDateTime.now();

    public final LocalDateTime UPDATED_AT = LocalDateTime.now();
}
