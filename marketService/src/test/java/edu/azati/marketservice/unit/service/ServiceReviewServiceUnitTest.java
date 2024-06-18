package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.ServiceReviewDto;
import edu.azati.marketservice.exception.ReviewNotFoundException;
import edu.azati.marketservice.mapper.ServiceReviewMapper;
import edu.azati.marketservice.model.ServiceReview;
import edu.azati.marketservice.repository.ServiceReviewRepository;
import edu.azati.marketservice.service.impl.ServiceReviewService;
import edu.azati.marketservice.unit.util.ServiceReviewTestUtil;
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

@SpringBootTest(classes = {ServiceReviewMapper.class})
class ServiceReviewServiceUnitTest {

    @Mock
    private ServiceReviewRepository repository;

    @Spy
    private ServiceReviewMapper mapper = Mappers.getMapper(ServiceReviewMapper.class);

    @InjectMocks
    private ServiceReviewService service;

    @Test
    void saveTest() {
        ServiceReviewDto request = ServiceReviewTestUtil.buildDtoWithoutTimestampsAndId();
        ServiceReview entity = ServiceReviewTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.reviewToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        ServiceReview entity = ServiceReviewTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.reviewToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(ReviewNotFoundException.class, () -> {
            ServiceReviewDto expected = ServiceReviewTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<ServiceReview> repositoryResponse = ServiceReviewTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<ServiceReviewDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::reviewToDto), actual);
    }

    @Test
    void updateTest() {
        ServiceReview entity = ServiceReviewTestUtil.buildEntity();
        ServiceReviewDto dto = ServiceReviewTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.reviewToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(ReviewNotFoundException.class, () -> {
            ServiceReviewDto expected = ServiceReviewTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        ServiceReview entity = ServiceReviewTestUtil.buildEntity();
        ServiceReviewDto dto = ServiceReviewTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.reviewToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(ReviewNotFoundException.class, () -> {
            ServiceReviewDto expected = ServiceReviewTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
