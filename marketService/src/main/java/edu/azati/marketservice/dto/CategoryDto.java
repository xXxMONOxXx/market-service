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
@Schema(description = "Category DTO entity")
public class CategoryDto {

    @Schema(description = "ID of category in database",
            example = "1")
    private Long id;

    @Schema(description = "Category name, the main field",
            example = "Food")
    @NotBlank
    private String name;

    @Schema(description = "Description of the category",
            example = "This category is about something...")
    private String description;

}
