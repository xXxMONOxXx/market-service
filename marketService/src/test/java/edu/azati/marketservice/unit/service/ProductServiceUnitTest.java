package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.exception.ProductNotFoundException;
import edu.azati.marketservice.mapper.ProductMapper;
import edu.azati.marketservice.model.Product;
import edu.azati.marketservice.repository.ProductRepository;
import edu.azati.marketservice.service.impl.ImageService;
import edu.azati.marketservice.service.impl.ProductService;
import edu.azati.marketservice.unit.util.ProductTestUtil;
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

@SpringBootTest(classes = {ProductMapper.class})
class ProductServiceUnitTest {

    @Mock
    private ProductRepository repository;

    @Mock
    private ImageService imageService;

    @Spy
    private ProductMapper mapper = Mappers.getMapper(ProductMapper.class);

    @InjectMocks
    private ProductService service;

    @Test
    void saveTest() {
        ProductDto request = ProductTestUtil.buildDtoWithoutTimestampsAndId();
        Product entity = ProductTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.productToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        Product entity = ProductTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.productToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(ProductNotFoundException.class, () -> {
            ProductDto expected = ProductTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<Product> repositoryResponse = ProductTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<ProductDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::productToDto), actual);
    }

    @Test
    void updateTest() {
        Product entity = ProductTestUtil.buildEntity();
        ProductDto dto = ProductTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.productToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(ProductNotFoundException.class, () -> {
            ProductDto expected = ProductTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        Product entity = ProductTestUtil.buildEntity();
        ProductDto dto = ProductTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.productToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(ProductNotFoundException.class, () -> {
            ProductDto expected = ProductTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
