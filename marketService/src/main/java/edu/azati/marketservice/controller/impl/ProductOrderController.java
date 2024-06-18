package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.ProductOrderDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.ProductOrderService;
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
@RequestMapping("/product-orders")
@Tag(
        name = "ProductOrderController",
        description = "Controller responsible for standard CRUD operations on product order entities"
)
public class ProductOrderController implements BaseController<ProductOrderDto> {

    private final ProductOrderService productOrderService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create new products order"
    )
    public ResponseEntity<Object> save(@RequestBody ProductOrderDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                productOrderService.save(dto)
        );
    }

    @Override
    @LogRequestExecutionTime
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete products order by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                productOrderService.delete(id)
        );
    }

    @Override
    @GetMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Get product order by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                productOrderService.findById(id)
        );
    }

    @Override
    @PatchMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Update product order by id"
    )
    public ResponseEntity<Object> update(@RequestBody ProductOrderDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                productOrderService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find product orders",
            description = "Find product orders with pagination, default page number - 0, default page size - 4"
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
                productOrderService.findAll(pageNumber, pageSize)
        );
    }
}
