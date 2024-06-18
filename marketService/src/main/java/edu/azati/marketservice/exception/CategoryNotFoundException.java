package edu.azati.marketservice.exception;

public class CategoryNotFoundException extends RuntimeException {

    public CategoryNotFoundException(Long id) {
        super(String.format("Category with id %s was not found", id));
    }
}
