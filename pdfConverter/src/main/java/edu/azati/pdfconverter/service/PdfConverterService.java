package edu.azati.pdfconverter.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import edu.azati.pdfconverter.dto.*;
import edu.azati.pdfconverter.exception.PdfConverterException;
import edu.azati.pdfconverter.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PdfConverterService {

    private final ObjectMapper mapper;

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(Constants.PAYMENT_DATE_FORMAT);

    public byte[] generatePdfFromJsonInvoice(String message) {
        try {
            InvoiceDto invoice = mapper.readValue(message, InvoiceDto.class);
            return createPdfFromInvoice(invoice);
        } catch (JsonProcessingException e) {
            log.error(e.getMessage());
            throw new PdfConverterException(e.getMessage());
        }


    }

    private byte[] createPdfFromInvoice(InvoiceDto invoice) {
        Document document = new Document();
        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
            PdfWriter writer = PdfWriter.getInstance(document, byteArrayOutputStream);
            document.open();
            document.setPageSize(PageSize.A4);
            document.addTitle(invoice.getFilename() == null
                    ? Constants.PDF_FILE_NAME
                    : String.format("%s%s", invoice.getFilename(), Constants.PDF_FILENAME_ENDING));
            Paragraph amountParagraph = new Paragraph();
            amountParagraph.add(String.format("%s: %s", Constants.PRICE, invoice.getAmount().toString()));
            document.add(amountParagraph);
            document.add(new Paragraph("\n"));
            if (invoice.getService() == null) {
                addProductsToDocument(invoice.getProducts(), document);
            } else {
                addServiceToDocument(invoice.getService(), document);
            }
            addAddressToDocument(invoice.getAddress(), document);
            document.add(new Paragraph("\n"));
            addPaymentsToDocument(invoice.getPayments(), document);
            writer.close();
            return byteArrayOutputStream.toByteArray();
        } catch (DocumentException | IOException e) {
            log.error(e.getMessage());
            throw new PdfConverterException(e.getMessage());
        } finally {
            document.close();
        }
    }

    private void addPaymentsToDocument(List<PaymentDto> payments, Document document) throws DocumentException {
        float[] pointColumnWidths = {
                Constants.PAYMENTS_COLUMN_WIDTH,
                Constants.PAYMENTS_COLUMN_WIDTH,
        };
        PdfPTable table = new PdfPTable(pointColumnWidths);
        table.addCell(Constants.PAYMENT_STATUS);
        table.addCell(Constants.PAYMENT_DATE);
        payments.forEach(payment -> {
            table.addCell(payment.getCurrentStatus().getValue());
            table.addCell(dateTimeFormatter.format(payment.getCreatedAt()));
        });
        document.add(table);
    }

    private void addAddressToDocument(AddressDto address, Document document) throws DocumentException {

        document.add(new Paragraph(String.format("%s: %s", Constants.COUNTRY, address.getCountry())));
        document.add(new Paragraph(String.format("%s: %s", Constants.CITY, address.getCity())));
        document.add(new Paragraph(String.format("%s: %s", Constants.STREET, address.getStreet())));
        document.add(new Paragraph(String.format("%s: %s", Constants.APARTMENT, address.getApartment())));
        document.add(new Paragraph(String.format("%s: %s", Constants.POSTCODE, address.getPostcode())));
    }

    private void addProductsToDocument(List<ProductDto> products, Document document) throws DocumentException {
        float[] pointColumnWidths = {
                Constants.PRODUCTS_COLUMN_WIDTH,
                Constants.PRODUCTS_COLUMN_WIDTH,
                Constants.PRODUCTS_COLUMN_WIDTH,
                Constants.PRODUCTS_COLUMN_WIDTH,
        };
        PdfPTable table = new PdfPTable(pointColumnWidths);
        table.addCell(Constants.CELL_ID);
        table.addCell(Constants.CELL_NAME);
        table.addCell(Constants.CELL_PRICE);
        table.addCell(Constants.CELL_QUANTITY);
        products.forEach(product -> {
            table.addCell(product.getProductId().toString());
            table.addCell(product.getName());
            table.addCell(product.getPrice().toString());
            table.addCell(product.getQuantity().toString());
        });
        document.add(table);
    }

    private void addServiceToDocument(ServiceDto service, Document document) throws DocumentException {
        document.add(new Paragraph(String.format("%s: %s", Constants.SERVICE_NAME, service.getName())));
        document.add(new Paragraph(String.format("%s: %s", Constants.PRICE, service.getPrice())));
    }
}
