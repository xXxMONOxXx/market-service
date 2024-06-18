package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.exception.ProductOrderNotFoundException;
import edu.azati.marketservice.mapper.ProductOrderMapper;
import edu.azati.marketservice.model.ProductOrder;
import edu.azati.marketservice.repository.ProductOrderRepository;
import edu.azati.marketservice.service.impl.ProductOrderService;
import edu.azati.marketservice.unit.util.ProductOrderTestUtil;
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

@SpringBootTest(classes = {ProductOrderMapper.class})
class ProductOrderServiceUnitTest {

    @Mock
    private ProductOrderRepository repository;

    @Spy
    private ProductOrderMapper mapper = Mappers.getMapper(ProductOrderMapper.class);

    @InjectMocks
    private ProductOrderService service;

    @Test
    void saveTest() {
        ProductOrderDto request = ProductOrderTestUtil.buildDtoWithoutTimestampsAndId();
        ProductOrder entity = ProductOrderTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.productOrderToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        ProductOrder entity = ProductOrderTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.productOrderToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(ProductOrderNotFoundException.class, () -> {
            ProductOrderDto expected = ProductOrderTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<ProductOrder> repositoryResponse = ProductOrderTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<ProductOrderDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::productOrderToDto), actual);
    }

    @Test
    void updateTest() {
        ProductOrder entity = ProductOrderTestUtil.buildEntity();
        ProductOrderDto dto = ProductOrderTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.productOrderToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(ProductOrderNotFoundException.class, () -> {
            ProductOrderDto expected = ProductOrderTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        ProductOrder entity = ProductOrderTestUtil.buildEntity();
        ProductOrderDto dto = ProductOrderTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.productOrderToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(ProductOrderNotFoundException.class, () -> {
            ProductOrderDto expected = ProductOrderTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
