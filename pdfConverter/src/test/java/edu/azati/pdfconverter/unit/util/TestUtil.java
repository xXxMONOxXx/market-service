package edu.azati.pdfconverter.unit.util;

import edu.azati.pdfconverter.dto.*;
import edu.azati.pdfconverter.dto.enums.Status;
import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@UtilityClass
public class TestUtil {

    @UtilityClass
    public class PdfConverterServiceUtil {

        public final byte[] EMPTY_BYTE_ARRAY = new byte[]{};
        private final Long AMOUNT = 1000L;
        private final String FILENAME = "filename";
        private final Status CURRENT_STATUS = Status.COMPLETE;
        private final LocalDateTime CREATED_AT = LocalDateTime.MIN;
        private final Long PRODUCT_ID = 1L;
        private final String PRODUCT_NAME = "product name";
        private final Integer PRODUCT_QUANTITY = 10;
        private final Integer PRODUCT_PRICE = 100;
        private final String COUNTRY = "country";
        private final String CITY = "city";
        private final String STREET = "street";
        private final String APARTMENT = "apartment";
        private final String POSTCODE = "postcode";
        private final String SERVICE_NAME = "service name";
        private final Integer SERVICE_PRICE = 1000;

        public InvoiceDto buildDtoForProductOrder() {
            return InvoiceDto.builder()
                    .amount(AMOUNT)
                    .payments(buildPayments())
                    .products(buildProducts())
                    .address(buildAddress())
                    .filename(FILENAME)
                    .build();
        }

        public InvoiceDto buildDtoForServiceOrder() {
            return InvoiceDto.builder()
                    .amount(AMOUNT)
                    .payments(buildPayments())
                    .service(buildService())
                    .address(buildAddress())
                    .filename(FILENAME)
                    .build();
        }

        private List<PaymentDto> buildPayments() {
            return Collections.singletonList(
                    PaymentDto.builder()
                            .currentStatus(CURRENT_STATUS)
                            .createdAt(CREATED_AT)
                            .build()
            );
        }

        private List<ProductDto> buildProducts() {
            return Collections.singletonList(
                    ProductDto.builder()
                            .productId(PRODUCT_ID)
                            .name(PRODUCT_NAME)
                            .quantity(PRODUCT_QUANTITY)
                            .price(PRODUCT_PRICE)
                            .build()
            );
        }

        private ServiceDto buildService() {
            return ServiceDto.builder()
                    .name(SERVICE_NAME)
                    .price(SERVICE_PRICE)
                    .build();

        }

        private AddressDto buildAddress() {
            return AddressDto.builder()
                    .country(COUNTRY)
                    .city(CITY)
                    .street(STREET)
                    .apartment(APARTMENT)
                    .postcode(POSTCODE)
                    .build();
        }
    }
}
