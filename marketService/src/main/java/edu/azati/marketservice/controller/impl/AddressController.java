package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.AddressDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.AddressService;
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
@RequestMapping("/addresses")
@Tag(
        name = "AddressController",
        description = "Controller responsible for standard CRUD operations on address entities"
)
public class AddressController implements BaseController<AddressDto> {

    private final AddressService addressService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create new address"
    )
    public ResponseEntity<Object> save(@RequestBody AddressDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.OK,
                addressService.save(dto));
    }

    @Override
    @DeleteMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Delete address by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                addressService.delete(id)
        );
    }

    @Override
    @GetMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Find address by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                addressService.findById(id)
        );
    }

    @Override
    @PatchMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Update address by id"
    )
    public ResponseEntity<Object> update(@RequestBody AddressDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.CREATED,
                addressService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find addresses",
            description = "Find addresses with pagination, default page number - 0, default page size - 4"
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
                addressService.findAll(pageNumber, pageSize)
        );
    }
}
