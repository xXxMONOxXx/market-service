package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.ProductOrderController;
import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.service.impl.ProductOrderService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.ProductOrderTestUtil;
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

@WebMvcTest(ProductOrderController.class)
class ProductOrderControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductOrderService productOrderService;

    @Test
    void saveTest() throws Exception {
        ProductOrderDto productOrderRequest = ProductOrderTestUtil.buildDtoWithoutId();
        ProductOrderDto productOrderResponse = ProductOrderTestUtil.buildDto();
        when(productOrderService.save(productOrderRequest)).thenReturn(productOrderResponse);
        mockMvc.perform(post("/products-orders")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(productOrderRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productOrderResponse.getId()))
                .andExpect(jsonPath("$.data.description").value(productOrderResponse.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productOrderResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productOrderResponse.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productOrderResponse.getCreatedBy()))
                .andExpect(jsonPath("$.data.products[0].productId")
                        .value(productOrderResponse.getProducts().get(0).getProductId()))
                .andExpect(jsonPath("$.data.products[0].quantity")
                        .value(productOrderResponse.getProducts().get(0).getQuantity()));
        verify(productOrderService, times(1)).save(productOrderRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        ProductOrderDto productOrder = ProductOrderTestUtil.buildDto();
        when(productOrderService.findById(productOrder.getId())).thenReturn(productOrder);
        mockMvc.perform(get("/products-orders/{id}", productOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productOrder.getId()))
                .andExpect(jsonPath("$.data.description").value(productOrder.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productOrder.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productOrder.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productOrder.getCreatedBy()))
                .andExpect(jsonPath("$.data.products[0].productId")
                        .value(productOrder.getProducts().get(0).getProductId()))
                .andExpect(jsonPath("$.data.products[0].quantity")
                        .value(productOrder.getProducts().get(0).getQuantity()));
        verify(productOrderService, times(1)).findById(productOrder.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<ProductOrderDto> page = ProductOrderTestUtil.buildFindAllPage(request);
        when(productOrderService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/products-orders")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productOrderService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        ProductOrderDto productOrder = ProductOrderTestUtil.buildDto();
        ProductOrderDto productUpdateDto = ProductOrderTestUtil.buildDtoWithoutId();
        when(productOrderService.update(productUpdateDto, productOrder.getId())).thenReturn(productOrder);
        mockMvc.perform(patch("/products-orders/{id}", productOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productOrder.getId()))
                .andExpect(jsonPath("$.data.description").value(productOrder.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productOrder.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productOrder.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productOrder.getCreatedBy()))
                .andExpect(jsonPath("$.data.products[0].productId")
                        .value(productOrder.getProducts().get(0).getProductId()))
                .andExpect(jsonPath("$.data.products[0].quantity")
                        .value(productOrder.getProducts().get(0).getQuantity()));
        verify(productOrderService, times(1)).update(productUpdateDto, productOrder.getId());
    }

    @Test
    void deleteTest() throws Exception {
        ProductOrderDto productOrder = ProductOrderTestUtil.buildDto();
        when(productOrderService.delete(productOrder.getId())).thenReturn(productOrder);
        mockMvc.perform(delete("/products-orders/{id}", productOrder.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productOrder.getId()))
                .andExpect(jsonPath("$.data.description").value(productOrder.getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productOrder.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productOrder.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(productOrder.getCreatedBy()))
                .andExpect(jsonPath("$.data.products[0].productId")
                        .value(productOrder.getProducts().get(0).getProductId()))
                .andExpect(jsonPath("$.data.products[0].quantity")
                        .value(productOrder.getProducts().get(0).getQuantity()));
        verify(productOrderService, times(1)).delete(productOrder.getId());
    }
}
