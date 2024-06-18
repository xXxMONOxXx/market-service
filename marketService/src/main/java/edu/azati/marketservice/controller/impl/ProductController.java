package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.ProductDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.ProductService;
import edu.azati.marketservice.util.Constants;
import edu.azati.marketservice.util.ResponseHandler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
@Tag(
        name = "ProductController",
        description = "Controller responsible for standard CRUD operations on product entities"
)
public class ProductController implements BaseController<ProductDto> {

    private final ProductService productService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create new product"
    )
    public ResponseEntity<Object> save(@RequestBody ProductDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                productService.save(dto)
        );
    }

    @Override
    @LogRequestExecutionTime
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete product by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                productService.delete(id)
        );
    }

    @Override
    @GetMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Get product by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                productService.findById(id)
        );
    }

    @Override
    @PatchMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Update product by id"
    )
    public ResponseEntity<Object> update(@RequestBody ProductDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                productService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find products",
            description = "Find products with pagination, default page number - 0, default page size - 4"
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
                productService.findAll(pageNumber, pageSize)
        );
    }

    @LogRequestExecutionTime
    @PostMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Add image to product",
            description = "Add image to product, could be preview image(main image) or not" +
                    "if other image is preview image, and user adding new image, that is have preview" +
                    "flag as true, new image will be set as preview and old will be a regular one"
    )
    public ResponseEntity<Object> addImage(@RequestPart MultipartFile file,
                                           @RequestParam Boolean isPreview,
                                           @PathVariable Long id) {
        productService.addImage(file, isPreview, id);
        return ResponseHandler.generateResponse(
                Constants.IMAGE_ADDED,
                HttpStatus.OK,
                null
        );
    }

    @LogRequestExecutionTime
    @DeleteMapping("/image/{id}")
    @Operation(
            summary = "Delete product image",
            description = "Delete product image by image id"
    )
    public ResponseEntity<Object> removeImage(@PathVariable Long id) {
        productService.deleteImage(id);
        return ResponseHandler.generateResponse(
                Constants.IMAGE_DELETED,
                HttpStatus.OK,
                null
        );
    }

    @GetMapping("/findBy")
    public ResponseEntity<Object> findBy(@RequestParam(name = "page", defaultValue = "0")
                                         @Parameter(description = "Number of page")
                                         int pageNumber,
                                         @RequestParam(name = "size", defaultValue = "4")
                                         @Parameter(description = "Page size")
                                         int pageSize,
                                         @RequestParam(name = "sort", defaultValue = "")
                                         @Parameter(description = "Sort by asc or desc for price")
                                         String sortByAsc,
                                         @RequestParam(name = "seller", defaultValue = "")
                                         @Parameter(description = "Find by seller id")
                                         Long sellerId,
                                         @RequestParam(name = "categories", defaultValue = "")
                                         @Parameter(description = "Find by categoriesIds")
                                         List<Long> categoriesIds) {
        return ResponseHandler.generateResponse(
                Constants.PAGE_CREATED,
                HttpStatus.OK,
                productService.findBy(pageNumber, pageSize, sortByAsc, sellerId, categoriesIds)
        );
    }
}
