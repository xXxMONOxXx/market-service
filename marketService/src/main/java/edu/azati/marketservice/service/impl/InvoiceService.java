package edu.azati.marketservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.azati.marketservice.dto.InvoiceDto;
import edu.azati.marketservice.dto.InvoiceProductDto;
import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.exception.InvoiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class InvoiceService {

    private final ProducerService producerService;

    private final PaymentService paymentService;

    private final ProductOrderService productOrderService;

    private final ServiceOrderService serviceOrderService;

    private final ProductService productService;

    private final ObjectMapper objectMapper;

    public byte[] getPdfOfOrderPayment(Long orderId, String filename) {
        try {
            log.info(String.format("Got request for conversion of order with id: %s", orderId));
            InvoiceDto invoice = InvoiceDto.builder()
                    .filename(filename)
                    .payments(paymentService.getOrderPayments(orderId))
                    .amount(paymentService.getOrderAmount(orderId))
                    .address(productOrderService.findById(orderId).getAddress())
                    .build();
            if (productOrderService.orderIsForProducts(orderId)) {
                invoice.setProducts(productOrderService.findById(orderId).getProducts()
                        .stream().map(product -> {
                            ProductDto productDto = productService.findById(product.getProductId());
                            return InvoiceProductDto.builder()
                                    .productId(product.getProductId())
                                    .name(productDto.getName())
                                    .quantity(product.getQuantity())
                                    .price(productDto.getPrice())
                                    .build();
                        }).toList());
            } else {
                invoice.setService(serviceOrderService.findById(orderId).getService());
            }
            return producerService.sendToQueue(objectMapper.writeValueAsString(invoice));
        } catch (JsonProcessingException e) {
            log.error(String.format("Could not create json from invoice of order id: %s", orderId));
            throw new InvoiceException("Could not create json from invoice");
        }
    }
}
