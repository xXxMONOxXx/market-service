package edu.azati.marketservice.service.impl;

import org.springframework.cache.annotation.Cacheable;
import edu.azati.marketservice.model.enums.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    @Cacheable("findAllRoles")
    public List<String> findAll() {
        return Arrays.stream(Role.values()).map(role -> role.getValue().toUpperCase()).toList();
    }
}
