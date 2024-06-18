package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.ServiceReviewController;
import edu.azati.marketservice.dto.ServiceReviewDto;
import edu.azati.marketservice.service.impl.ServiceReviewService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.ServiceReviewTestUtil;
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

@WebMvcTest(ServiceReviewController.class)
class ServiceReviewControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceReviewService serviceReviewService;

    @Test
    void saveTest() throws Exception {
        ServiceReviewDto serviceReviewRequest = ServiceReviewTestUtil.buildDtoWithoutId();
        ServiceReviewDto serviceReviewResponse = ServiceReviewTestUtil.buildDto();
        when(serviceReviewService.save(serviceReviewRequest)).thenReturn(serviceReviewResponse);
        mockMvc.perform(post("/service-reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(serviceReviewRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceReviewResponse.getId()))
                .andExpect(jsonPath("$.data.serviceId").value(serviceReviewResponse.getServiceId()))
                .andExpect(jsonPath("$.data.comment").value(serviceReviewResponse.getComment()))
                .andExpect(jsonPath("$.data.rating").value(serviceReviewResponse.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReviewResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReviewResponse.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceReviewResponse.getId()));
        verify(serviceReviewService, times(1)).save(serviceReviewRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        ServiceReviewDto serviceReview = ServiceReviewTestUtil.buildDto();
        when(serviceReviewService.findById(serviceReview.getId())).thenReturn(serviceReview);
        mockMvc.perform(get("/service-reviews/{id}", serviceReview.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceReview.getId()))
                .andExpect(jsonPath("$.data.serviceId").value(serviceReview.getServiceId()))
                .andExpect(jsonPath("$.data.comment").value(serviceReview.getComment()))
                .andExpect(jsonPath("$.data.rating").value(serviceReview.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReview.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReview.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceReview.getId()));
        verify(serviceReviewService, times(1)).findById(serviceReview.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<ServiceReviewDto> page = ServiceReviewTestUtil.buildFindAllPage(request);
        when(serviceReviewService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/service-reviews")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(serviceReviewService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        ServiceReviewDto serviceReview = ServiceReviewTestUtil.buildDto();
        ServiceReviewDto serviceReviewUpdateDto = ServiceReviewTestUtil.buildDtoWithoutId();
        when(serviceReviewService.update(serviceReviewUpdateDto, serviceReview.getId())).thenReturn(serviceReview);
        mockMvc.perform(patch("/service-reviews/{id}", serviceReview.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(serviceReviewUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceReview.getId()))
                .andExpect(jsonPath("$.data.serviceId").value(serviceReview.getServiceId()))
                .andExpect(jsonPath("$.data.comment").value(serviceReview.getComment()))
                .andExpect(jsonPath("$.data.rating").value(serviceReview.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReview.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReview.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceReview.getId()));
        verify(serviceReviewService, times(1)).update(serviceReviewUpdateDto, serviceReview.getId());
    }

    @Test
    void deleteTest() throws Exception {
        ServiceReviewDto serviceReview = ServiceReviewTestUtil.buildDto();
        when(serviceReviewService.delete(serviceReview.getId())).thenReturn(serviceReview);
        mockMvc.perform(delete("/service-reviews/{id}", serviceReview.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceReview.getId()))
                .andExpect(jsonPath("$.data.serviceId").value(serviceReview.getServiceId()))
                .andExpect(jsonPath("$.data.comment").value(serviceReview.getComment()))
                .andExpect(jsonPath("$.data.rating").value(serviceReview.getRating().toString()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReview.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceReview.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceReview.getId()));
        verify(serviceReviewService, times(1)).delete(serviceReview.getId());
    }
}
