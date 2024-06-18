package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.AddressDto;
import edu.azati.marketservice.exception.AddressNotFoundException;
import edu.azati.marketservice.mapper.AddressMapper;
import edu.azati.marketservice.model.Address;
import edu.azati.marketservice.repository.AddressRepository;
import edu.azati.marketservice.service.impl.AddressService;
import edu.azati.marketservice.unit.util.AddressTestUtil;
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

@SpringBootTest(classes = {AddressMapper.class})
class AddressServiceUnitTest {

    @Mock
    private AddressRepository repository;

    @Spy
    private AddressMapper mapper = Mappers.getMapper(AddressMapper.class);

    @InjectMocks
    private AddressService service;

    @Test
    void saveTest() {
        AddressDto request = AddressTestUtil.buildDtoWithoutTimestampsAndId();
        Address entity = AddressTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.addressToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        Address entity = AddressTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.addressToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(AddressNotFoundException.class, () -> {
            AddressDto expected = AddressTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<Address> repositoryResponse = AddressTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<AddressDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::addressToDto), actual);
    }

    @Test
    void updateTest() {
        Address entity = AddressTestUtil.buildEntity();
        AddressDto dto = AddressTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.addressToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(AddressNotFoundException.class, () -> {
            AddressDto expected = AddressTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        Address entity = AddressTestUtil.buildEntity();
        AddressDto dto = AddressTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.addressToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(AddressNotFoundException.class, () -> {
            AddressDto expected = AddressTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
