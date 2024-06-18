package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "User DTO entity")
public class UserDto {

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @Schema(description = "User's email",
            example = "lazaro.stark@gmail.com")
    @Email
    @NotBlank
    private String email;

    @Schema(description = "User's username, must be unique",
            example = "velma50")
    @NotBlank
    private String username;

    @Schema(description = "User's password",
            example = "/s&6R7+9c1&zTBOwI")
    @NotBlank
    private String password;

    @Schema(description = "Represents if user is blocked",
            example = "false")
    private Boolean isBlocked;

    @Schema(description = "Additional information about user")
    private UserDetailsDto userDetails;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime updatedAt;

    @Schema(description = "List of user's roles")
    private List<RoleDto> roles;

    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private LocalDateTime verifiedAt;
}
