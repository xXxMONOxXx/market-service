package edu.azati.marketservice.exception;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(Long id) {
        super(String.format("Image with id %s was not found", id));
    }
}
