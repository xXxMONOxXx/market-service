package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.service.impl.RevisionService;
import edu.azati.marketservice.util.Constants;
import edu.azati.marketservice.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/history")
@Tag(
        name = "RevisionController",
        description = "Controller responsible showing for revisions (history) of entities"
)
public class RevisionController {

    private RevisionService service;

    @GetMapping("/{id}")
    @Operation(
            summary = "Get history of product"
    )
    public ResponseEntity<Object> getProductHistory(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                service.getProductHistory(id)
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get history of user"
    )
    public ResponseEntity<Object> getUserHistory(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                service.getUserHistory(id)
        );
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Get history of service"
    )
    public ResponseEntity<Object> getServiceHistory(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                service.getServiceHistory(id)
        );
    }
}
