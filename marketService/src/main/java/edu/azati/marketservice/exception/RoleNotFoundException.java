package edu.azati.marketservice.exception;

public class RoleNotFoundException extends RuntimeException {

    public RoleNotFoundException(Long id) {
        super(String.format("Role with id %s was not found", id));
    }
}
