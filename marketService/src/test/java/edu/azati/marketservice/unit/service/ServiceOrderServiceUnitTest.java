package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.ServiceOrderDto;
import edu.azati.marketservice.exception.ServiceOrderNotFoundException;
import edu.azati.marketservice.mapper.ServiceOrderMapper;
import edu.azati.marketservice.model.ServiceOrder;
import edu.azati.marketservice.repository.ServiceOrderRepository;
import edu.azati.marketservice.service.impl.ServiceOrderService;
import edu.azati.marketservice.unit.util.ServiceOrderTestUtil;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {ServiceOrderMapper.class})
class ServiceOrderServiceUnitTest {

    @Mock
    private ServiceOrderRepository repository;

    @Spy
    private ServiceOrderMapper mapper = Mappers.getMapper(ServiceOrderMapper.class);

    @InjectMocks
    private ServiceOrderService service;

    @Test
    void saveTest() {
        ServiceOrderDto request = ServiceOrderTestUtil.buildDtoWithoutTimestampsAndId();
        ServiceOrder entity = ServiceOrderTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.serviceOrderToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        ServiceOrder entity = ServiceOrderTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.serviceOrderToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(ServiceOrderNotFoundException.class, () -> {
            ServiceOrderDto expected = ServiceOrderTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<ServiceOrder> repositoryResponse = ServiceOrderTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<ServiceOrderDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::serviceOrderToDto), actual);
    }

    @Test
    void updateTest() {
        ServiceOrder entity = ServiceOrderTestUtil.buildEntity();
        ServiceOrderDto dto = ServiceOrderTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.serviceOrderToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(ServiceOrderNotFoundException.class, () -> {
            ServiceOrderDto expected = ServiceOrderTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        ServiceOrder entity = ServiceOrderTestUtil.buildEntity();
        ServiceOrderDto dto = ServiceOrderTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.serviceOrderToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(ServiceOrderNotFoundException.class, () -> {
            ServiceOrderDto expected = ServiceOrderTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
