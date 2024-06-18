package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.ServiceOrderController;
import edu.azati.marketservice.dto.ServiceOrderDto;
import edu.azati.marketservice.service.impl.ServiceOrderService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.ServiceOrderTestUtil;
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

@WebMvcTest(ServiceOrderController.class)
class ServiceOrderControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceOrderService serviceOrderService;

    @Test
    void saveTest() throws Exception {
        ServiceOrderDto serviceOrderRequest = ServiceOrderTestUtil.buildDtoWithoutId();
        ServiceOrderDto serviceOrderResponse = ServiceOrderTestUtil.buildDto();
        when(serviceOrderService.save(serviceOrderRequest)).thenReturn(serviceOrderResponse);
        mockMvc.perform(post("/services-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(serviceOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceOrderResponse.getId()))
                .andExpect(jsonPath("$.data.description").value(serviceOrderResponse.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrderResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrderResponse.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceOrderResponse.getCreatedBy()))
                .andExpect(jsonPath("$.data.service.id").value(serviceOrderResponse.getService().getId()))
                .andExpect(jsonPath("$.data.service.name").value(serviceOrderResponse.getService().getName()))
                .andExpect(jsonPath("$.data.service.description").value(serviceOrderResponse.getService().getDescription()))
                .andExpect(jsonPath("$.data.service.workerId").value(serviceOrderResponse.getService().getWorkerId()))
                .andExpect(jsonPath("$.data.service.price").value(serviceOrderResponse.getService().getPrice()))
                .andExpect(jsonPath("$.data.service.categories").isArray())
                .andExpect(jsonPath("$.data.service.categories[0].id")
                        .value(serviceOrderResponse.getService().getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.service.categories[0].name")
                        .value(serviceOrderResponse.getService().getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.service.categories[0].description")
                        .value(serviceOrderResponse.getService().getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.service.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrderResponse.getService().getCreatedAt())))
                .andExpect(jsonPath("$.data.service.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrderResponse.getService().getUpdatedAt())));
        verify(serviceOrderService, times(1)).save(serviceOrderRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        ServiceOrderDto serviceOrder = ServiceOrderTestUtil.buildDto();
        when(serviceOrderService.findById(serviceOrder.getId())).thenReturn(serviceOrder);
        mockMvc.perform(get("/services-orders/{id}", serviceOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceOrder.getId()))
                .andExpect(jsonPath("$.data.description").value(serviceOrder.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceOrder.getCreatedBy()))
                .andExpect(jsonPath("$.data.service.id").value(serviceOrder.getService().getId()))
                .andExpect(jsonPath("$.data.service.name").value(serviceOrder.getService().getName()))
                .andExpect(jsonPath("$.data.service.description").value(serviceOrder.getService().getDescription()))
                .andExpect(jsonPath("$.data.service.workerId").value(serviceOrder.getService().getWorkerId()))
                .andExpect(jsonPath("$.data.service.price").value(serviceOrder.getService().getPrice()))
                .andExpect(jsonPath("$.data.service.categories").isArray())
                .andExpect(jsonPath("$.data.service.categories[0].id")
                        .value(serviceOrder.getService().getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.service.categories[0].name")
                        .value(serviceOrder.getService().getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.service.categories[0].description")
                        .value(serviceOrder.getService().getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.service.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getService().getCreatedAt())))
                .andExpect(jsonPath("$.data.service.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getService().getUpdatedAt())));
        verify(serviceOrderService, times(1)).findById(serviceOrder.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<ServiceOrderDto> page = ServiceOrderTestUtil.buildFindAllPage(request);
        when(serviceOrderService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/services-orders")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(serviceOrderService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        ServiceOrderDto serviceOrder = ServiceOrderTestUtil.buildDto();
        ServiceOrderDto serviceOrderUpdateDto = ServiceOrderTestUtil.buildDtoWithoutId();
        when(serviceOrderService.update(serviceOrderUpdateDto, serviceOrder.getId())).thenReturn(serviceOrder);
        mockMvc.perform(patch("/services-orders/{id}", serviceOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(serviceOrderUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceOrder.getId()))
                .andExpect(jsonPath("$.data.description").value(serviceOrder.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceOrder.getCreatedBy()))
                .andExpect(jsonPath("$.data.service.id").value(serviceOrder.getService().getId()))
                .andExpect(jsonPath("$.data.service.name").value(serviceOrder.getService().getName()))
                .andExpect(jsonPath("$.data.service.description").value(serviceOrder.getService().getDescription()))
                .andExpect(jsonPath("$.data.service.workerId").value(serviceOrder.getService().getWorkerId()))
                .andExpect(jsonPath("$.data.service.price").value(serviceOrder.getService().getPrice()))
                .andExpect(jsonPath("$.data.service.categories").isArray())
                .andExpect(jsonPath("$.data.service.categories[0].id")
                        .value(serviceOrder.getService().getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.service.categories[0].name")
                        .value(serviceOrder.getService().getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.service.categories[0].description")
                        .value(serviceOrder.getService().getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.service.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getService().getCreatedAt())))
                .andExpect(jsonPath("$.data.service.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getService().getUpdatedAt())));
        verify(serviceOrderService, times(1)).update(serviceOrderUpdateDto, serviceOrder.getId());
    }

    @Test
    void deleteTest() throws Exception {
        ServiceOrderDto serviceOrder = ServiceOrderTestUtil.buildDto();
        when(serviceOrderService.delete(serviceOrder.getId())).thenReturn(serviceOrder);
        mockMvc.perform(delete("/services-orders/{id}", serviceOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(serviceOrder.getId()))
                .andExpect(jsonPath("$.data.description").value(serviceOrder.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(serviceOrder.getCreatedBy()))
                .andExpect(jsonPath("$.data.service.id").value(serviceOrder.getService().getId()))
                .andExpect(jsonPath("$.data.service.name").value(serviceOrder.getService().getName()))
                .andExpect(jsonPath("$.data.service.description").value(serviceOrder.getService().getDescription()))
                .andExpect(jsonPath("$.data.service.workerId").value(serviceOrder.getService().getWorkerId()))
                .andExpect(jsonPath("$.data.service.price").value(serviceOrder.getService().getPrice()))
                .andExpect(jsonPath("$.data.service.categories").isArray())
                .andExpect(jsonPath("$.data.service.categories[0].id")
                        .value(serviceOrder.getService().getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.service.categories[0].name")
                        .value(serviceOrder.getService().getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.service.categories[0].description")
                        .value(serviceOrder.getService().getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.service.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getService().getCreatedAt())))
                .andExpect(jsonPath("$.data.service.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceOrder.getService().getUpdatedAt())));
        verify(serviceOrderService, times(1)).delete(serviceOrder.getId());
    }
}
