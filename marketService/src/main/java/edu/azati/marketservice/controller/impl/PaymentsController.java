package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.dto.ChargeDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.PaymentService;
import edu.azati.marketservice.util.Constants;
import edu.azati.marketservice.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
@Tag(
        name = "PaymentsController",
        description = "Controller responsible for payments of orders"
)
public class PaymentsController {

    private final PaymentService service;

    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create new payment"
    )
    public ResponseEntity<Object> charge(@RequestBody ChargeDto chargeRequest) {
        return ResponseHandler.generateResponse(
                Constants.PAYMENT_SUCCESS,
                HttpStatus.CREATED,
                service.charge(chargeRequest)
        );
    }

}
