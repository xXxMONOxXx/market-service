package edu.azati.marketservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum CurrencyEnum {

    USD("usd"),

    BYN("byn"),

    RYB("ryb");

    private final String value;
}
