package edu.azati.marketservice.exception;

public class SmsVerificationException extends RuntimeException {

    public SmsVerificationException(Exception e) {
        super(e);
    }
}
