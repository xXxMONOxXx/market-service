package edu.azati.marketservice.exception;

public class ProductOrderNotFoundException extends RuntimeException {

    public ProductOrderNotFoundException(Long id) {
        super(String.format("Product order with id %s was not found", id));
    }
}
