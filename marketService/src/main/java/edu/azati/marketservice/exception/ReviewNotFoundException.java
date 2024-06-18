package edu.azati.marketservice.exception;

public class ReviewNotFoundException extends RuntimeException {

    public ReviewNotFoundException(Long id) {
        super(String.format("Review with id %s was not found", id));
    }
}
