package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.client.MailSenderClient;
import edu.azati.marketservice.dto.*;
import edu.azati.marketservice.model.enums.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Log4j2
@Service
@RequiredArgsConstructor
public class OrderMailService {

    private final UserService userService;

    private final MailSenderClient mailSenderClient;

    private final ProductService productService;

    public void sendStatusChangeMessageForProductOrder(Status oldStatus, Status newStatus, Long orderId,
                                                       ProductOrderDto order, LocalDateTime updatedAt) {
        UserDto user = userService.findById(order.getCreatedBy());
        order.getProducts().forEach(product ->
                product.setName(productService.findById(product.getProductId()).getName()));
        mailSenderClient.sendOrderStatusUpdateMailForProductOrder(
                ProductOrderMailRequestDto.builder()
                        .oldStatus(oldStatus)
                        .newStatus(newStatus)
                        .orderId(orderId)
                        .emailReceiver(user.getEmail())
                        .updateAt(updatedAt)
                        .products(order.getProducts())
                        .address(order.getAddress())
                        .build());
    }

    public void sendStatusChangeMessageForServiceOrder(Status oldStatus, Status newStatus, Long orderId,
                                                       ServiceOrderDto order, LocalDateTime updatedAt) {
        UserDto user = userService.findById(order.getCreatedBy());
        mailSenderClient.sendOrderStatusUpdateMailForProductOrder(
                ServiceOrderMailRequestDto.builder()
                        .oldStatus(oldStatus)
                        .newStatus(newStatus)
                        .orderId(orderId)
                        .emailReceiver(user.getEmail())
                        .updateAt(updatedAt)
                        .serviceId(order.getService().getId())
                        .serviceName(order.getService().getName())
                        .address(order.getAddress())
                        .build());
    }
}
