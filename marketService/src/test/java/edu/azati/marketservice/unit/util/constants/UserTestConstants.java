package edu.azati.marketservice.unit.util.constants;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;

@UtilityClass
public class UserTestConstants {

    public final Long ID = 1L;

    public final String EMAIL = "email@mail.com";

    public final String USERNAME = "username";

    public final String PASSWORD = "password";

    public final Boolean IS_BLOCKED = false;

    public final LocalDateTime UPDATED_AT = LocalDateTime.now();

    public final LocalDate BIRTHDATE = LocalDate.now();

    public final String FIRSTNAME = "firstname";

    public final String SURNAME = "surname";

    public final String PHONE = "123456";

    public final Boolean SEX = false;
}
