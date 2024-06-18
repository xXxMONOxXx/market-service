package edu.azati.marketservice.service;

import org.springframework.data.domain.Page;

public interface BaseService<T> {
    T save(T dto);

    T delete(Long id);

    T findById(Long id);

    T update(T dto, Long id);

    Page<T> findAll(int pageNumber, int pageSize);
}
