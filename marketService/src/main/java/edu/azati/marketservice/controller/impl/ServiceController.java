package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.ServiceDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.ServiceEntityService;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/services")
@Tag(
        name = "ServiceController",
        description = "Controller responsible for standard CRUD operations on service entities"
)
public class ServiceController implements BaseController<ServiceDto> {

    private final ServiceEntityService serviceEntityService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create service"
    )
    public ResponseEntity<Object> save(@RequestBody ServiceDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                serviceEntityService.save(dto)
        );
    }

    @Override
    @LogRequestExecutionTime
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete service by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                serviceEntityService.delete(id)
        );
    }

    @Override
    @GetMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Get service by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                serviceEntityService.findById(id)
        );
    }

    @Override
    @PatchMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Update service by id"
    )
    public ResponseEntity<Object> update(@RequestBody ServiceDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                serviceEntityService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find services",
            description = "Find services with pagination, default page number - 0, default page size - 4"
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
                serviceEntityService.findAll(pageNumber, pageSize)
        );
    }

    @LogRequestExecutionTime
    @PostMapping(value = "/image/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Add image to service",
            description = "Add image to service, could be preview image(main image) or not" +
                    "if other image is preview image, and user adding new image, that is have preview" +
                    "flag as true, new image will be set as preview and old will be a regular one"
    )
    public ResponseEntity<Object> addImage(@RequestPart MultipartFile file,
                                           @RequestParam Boolean isPreview,
                                           @PathVariable Long id) {
        serviceEntityService.addImage(file, isPreview, id);
        return ResponseHandler.generateResponse(
                Constants.IMAGE_ADDED,
                HttpStatus.OK,
                null
        );
    }

    @LogRequestExecutionTime
    @DeleteMapping("/image/{id}")
    @Operation(
            summary = "Delete service image",
            description = "Delete service image by image id"
    )
    public ResponseEntity<Object> removeImage(@PathVariable Long id) {
        serviceEntityService.deleteImage(id);
        return ResponseHandler.generateResponse(
                Constants.IMAGE_DELETED,
                HttpStatus.OK,
                null
        );
    }
}
