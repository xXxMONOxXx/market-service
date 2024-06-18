package edu.azati.marketservice.controller.impl;

import edu.azati.marketservice.controller.BaseController;
import edu.azati.marketservice.dto.UserDto;
import edu.azati.marketservice.dto.UserVerificationResponseDto;
import edu.azati.marketservice.log.LogRequestExecutionTime;
import edu.azati.marketservice.service.impl.UserService;
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
@RequestMapping("/users")
@Tag(
        name = "UserController",
        description = "Controller responsible for standard CRUD operations on user entities"
)
public class UserController implements BaseController<UserDto> {

    private final UserService userService;

    @Override
    @PostMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Create user"
    )
    public ResponseEntity<Object> save(@RequestBody UserDto dto) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_CREATED,
                HttpStatus.CREATED,
                userService.save(dto)
        );
    }

    @Override
    @LogRequestExecutionTime
    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete user by id"
    )
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_DELETED,
                HttpStatus.OK,
                userService.delete(id)
        );
    }

    @Override
    @LogRequestExecutionTime
    @GetMapping("/{id}")
    @Operation(
            summary = "Get user by id"
    )
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_FOUND,
                HttpStatus.OK,
                userService.findById(id)
        );
    }


    @Override
    @LogRequestExecutionTime
    @PatchMapping("/{id}")
    @Operation(
            summary = "Update user by id"
    )
    public ResponseEntity<Object> update(@RequestBody UserDto dto, @PathVariable Long id) {
        return ResponseHandler.generateResponse(
                Constants.ENTITY_UPDATED,
                HttpStatus.OK,
                userService.update(dto, id)
        );
    }

    @Override
    @GetMapping
    @LogRequestExecutionTime
    @Operation(
            summary = "Find users",
            description = "Find users with pagination, default page number - 0, default page size - 4"
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
                userService.findAll(pageNumber, pageSize)
        );
    }

    @PostMapping("/{id}")
    @LogRequestExecutionTime
    @Operation(
            summary = "Verify user though sms on phone",
            description = "After registration the sms code verification is sent to user's phone number, this method" +
                    "receives code from request and if the codes are identical, then user is verified in database"
    )
    public ResponseEntity<Object> verifyUser(@RequestBody String code, @PathVariable Long id) {
        UserVerificationResponseDto response = userService.verifyUser(code, id);
        return ResponseHandler.generateResponse(
                response.getVerificationMessage(),
                HttpStatus.OK,
                response
        );
    }

    @LogRequestExecutionTime
    @PostMapping("/token")
    public ResponseEntity<Object> generateToken(@RequestBody UserDto userDto) {
        return ResponseHandler.generateResponse(
                Constants.ACCESS_TOKEN_CREATED,
                HttpStatus.CREATED,
                userService.generateToken(userDto)
        );
    }
}
