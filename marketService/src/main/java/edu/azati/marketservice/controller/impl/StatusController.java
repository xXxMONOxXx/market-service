package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.StatusService;
import edu.azati.marketservice.util.Constants;
import edu.azati.marketservice.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/statuses")
@Tag(
        name = "StatusController",
        description = "Controller responsible for read operations on status entities"
)
public class StatusController {

    private final StatusService statusService;

    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find statuses",
            description = "Find all statuses"
    )
    public ResponseEntity<Object> findAll() {
        return ResponseHandler.generateResponse(
                Constants.FOUND_ALL,
                HttpStatus.OK,
                statusService.findAll()
        );
    }
}
