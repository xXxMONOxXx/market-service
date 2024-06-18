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
@Schema(description = "Review of service DTO entity")
public class ServiceReviewDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "ID of the service in database",
            example = "1")
    @NotBlank
    private Long serviceId;

    @Schema(description = "Additional information for the review",
            example = "I really liked it")
    private String comment;

    @Schema(description = "Number from 1 to 5, that represents satisfaction of the service",
            example = "5")
    @NotBlank
    private Byte rating;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime createdAt;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "User id of the reviewer")
    private Long createdBy;
}
