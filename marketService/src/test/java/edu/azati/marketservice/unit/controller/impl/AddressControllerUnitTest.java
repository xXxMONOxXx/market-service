package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.AddressController;
import edu.azati.marketservice.dto.AddressDto;
import edu.azati.marketservice.service.impl.AddressService;
import edu.azati.marketservice.unit.util.AddressTestUtil;
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

@WebMvcTest(AddressController.class)
class AddressControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AddressService addressService;

    @Test
    void saveTest() throws Exception {
        AddressDto addressRequest = AddressTestUtil.buildDtoWithoutId();
        AddressDto addressResponse = AddressTestUtil.buildDto();
        when(addressService.save(addressRequest)).thenReturn(addressResponse);
        mockMvc.perform(post("/addresses")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(addressRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(addressResponse.getId()))
                .andExpect(jsonPath("$.data.country").value(addressResponse.getCountry()))
                .andExpect(jsonPath("$.data.city").value(addressResponse.getCity()))
                .andExpect(jsonPath("$.data.street").value(addressResponse.getStreet()))
                .andExpect(jsonPath("$.data.apartment").value(addressResponse.getApartment()))
                .andExpect(jsonPath("$.data.postcode").value(addressResponse.getPostcode()))
                .andExpect(jsonPath("$.data.createdAt")
                        .value(JsonUtil.formatLocalDateTime(addressResponse.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(addressResponse.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(addressResponse.getCreatedBy()));
        verify(addressService, times(1)).save(addressRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        AddressDto address = AddressTestUtil.buildDto();
        when(addressService.findById(address.getId())).thenReturn(address);
        mockMvc.perform(get("/addresses/{id}", address.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(address.getId()))
                .andExpect(jsonPath("$.data.country").value(address.getCountry()))
                .andExpect(jsonPath("$.data.city").value(address.getCity()))
                .andExpect(jsonPath("$.data.street").value(address.getStreet()))
                .andExpect(jsonPath("$.data.apartment").value(address.getApartment()))
                .andExpect(jsonPath("$.data.postcode").value(address.getPostcode()))
                .andExpect(jsonPath("$.data.createdAt").value(JsonUtil.formatLocalDateTime(address.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt").value(JsonUtil.formatLocalDateTime(address.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(address.getCreatedBy()));
        verify(addressService, times(1)).findById(address.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<AddressDto> page = AddressTestUtil.buildFindAllPage(request);
        when(addressService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/addresses")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(addressService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        AddressDto address = AddressTestUtil.buildDto();
        AddressDto addressUpdateDto = AddressTestUtil.buildDtoWithoutId();
        when(addressService.update(addressUpdateDto, address.getId())).thenReturn(address);
        mockMvc.perform(patch("/addresses/{id}", address.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(addressUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(address.getId()))
                .andExpect(jsonPath("$.data.country").value(address.getCountry()))
                .andExpect(jsonPath("$.data.city").value(address.getCity()))
                .andExpect(jsonPath("$.data.street").value(address.getStreet()))
                .andExpect(jsonPath("$.data.apartment").value(address.getApartment()))
                .andExpect(jsonPath("$.data.postcode").value(address.getPostcode()))
                .andExpect(jsonPath("$.data.createdAt").value(JsonUtil.formatLocalDateTime(address.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt").value(JsonUtil.formatLocalDateTime(address.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(address.getCreatedBy()));
        verify(addressService, times(1)).update(addressUpdateDto, address.getId());
    }

    @Test
    void deleteTest() throws Exception {
        AddressDto address = AddressTestUtil.buildDto();
        when(addressService.delete(address.getId())).thenReturn(address);
        mockMvc.perform(delete("/addresses/{id}", address.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(address.getId()))
                .andExpect(jsonPath("$.data.country").value(address.getCountry()))
                .andExpect(jsonPath("$.data.city").value(address.getCity()))
                .andExpect(jsonPath("$.data.street").value(address.getStreet()))
                .andExpect(jsonPath("$.data.apartment").value(address.getApartment()))
                .andExpect(jsonPath("$.data.postcode").value(address.getPostcode()))
                .andExpect(jsonPath("$.data.createdAt").value(JsonUtil.formatLocalDateTime(address.getCreatedAt())))
                .andExpect(jsonPath("$.data.updatedAt").value(JsonUtil.formatLocalDateTime(address.getUpdatedAt())))
                .andExpect(jsonPath("$.data.createdBy").value(address.getCreatedBy()));
        verify(addressService, times(1)).delete(address.getId());
    }
}
