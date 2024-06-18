package edu.azati.marketservice.dto;

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
@Schema(description = "Address DTO entity")
public class AddressDto {

    @Schema(description = "ID of address in database",
            example = "1")
    private Long id;

    @Schema(description = "Country",
            example = "Austria")
    @NotBlank
    private String country;

    @Schema(description = "City name",
            example = "New Patiencechester")
    @NotBlank
    private String city;

    @Schema(description = "Street name",
            example = "Kuhic Burg")
    private String street;

    @Schema(description = "Could be just a number of the apartment in the building, or something more specific",
            example = "85218")
    private String apartment;

    @Schema(description = "Postcode",
            example = "18656")
    private String postcode;

    @Schema(description = "When address was created",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(description = "When address was last updated",
            accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "ID of user, that created this address",
            example = "1")
    private Long createdBy;
}
