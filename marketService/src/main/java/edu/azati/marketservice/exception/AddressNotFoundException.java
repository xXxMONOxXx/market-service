package edu.azati.marketservice.exception;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long id) {
        super(String.format("Address with id %s was not found", id));
    }
}
