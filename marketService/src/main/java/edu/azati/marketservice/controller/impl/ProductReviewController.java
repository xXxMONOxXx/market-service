package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.ProductReviewDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.ProductReviewService;
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
@RequestMapping("/product-reviews")
@Tag(
        name = "ProductReviewController",
        description = "Controller responsible for standard CRUD operations on product review entities"
)
public class ProductReviewController implements BaseController<ProductReviewDto> {

    private final ProductReviewService reviewService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create product review"
    )
    public ResponseEntity<Object> save(@RequestBody ProductReviewDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                reviewService.save(dto)
        );
    }

    @Override
    @LogRequestExecutionTime
    @DeleteMapping(("/{id}"))
    @Operation(
            summary = "Delete product review by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                reviewService.delete(id)
        );
    }

    @Override
    @LogRequestExecutionTime
    @GetMapping(("/{id}"))
    @Operation(
            summary = "Find product review by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                reviewService.findById(id)
        );
    }

    @Override
    @LogRequestExecutionTime
    @PatchMapping("/{id}")
    @Operation(
            summary = "Update product review by id"
    )
    public ResponseEntity<Object> update(@RequestBody ProductReviewDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                reviewService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find product reviews",
            description = "Find product reviews with pagination, default page number - 0, default page size - 4"
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
                reviewService.findAll(pageNumber, pageSize)
        );
    }
}
