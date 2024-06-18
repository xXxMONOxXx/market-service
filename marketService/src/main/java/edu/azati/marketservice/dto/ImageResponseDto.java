package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Image for service or product, could be a preview")
public class ImageResponseDto {

    @Schema(description = "ID of the image in database",
            accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "Flag, that represents if this picture is the main one",
            accessMode = Schema.AccessMode.READ_ONLY)
    @NotBlank
    private Boolean isPreview;

    @Schema(description = "Image byte array",
            accessMode = Schema.AccessMode.READ_ONLY)
    private byte[] image;
}
