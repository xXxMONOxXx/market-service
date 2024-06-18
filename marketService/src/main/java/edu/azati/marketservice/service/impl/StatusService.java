package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.model.enums.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StatusService {

    @Cacheable("findAllStatuses")
    public List<String> findAll() {
        return Arrays.stream(Status.values()).map(status -> status.getValue().toUpperCase()).toList();
    }

}
