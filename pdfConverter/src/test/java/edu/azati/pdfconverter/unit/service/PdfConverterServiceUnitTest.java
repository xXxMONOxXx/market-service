package edu.azati.pdfconverter.unit.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.azati.pdfconverter.config.ObjectMapperConfig;
import edu.azati.pdfconverter.dto.InvoiceDto;
import edu.azati.pdfconverter.service.PdfConverterService;
import edu.azati.pdfconverter.unit.util.TestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

import static org.junit.jupiter.api.Assertions.assertNotSame;

@SpringBootTest(classes = {})
@Import(ObjectMapperConfig.class)
class PdfConverterServiceUnitTest {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PdfConverterService pdfConverterService;

    @Test
    void generatePdfFromJsonProductInvoice() throws Exception {
        InvoiceDto invoice = TestUtil.PdfConverterServiceUtil.buildDtoForProductOrder();
        String message = objectMapper.writeValueAsString(invoice);
        assertNotSame(TestUtil.PdfConverterServiceUtil.EMPTY_BYTE_ARRAY,
                pdfConverterService.generatePdfFromJsonInvoice(message));
    }

}
