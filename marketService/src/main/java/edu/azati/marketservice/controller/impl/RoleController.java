package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.RoleService;
import edu.azati.marketservice.util.Constants;
import edu.azati.marketservice.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
@Tag(
        name = "RoleController",
        description = "Controller responsible for standard read operations on role entities"
)
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find roles",
            description = "Find all roles"
    )
    public ResponseEntity<Object> findAll() {
        return ResponseHandler.generateResponse(
                Constants.FOUND_ALL,
                HttpStatus.OK,
                roleService.findAll()
        );
    }
}
