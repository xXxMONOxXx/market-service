package edu.azati.marketservice.exception;

public class ServiceOrderNotFoundException extends RuntimeException {

    public ServiceOrderNotFoundException(Long id) {
        super(String.format("Service order with id %s was not found", id));
    }
}
