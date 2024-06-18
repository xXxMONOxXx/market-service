package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.ServiceOrderDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.ServiceOrderService;
import edu.azati.marketservice.util.Constants;
import edu.azati.marketservice.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/service-orders")
@Tag(
        name = "ServiceOrderController",
        description = "Controller responsible for standard CRUD operations on service order entities"
)
public class ServiceOrderController implements BaseController<ServiceOrderDto> {

    private final ServiceOrderService serviceOrderService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create new service order"
    )
    public ResponseEntity<Object> save(@RequestBody ServiceOrderDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                serviceOrderService.save(dto)
        );
    }

    @Override
    @LogRequestExecutionTime
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete service order by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                serviceOrderService.delete(id)
        );
    }

    @Override
    @GetMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Find service order by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                serviceOrderService.findById(id)
        );
    }

    @Override
    @LogRequestExecutionTime
    @PatchMapping("/{id}")
    @Operation(
            summary = "Update service order by id"
    )
    public ResponseEntity<Object> update(@RequestBody ServiceOrderDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                serviceOrderService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find service orders",
            description = "Find service orders with pagination, default page number - 0, default page size - 4"
    )
    public ResponseEntity<Object> findAll(@RequestParam(name = "page", defaultValue = "0")
                                          @Parameter(description = "Number of page")
                                          int pageNumber,
                                          @RequestParam(name = "size", defaultValue = "4")
                                          @Parameter(description = "Page size")
                                          int pageSize) {
        return ResponseHandler.generateResponse(
                Constants.PAGE_CREATED,
                HttpStatus.OK,
                serviceOrderService.findAll(pageNumber, pageSize)
        );
    }
}
