package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Service DTO entity")
public class ServiceDto {

    @Schema(description = "ID of the service in database",
            example = "1")
    private Long id;

    @Schema(description = "Service's owner ID in the database, related to users table",
            example = "1")
    @NotBlank
    private Long workerId;

    @Schema(description = "Name of the service",
            example = "Cleaning")
    @NotBlank
    private String name;

    @Schema(description = "Price for this service",
            example = "1099")
    @NotBlank
    private Integer price;

    @Schema(description = "Additional information about the service",
            example = "Cleaning of the customer's properties")
    private String description;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "List of the related categories, when changing/adding new service," +
            " contains only ID's for related in database category")
    private List<CategoryDto> categories;

    @Schema(description = "All related to this service images",
            accessMode = Schema.AccessMode.READ_ONLY)
    private List<ImageResponseDto> images;
}
