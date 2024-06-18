package edu.azati.pdfconverter.dto.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Status {

    FAILED("Failed"),

    COMPLETE("Complete");

    private final String value;
}
