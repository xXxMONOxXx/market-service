package edu.azati.marketservice.dto;

import edu.azati.marketservice.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Service order DTO entity")
public class ServiceOrderDto {

    @Schema(description = "ID of the order in database",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID of the order in database")
    @NotBlank
    private Status currentStatus;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY, hidden = true)
    private Boolean orderHasProducts = false;

    @Schema(description = "Description of the order, could contain some additional information",
            example = "Description")
    private String description;

    @Schema(description = "Address, where order should go to, in this request only ID needed")
    @NotBlank
    private AddressDto address;

    @Schema(description = "ID of user, that created this order",
            example = "1")
    private Long createdBy;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "Related to this order service, when creating new order, contains only ID")
    private ServiceDto service;
}
