package edu.azati.marketservice.controller;

import org.springframework.http.ResponseEntity;

public interface BaseController<T> {
    ResponseEntity<Object> save(T dto);

    ResponseEntity<Object> findById(Long id);

    ResponseEntity<Object> update(T dto, Long id);

    ResponseEntity<Object> delete(Long id);

    ResponseEntity<Object> findAll(int pageNumber, int pageSize);
}
