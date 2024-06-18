package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.ProductReviewDto;
import edu.azati.marketservice.exception.ReviewNotFoundException;
import edu.azati.marketservice.mapper.ProductReviewMapper;
import edu.azati.marketservice.model.ProductReview;
import edu.azati.marketservice.repository.ProductReviewRepository;
import edu.azati.marketservice.service.impl.ProductReviewService;
import edu.azati.marketservice.unit.util.ProductReviewTestUtil;
import org.junit.jupiter.api.Test;
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

@SpringBootTest(classes = {ProductReviewMapper.class})
class ProductReviewServiceUnitTest {

    @Mock
    private ProductReviewRepository repository;

    @Spy
    private ProductReviewMapper mapper;

    @InjectMocks
    private ProductReviewService service;

    @Test
    void saveTest() {
        ProductReviewDto request = ProductReviewTestUtil.buildDtoWithoutTimestampsAndId();
        ProductReview entity = ProductReviewTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.reviewToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        ProductReview entity = ProductReviewTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.reviewToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(ReviewNotFoundException.class, () -> {
            ProductReviewDto expected = ProductReviewTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<ProductReview> repositoryResponse = ProductReviewTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<ProductReviewDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::reviewToDto), actual);
    }

    @Test
    void updateTest() {
        ProductReview entity = ProductReviewTestUtil.buildEntity();
        ProductReviewDto dto = ProductReviewTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.reviewToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(ReviewNotFoundException.class, () -> {
            ProductReviewDto expected = ProductReviewTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        ProductReview entity = ProductReviewTestUtil.buildEntity();
        ProductReviewDto dto = ProductReviewTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.reviewToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(ReviewNotFoundException.class, () -> {
            ProductReviewDto expected = ProductReviewTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
