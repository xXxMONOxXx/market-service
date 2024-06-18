package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.ServiceController;
import edu.azati.marketservice.dto.ServiceDto;
import edu.azati.marketservice.service.impl.ServiceEntityService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.ServiceTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ServiceController.class)
class ServiceControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServiceEntityService serviceEntityService;

    @Test
    void saveTest() throws Exception {
        ServiceDto serviceRequest = ServiceTestUtil.buildDtoWithoutId();
        ServiceDto serviceResponse = ServiceTestUtil.buildDto();
        when(serviceEntityService.save(serviceRequest)).thenReturn(serviceResponse);
        mockMvc.perform(post("/services")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(serviceRequest)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(serviceResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(serviceResponse.getName()))
                .andExpect(jsonPath("$.data.description").value(serviceResponse.getDescription()))
                .andExpect(jsonPath("$.data.workerId").value(serviceResponse.getWorkerId()))
                .andExpect(jsonPath("$.data.price").value(serviceResponse.getPrice()))
                .andExpect(jsonPath("$.data.categories").isArray())
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(serviceResponse.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(serviceResponse.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(serviceResponse.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(serviceResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(serviceResponse.getUpdatedAt())));
        verify(serviceEntityService, times(1)).save(serviceRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        ServiceDto service = ServiceTestUtil.buildDto();
        when(serviceEntityService.findById(service.getId())).thenReturn(service);
        mockMvc.perform(get("/services/{id}", service.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(service.getId()))
                .andExpect(jsonPath("$.data.name").value(service.getName()))
                .andExpect(jsonPath("$.data.description").value(service.getDescription()))
                .andExpect(jsonPath("$.data.workerId").value(service.getWorkerId()))
                .andExpect(jsonPath("$.data.price").value(service.getPrice()))
                .andExpect(jsonPath("$.data.categories").isArray())
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(service.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(service.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(service.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(service.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(service.getUpdatedAt())));
        verify(serviceEntityService, times(1)).findById(service.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<ServiceDto> page = ServiceTestUtil.buildFindAllPage(request);
        when(serviceEntityService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/services")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(serviceEntityService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        ServiceDto service = ServiceTestUtil.buildDto();
        ServiceDto serviceUpdateDto = ServiceTestUtil.buildDtoWithoutId();
        when(serviceEntityService.update(serviceUpdateDto, service.getId())).thenReturn(service);
        mockMvc.perform(patch("/services/{id}", service.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(serviceUpdateDto)))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(service.getId()))
                .andExpect(jsonPath("$.data.name").value(service.getName()))
                .andExpect(jsonPath("$.data.description").value(service.getDescription()))
                .andExpect(jsonPath("$.data.workerId").value(service.getWorkerId()))
                .andExpect(jsonPath("$.data.price").value(service.getPrice()))
                .andExpect(jsonPath("$.data.categories").isArray())
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(service.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(service.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(service.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(service.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(service.getUpdatedAt())));
        verify(serviceEntityService, times(1)).update(serviceUpdateDto, service.getId());
    }

    @Test
    void deleteTest() throws Exception {
        ServiceDto service = ServiceTestUtil.buildDto();
        when(serviceEntityService.delete(service.getId())).thenReturn(service);
        mockMvc.perform(delete("/services/{id}", service.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(jsonPath("$.data.id").value(service.getId()))
                .andExpect(jsonPath("$.data.name").value(service.getName()))
                .andExpect(jsonPath("$.data.description").value(service.getDescription()))
                .andExpect(jsonPath("$.data.workerId").value(service.getWorkerId()))
                .andExpect(jsonPath("$.data.price").value(service.getPrice()))
                .andExpect(jsonPath("$.data.categories").isArray())
                .andExpect(jsonPath("$.data.categories[0].id")
                        .value(service.getCategories().get(0).getId()))
                .andExpect(jsonPath("$.data.categories[0].name")
                        .value(service.getCategories().get(0).getName()))
                .andExpect(jsonPath("$.data.categories[0].description")
                        .value(service.getCategories().get(0).getDescription()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(service.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(service.getUpdatedAt())));
        verify(serviceEntityService, times(1)).delete(service.getId());
    }
}
