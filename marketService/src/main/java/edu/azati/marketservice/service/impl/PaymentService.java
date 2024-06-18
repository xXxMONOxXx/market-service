package edu.azati.marketservice.service.impl;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.param.ChargeCreateParams;
import edu.azati.marketservice.dto.ChargeDto;
import edu.azati.marketservice.dto.PaymentDto;
import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.exception.PaymentException;
import edu.azati.marketservice.mapper.PaymentMapper;
import edu.azati.marketservice.model.Payment;
import edu.azati.marketservice.model.enums.Status;
import edu.azati.marketservice.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    private final ProductOrderService productOrderService;

    private final ServiceOrderService serviceOrderService;

    private final ProductService productService;

    private final PaymentMapper paymentMapper;

    @Value("${stripe.public.key}")
    private String stripePublicKey;

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    public List<PaymentDto> getOrderPayments(Long orderId) {
        return paymentRepository.findByOrderId(orderId).stream().map(paymentMapper::paymentToDto).toList();
    }

    public Status charge(ChargeDto chargeDto) {
        log.info("Start of payment charge");
        try {
            if (!chargeCanPayForOrder(chargeDto)) {
                return Status.FAILED;
            }
            Stripe.apiKey = stripeSecretKey;
            ChargeCreateParams params =
                    ChargeCreateParams.builder()
                            .setAmount(chargeDto.getAmount())
                            .setCurrency(chargeDto.getCurrency().getValue())
                            .setSource(chargeDto.getStripeToken())
                            .build();
            Charge charge = Charge.create(params);
            Status status = Boolean.TRUE.equals(charge.getPaid()) ? Status.COMPLETE : Status.FAILED;
            if (status.equals(Status.COMPLETE)) {
                changeOrderStatus(chargeDto.getOrderId());
                changeNumberOfProducts(chargeDto);
            }
            savePayment(chargeDto, status);
            log.info(String.format("End of payment charge, payment status: %s", status.getValue()));
            return status;
        } catch (StripeException e) {
            log.error("Could not create payment charge");
            throw new PaymentException(e.getMessage());
        }
    }

    public Long getOrderAmount(Long orderId) {
        Long amount;
        if (Boolean.TRUE.equals(productOrderService.orderIsForProducts(orderId))) {
            amount = productOrderService.getAmount(orderId);
        } else {
            amount = Long.valueOf(serviceOrderService.getAmount(orderId));
        }
        return amount;
    }

    private void changeNumberOfProducts(ChargeDto chargeDto) {
        ProductOrderDto productOrderDto = productOrderService.findById(chargeDto.getOrderId());
        if (Boolean.TRUE.equals(productOrderDto.getOrderHasProducts())) {
            productOrderDto.getProducts().forEach(product -> {
                Long productId = product.getProductId();
                ProductDto productDto = productService.findById(productId);
                productDto.setQuantity(productDto.getQuantity() - product.getQuantity());
                productService.update(productDto, productId);
            });
        }
    }

    private void savePayment(ChargeDto chargeDto, Status status) {
        Payment payment = Payment.builder()
                .userId(chargeDto.getUserId())
                .currentStatus(status)
                .orderId(chargeDto.getOrderId())
                .stripeToken(chargeDto.getStripeToken())
                .build();
        paymentRepository.save(payment);
    }

    private boolean chargeCanPayForOrder(ChargeDto chargeDto) {
        Long amount = getOrderAmount(chargeDto.getOrderId());
        log.info(String.format("Required amount: %s, charging amount: %s", amount, chargeDto.getAmount()));
        return amount.equals(chargeDto.getAmount());
    }

    private void changeOrderStatus(Long id) {
        ProductOrderDto orderDto = ProductOrderDto.builder()
                .currentStatus(Status.PROCESSING)
                .build();
        productOrderService.update(orderDto, id);

    }
}
