package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.InvoiceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/pdf")
@Tag(
        name = "PdfConverterController",
        description = "Controller responsible for creating pdf files of requested objects"
)
public class PdfConverterController {

    private final InvoiceService service;

    @GetMapping("/order")
    @LogRequestExecutionTime
    @Operation(
            summary = "Convert order's payment to pdf"
    )
    public ResponseEntity<byte[]> convertToPdf(@RequestParam(name = "order_id") Long orderId,
                                               @Nullable @RequestParam(name = "filename") String filename) {
        return ResponseEntity.ok(service.getPdfOfOrderPayment(orderId, filename));
    }
}
