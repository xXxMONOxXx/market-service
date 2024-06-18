package edu.azati.mailnotifier.controller;

import edu.azati.mailnotifier.dto.ProductOrderMailRequestDto;
import edu.azati.mailnotifier.dto.ServiceOrderMailRequestDto;
import edu.azati.mailnotifier.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @PostMapping("/product-order")
    public ResponseEntity<Object> sendMailForChangedProductOrder(@RequestBody ProductOrderMailRequestDto request) {
        mailService.sendMailUpdateForProductOrder(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/service-order")
    public ResponseEntity<Object> sendMailForChangedServiceOrder(@RequestBody ServiceOrderMailRequestDto request) {
        mailService.sendMailUpdateForServiceOrder(request);
        return ResponseEntity.ok().build();
    }
}
