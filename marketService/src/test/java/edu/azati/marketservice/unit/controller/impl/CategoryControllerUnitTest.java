package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.CategoryController;
import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.service.impl.CategoryService;
import edu.azati.marketservice.unit.util.CategoryTestUtil;
import edu.azati.marketservice.unit.util.JsonUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CategoryController.class)
class CategoryControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @Test
    void saveTest() throws Exception {
        CategoryDto categoryRequest = CategoryTestUtil.buildDtoWithoutId();
        CategoryDto categoryResponse = CategoryTestUtil.buildDto();
        when(categoryService.save(categoryRequest)).thenReturn(categoryResponse);
        mockMvc.perform(post("/categories")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(categoryRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(categoryResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(categoryResponse.getName()))
                .andExpect(jsonPath("$.data.description").value(categoryResponse.getDescription()));
        verify(categoryService, times(1)).save(categoryRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        CategoryDto category = CategoryTestUtil.buildDto();
        when(categoryService.findById(category.getId())).thenReturn(category);
        mockMvc.perform(get("/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(category.getId()))
                .andExpect(jsonPath("$.data.name").value(category.getName()))
                .andExpect(jsonPath("$.data.description").value(category.getDescription()));
        verify(categoryService, times(1)).findById(category.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<CategoryDto> page = CategoryTestUtil.buildFindAllPage(request);
        when(categoryService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/categories")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(categoryService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        CategoryDto category = CategoryTestUtil.buildDto();
        CategoryDto categoryUpdateDto = CategoryTestUtil.buildDtoWithoutId();
        when(categoryService.update(categoryUpdateDto, category.getId())).thenReturn(category);
        mockMvc.perform(patch("/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(categoryUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(category.getId()))
                .andExpect(jsonPath("$.data.name").value(category.getName()))
                .andExpect(jsonPath("$.data.description").value(category.getDescription()));
        verify(categoryService, times(1)).update(categoryUpdateDto, category.getId());
    }

    @Test
    void deleteTest() throws Exception {
        CategoryDto category = CategoryTestUtil.buildDto();
        when(categoryService.delete(category.getId())).thenReturn(category);
        mockMvc.perform(delete("/categories/{id}", category.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(category.getId()))
                .andExpect(jsonPath("$.data.name").value(category.getName()))
                .andExpect(jsonPath("$.data.description").value(category.getDescription()));
        verify(categoryService, times(1)).delete(category.getId());
    }
}
