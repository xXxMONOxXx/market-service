package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.exception.CategoryNotFoundException;
import edu.azati.marketservice.mapper.CategoryMapper;
import edu.azati.marketservice.model.Category;
import edu.azati.marketservice.repository.CategoryRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class CategoryService implements BaseService<CategoryDto> {

    private final CategoryMapper categoryMapper;

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto save(CategoryDto dto) {
        log.info("Saving new category");
        return categoryMapper.categoryToDto(categoryRepository.save(categoryMapper.dtoToCategory(dto)));
    }

    @Override
    public CategoryDto delete(Long id) {
        log.info("Deleting category from database");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryRepository.deleteById(id);
        return categoryMapper.categoryToDto(category);
    }

    @Override
    public CategoryDto findById(Long id) {
        return categoryMapper.categoryToDto(categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id)));
    }

    @Override
    public CategoryDto update(CategoryDto dto, Long id) {
        log.info("Updating existing category");
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(id));
        categoryMapper.updateCategoryFromDto(dto, category);
        categoryRepository.save(category);
        return categoryMapper.categoryToDto(category);
    }

    @Cacheable("findAllCategories")
    @Override
    public Page<CategoryDto> findAll(int pageNumber, int pageSize) {
        return categoryRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(categoryMapper::categoryToDto);
    }
}
