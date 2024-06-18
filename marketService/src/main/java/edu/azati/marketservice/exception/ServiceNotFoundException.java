package edu.azati.marketservice.exception;

public class ServiceNotFoundException extends RuntimeException {

    public ServiceNotFoundException(Long id) {
        super(String.format("Service with id %s was not found", id));
    }
}
