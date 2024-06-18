package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.mapper.PaymentMapper;
import edu.azati.marketservice.repository.PaymentRepository;
import edu.azati.marketservice.service.impl.PaymentService;
import edu.azati.marketservice.service.impl.ProductOrderService;
import edu.azati.marketservice.service.impl.ProductService;
import edu.azati.marketservice.service.impl.ServiceOrderService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {PaymentMapper.class})
class PaymentServiceUnitTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private ProductOrderService productOrderService;

    @Mock
    private ServiceOrderService serviceOrderService;

    @Mock
    private ProductService productService;

    @Spy
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentService paymentService;

    @Test
    void getOrderAmountServiceTest() {
        Long orderId = PaymentServiceTestConstants.ID;
        Long expected = PaymentServiceTestConstants.AMOUNT;
        when(productOrderService.orderIsForProducts(orderId)).thenReturn(false);
        when(serviceOrderService.getAmount(orderId)).thenReturn(expected.intValue());
        assertEquals(expected, paymentService.getOrderAmount(orderId));
        verify(productOrderService, times(0)).getAmount(orderId);
        verify(serviceOrderService, times(1)).getAmount(orderId);
    }

    @Test
    void getOrderAmountProductTest() {
        Long orderId = PaymentServiceTestConstants.ID;
        Long expected = PaymentServiceTestConstants.AMOUNT;
        when(productOrderService.orderIsForProducts(orderId)).thenReturn(true);
        when(productOrderService.getAmount(orderId)).thenReturn(expected);
        assertEquals(expected, paymentService.getOrderAmount(orderId));
        verify(productOrderService, times(1)).getAmount(orderId);
        verify(serviceOrderService, times(0)).getAmount(orderId);
    }
}
