package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.ProductController;
import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.service.impl.ProductService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.ProductTestUtil;
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

@WebMvcTest(ProductController.class)
class ProductControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    void saveTest() throws Exception {
        ProductDto productRequest = ProductTestUtil.buildDtoWithoutId();
        ProductDto productResponse = ProductTestUtil.buildDto();
        when(productService.save(productRequest)).thenReturn(productResponse);
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(productRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(productResponse.getId()))
                .andExpect(jsonPath("$.data.sellerId").value(productResponse.getSellerId()))
                .andExpect(jsonPath("$.data.name").value(productResponse.getName()))
                .andExpect(jsonPath("$.data.price").value(productResponse.getPrice()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(productResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(productResponse.getUpdatedAt())))
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(productResponse.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(productResponse.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(productResponse.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.priceHistory[0].id")
                        .value(productResponse.getPriceHistory().get(0).getId()))
                .andExpect(jsonPath("$.data.priceHistory[0].price")
                        .value(productResponse.getPriceHistory().get(0).getPrice()))
                .andExpect(jsonPath("$.data.priceHistory[0].createdAt")
                        .value(JsonUtil.formatLocalDateTime(productResponse.getPriceHistory().get(0).getCreatedAt())))
                .andExpect(jsonPath("$.data.productDetails").value(productResponse.getProductDetails()));
        verify(productService, times(1)).save(productRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        ProductDto product = ProductTestUtil.buildDto();
        when(productService.findById(product.getId())).thenReturn(product);
        mockMvc.perform(get("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(product.getId()))
                .andExpect(jsonPath("$.data.sellerId").value(product.getSellerId()))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(product.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(product.getUpdatedAt())))
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(product.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(product.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(product.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.priceHistory[0].id")
                        .value(product.getPriceHistory().get(0).getId()))
                .andExpect(jsonPath("$.data.priceHistory[0].price")
                        .value(product.getPriceHistory().get(0).getPrice()))
                .andExpect(jsonPath("$.data.priceHistory[0].createdAt")
                        .value(JsonUtil.formatLocalDateTime(product.getPriceHistory().get(0).getCreatedAt())))
                .andExpect(jsonPath("$.data.productDetails").value(product.getProductDetails()));
        verify(productService, times(1)).findById(product.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<ProductDto> page = ProductTestUtil.buildFindAllPage(request);
        when(productService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/products")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        ProductDto product = ProductTestUtil.buildDto();
        ProductDto productUpdateDto = ProductTestUtil.buildDtoWithoutId();
        when(productService.update(productUpdateDto, product.getId())).thenReturn(product);
        mockMvc.perform(patch("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(productUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(product.getId()))
                .andExpect(jsonPath("$.data.sellerId").value(product.getSellerId()))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(product.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(product.getUpdatedAt())))
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(product.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(product.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(product.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.priceHistory[0].id")
                        .value(product.getPriceHistory().get(0).getId()))
                .andExpect(jsonPath("$.data.priceHistory[0].price")
                        .value(product.getPriceHistory().get(0).getPrice()))
                .andExpect(jsonPath("$.data.priceHistory[0].createdAt")
                        .value(JsonUtil.formatLocalDateTime(product.getPriceHistory().get(0).getCreatedAt())))
                .andExpect(jsonPath("$.data.productDetails").value(product.getProductDetails()));
        verify(productService, times(1)).update(productUpdateDto, product.getId());
    }

    @Test
    void deleteTest() throws Exception {
        ProductDto product = ProductTestUtil.buildDto();
        when(productService.delete(product.getId())).thenReturn(product);
        mockMvc.perform(delete("/products/{id}", product.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(product.getId()))
                .andExpect(jsonPath("$.data.sellerId").value(product.getSellerId()))
                .andExpect(jsonPath("$.data.name").value(product.getName()))
                .andExpect(jsonPath("$.data.price").value(product.getPrice()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(product.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(product.getUpdatedAt())))
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(product.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(product.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(product.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.priceHistory[0].id")
                        .value(product.getPriceHistory().get(0).getId()))
                .andExpect(jsonPath("$.data.priceHistory[0].price")
                        .value(product.getPriceHistory().get(0).getPrice()))
                .andExpect(jsonPath("$.data.priceHistory[0].createdAt")
                        .value(JsonUtil.formatLocalDateTime(product.getPriceHistory().get(0).getCreatedAt())))
                .andExpect(jsonPath("$.data.productDetails").value(product.getProductDetails()));
        verify(productService, times(1)).delete(product.getId());
    }
}
