package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.PdfConverterController;
import edu.azati.marketservice.service.impl.InvoiceService;
import edu.azati.marketservice.unit.util.constants.PdfConverterTestConstants;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PdfConverterController.class)
class PdfConverterControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private InvoiceService invoiceService;

    @Test
    void convertToPdfTest() throws Exception {
        when(invoiceService.getPdfOfOrderPayment(PdfConverterTestConstants.ID, PdfConverterTestConstants.ID.toString()))
                .thenReturn(PdfConverterTestConstants.BYTES_RETURN_EXAMPLE);
        mockMvc.perform(get("/pdf/order")
                        .param("order_id", PdfConverterTestConstants.ID.toString())
                        .param("filename", PdfConverterTestConstants.ID.toString())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(invoiceService, times(1))
                .getPdfOfOrderPayment(PdfConverterTestConstants.ID, PdfConverterTestConstants.ID.toString());
    }

}
