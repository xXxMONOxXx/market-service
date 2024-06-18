package edu.azati.marketservice.client;

import edu.azati.marketservice.dto.ProductOrderMailRequestDto;
import edu.azati.marketservice.dto.ServiceOrderMailRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@FeignClient(name = "mail-sender", url = "${mail.sender.module.url}")
public interface MailSenderClient {

    @PostMapping("/mail/product-order")
    ResponseEntity<Object> sendOrderStatusUpdateMailForProductOrder(
            @RequestBody ProductOrderMailRequestDto request);

    @PostMapping("/mail/service-order")
    ResponseEntity<Object> sendOrderStatusUpdateMailForProductOrder(
            @RequestBody ServiceOrderMailRequestDto request);
}
