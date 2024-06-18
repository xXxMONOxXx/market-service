package edu.azati.marketservice.exception;

public class StatusNotFoundException extends RuntimeException {

    public StatusNotFoundException(Long id) {
        super(String.format("Status with id %s was not found", id));
    }

    public StatusNotFoundException(String name) {
        super(String.format("Status with name %s was not found", name));
    }
}
