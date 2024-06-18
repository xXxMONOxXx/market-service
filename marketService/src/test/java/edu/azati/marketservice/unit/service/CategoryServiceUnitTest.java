package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.exception.CategoryNotFoundException;
import edu.azati.marketservice.mapper.CategoryMapper;
import edu.azati.marketservice.model.Category;
import edu.azati.marketservice.repository.CategoryRepository;
import edu.azati.marketservice.service.impl.CategoryService;
import edu.azati.marketservice.unit.util.CategoryTestUtil;
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

@SpringBootTest(classes = {CategoryMapper.class})
class CategoryServiceUnitTest {

    @Mock
    private CategoryRepository repository;

    @Spy
    private CategoryMapper mapper;

    @InjectMocks
    private CategoryService service;

    @Test
    void saveTest() {
        CategoryDto request = CategoryTestUtil.buildDtoWithoutId();
        Category entity = CategoryTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.categoryToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        Category entity = CategoryTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.categoryToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(CategoryNotFoundException.class, () -> {
            CategoryDto expected = CategoryTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<Category> repositoryResponse = CategoryTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<CategoryDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::categoryToDto), actual);
    }

    @Test
    void updateTest() {
        Category entity = CategoryTestUtil.buildEntity();
        CategoryDto dto = CategoryTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.categoryToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(CategoryNotFoundException.class, () -> {
            CategoryDto expected = CategoryTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        Category entity = CategoryTestUtil.buildEntity();
        CategoryDto dto = CategoryTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.categoryToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(CategoryNotFoundException.class, () -> {
            CategoryDto expected = CategoryTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
