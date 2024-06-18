package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.CategoryDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.CategoryService;
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
@RequestMapping("/categories")
@Tag(
        name = "CategoryController",
        description = "Controller responsible for standard CRUD operations on category entities"
)
public class CategoryController implements BaseController<CategoryDto> {

    private final CategoryService categoryService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create new category"
    )
    public ResponseEntity<Object> save(@RequestBody CategoryDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                categoryService.save(dto)
        );
    }

    @Override
    @DeleteMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Delete category by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                categoryService.delete(id)
        );
    }

    @Override
    @GetMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Find category by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                categoryService.findById(id)
        );
    }

    @Override
    @PatchMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Update category by id"
    )
    public ResponseEntity<Object> update(@RequestBody CategoryDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                categoryService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find categories",
            description = "Find categories with pagination, default page number - 0, default page size - 4"
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
                categoryService.findAll(pageNumber, pageSize)
        );
    }
}
