package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImageDto {

    @Schema(description = "ID of the image in database",
            example = "1")
    private Long id;

    @Schema(description = "ID of the product, that is related to the image",
            example = "1")
    private Long productId;

    @Schema(description = "ID of the service, that is related to the image",
            example = "1")
    private Long serviceId;

    @Schema(description = "Flag, that represents if this picture is the main one",
            example = "true")
    @NotBlank
    private Boolean isPreview;

    @Schema(description = "Picture related to the product/service")
    @NotBlank
    private MultipartFile image;
}
