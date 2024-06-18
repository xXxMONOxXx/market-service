package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.model.enums.Status;
import edu.azati.marketservice.service.impl.StatusService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(classes = {})
class StatusServiceUnitTest {

    @InjectMocks
    private StatusService service;

    @Test
    void findAllTest() {
        List<String> expected = Arrays.stream(Status.values()).map(status -> status.getValue().toUpperCase()).toList();
        List<String> actual = service.findAll();
        assertEquals(expected, actual);
    }
}
