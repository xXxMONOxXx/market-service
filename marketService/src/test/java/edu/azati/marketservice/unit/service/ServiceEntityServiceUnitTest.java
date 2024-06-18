package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.ServiceDto;
import edu.azati.marketservice.exception.ServiceNotFoundException;
import edu.azati.marketservice.mapper.ServiceMapper;
import edu.azati.marketservice.model.Service;
import edu.azati.marketservice.repository.ServiceRepository;
import edu.azati.marketservice.service.impl.ImageService;
import edu.azati.marketservice.service.impl.ServiceEntityService;
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

@SpringBootTest(classes = {ServiceMapper.class})
class ServiceEntityServiceUnitTest {

    @Mock
    private ServiceRepository repository;

    @Mock
    private ImageService imageService;

    @Spy
    private ServiceMapper mapper = Mappers.getMapper(ServiceMapper.class);

    @InjectMocks
    private ServiceEntityService service;

    @Test
    void saveTest() {
        ServiceDto request = ServiceTestUtil.buildDtoWithoutTimestampsAndId();
        Service entity = ServiceTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.serviceToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        Service entity = ServiceTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.serviceToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(ServiceNotFoundException.class, () -> {
            ServiceDto expected = ServiceTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<Service> repositoryResponse = ServiceTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<ServiceDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::serviceToDto), actual);
    }

    @Test
    void updateTest() {
        Service entity = ServiceTestUtil.buildEntity();
        ServiceDto dto = ServiceTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.serviceToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(ServiceNotFoundException.class, () -> {
            ServiceDto expected = ServiceTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        Service entity = ServiceTestUtil.buildEntity();
        ServiceDto dto = ServiceTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.serviceToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(ServiceNotFoundException.class, () -> {
            ServiceDto expected = ServiceTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
