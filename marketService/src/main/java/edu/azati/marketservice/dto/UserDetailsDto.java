package edu.azati.marketservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Additional information about user")
public class UserDetailsDto {

    @Schema(description = "User birthdate",
            example = "02-05-1990")
    @NotBlank
    private LocalDate birthdate;

    @Schema(description = "Firstname of the user",
            example = "Sven")
    @NotBlank
    private String firstname;

    @Schema(description = "Second name of the user",
            example = "Crist")
    @NotBlank
    private String surname;

    @Schema(description = "Contains user's phone number",
            example = "13214995528")
    @NotBlank
    private String phone;

    @Schema(description = "Represents user's gender, only two options here, false - male, true - female",
            example = "true")
    @NotBlank
    private Boolean sex;
}
