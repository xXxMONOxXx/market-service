package edu.azati.marketservice.model.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    CANCELLED("cancelled"),

    DELIVERED("delivered"),

    IN_TRANSIT("in_transit"),

    PAYMENT_DUE("payment_due"),

    PICKUP_AVAILABLE("pickup_available"),

    PROBLEM("problem"),

    PROCESSING("processing"),

    RETURNED("returned"),

    FAILED("failed"),

    COMPLETE("complete");

    private final String value;
}
