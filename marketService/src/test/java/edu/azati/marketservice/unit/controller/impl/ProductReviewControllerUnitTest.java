package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.ProductReviewController;
import edu.azati.marketservice.dto.ProductReviewDto;
import edu.azati.marketservice.service.impl.ProductReviewService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.ProductReviewTestUtil;
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

@WebMvcTest(ProductReviewController.class)
class ProductReviewControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductReviewService productReviewService;

    @Test
    void saveTest() throws Exception {
        ProductReviewDto productReviewRequest = ProductReviewTestUtil.buildDtoWithoutId();
        ProductReviewDto productReviewResponse = ProductReviewTestUtil.buildDto();
        when(productReviewService.save(productReviewRequest)).thenReturn(productReviewResponse);
        mockMvc.perform(post("/products-reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(productReviewRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productReviewResponse.getId()))
                .andExpect(jsonPath("$.data.productId").value(productReviewResponse.getProductId()))
                .andExpect(jsonPath("$.data.comment").value(productReviewResponse.getComment()))
                .andExpect(jsonPath("$.data.rating").value(productReviewResponse.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productReviewResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productReviewResponse.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productReviewResponse.getCreatedBy()));
        verify(productReviewService, times(1)).save(productReviewRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        ProductReviewDto productReview = ProductReviewTestUtil.buildDto();
        when(productReviewService.findById(productReview.getId())).thenReturn(productReview);
        mockMvc.perform(get("/products-reviews/{id}", productReview.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productReview.getId()))
                .andExpect(jsonPath("$.data.productId").value(productReview.getProductId()))
                .andExpect(jsonPath("$.data.comment").value(productReview.getComment()))
                .andExpect(jsonPath("$.data.rating").value(productReview.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productReview.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productReview.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productReview.getCreatedBy()));
        verify(productReviewService, times(1)).findById(productReview.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<ProductReviewDto> page = ProductReviewTestUtil.buildFindAllPage(request);
        when(productReviewService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/products-reviews")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productReviewService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        ProductReviewDto productReview = ProductReviewTestUtil.buildDto();
        ProductReviewDto productReviewUpdateDto = ProductReviewTestUtil.buildDtoWithoutId();
        when(productReviewService.update(productReviewUpdateDto, productReview.getId())).thenReturn(productReview);
        mockMvc.perform(patch("/products-reviews/{id}", productReview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(productReviewUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productReview.getId()))
                .andExpect(jsonPath("$.data.productId").value(productReview.getProductId()))
                .andExpect(jsonPath("$.data.comment").value(productReview.getComment()))
                .andExpect(jsonPath("$.data.rating").value(productReview.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productReview.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productReview.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productReview.getCreatedBy()));
        verify(productReviewService, times(1)).update(productReviewUpdateDto, productReview.getId());
    }

    @Test
    void deleteTest() throws Exception {
        ProductReviewDto productReview = ProductReviewTestUtil.buildDto();
        when(productReviewService.delete(productReview.getId())).thenReturn(productReview);
        mockMvc.perform(delete("/products-reviews/{id}", productReview.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productReview.getId()))
                .andExpect(jsonPath("$.data.productId").value(productReview.getProductId()))
                .andExpect(jsonPath("$.data.comment").value(productReview.getComment()))
                .andExpect(jsonPath("$.data.rating").value(productReview.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productReview.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productReview.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productReview.getCreatedBy()));
        verify(productReviewService, times(1)).delete(productReview.getId());
    }
}
