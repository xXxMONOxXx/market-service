package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.StatusController;
import edu.azati.marketservice.service.impl.StatusService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StatusController.class)
class StatusControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StatusService statusService;

    @Test
    void findAllTest() throws Exception {
        mockMvc.perform(get("/statuses")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(statusService, times(1)).findAll();
    }
}
