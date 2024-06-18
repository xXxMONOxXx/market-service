package edu.azati.marketservice.exception;

public class KeycloakException extends RuntimeException {

    public KeycloakException(String message) {
        super(message);
    }

    public KeycloakException(Exception e) {
        super(e);
    }
}
