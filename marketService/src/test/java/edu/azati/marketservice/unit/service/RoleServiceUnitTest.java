package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.mapper.RoleMapper;
import edu.azati.marketservice.model.enums.Role;
import edu.azati.marketservice.service.impl.RoleService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(classes = {RoleMapper.class})
class RoleServiceUnitTest {

    @InjectMocks
    private RoleService service;

    @Test
    void findAllTest() {
        List<String> expected = Arrays.stream(Role.values()).map(role -> role.getValue().toUpperCase()).toList();
        List<String> actual = service.findAll();
        assertEquals(expected, actual);
    }

}
