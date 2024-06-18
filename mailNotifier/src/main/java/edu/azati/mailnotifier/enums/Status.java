package edu.azati.mailnotifier.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    CANCELLED("Cancelled"),

    DELIVERED("Delivered"),

    IN_TRANSIT("In_transit"),

    PAYMENT_DUE("Payment_due"),

    PICKUP_AVAILABLE("Pickup_available"),

    PROBLEM("Problem"),

    PROCESSING("Processing"),

    RETURNED("Returned"),

    FAILED("Failed"),

    COMPLETE("Complete");

    private final String value;
}

