package edu.azati.marketservice.dto;

import edu.azati.marketservice.model.enums.Role;
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
@Schema(description = "Role DTO entity")
public class RoleDto {

    @Schema(description = "User's role, determines user's abilities",
            example = "ADMIN")
    private Role name;
}
