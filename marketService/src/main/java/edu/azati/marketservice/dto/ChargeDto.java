package edu.azati.marketservice.dto;

import edu.azati.marketservice.model.enums.CurrencyEnum;
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
@Schema(description = "Charge DTO entity")
public class ChargeDto {

    @Schema(description = "Currency of the charge",
            example = "BYN")
    @NotBlank
    private CurrencyEnum currency;

    @Schema(description = "Amount of money, NOT presented as point number, if point number is needed," +
            " for example cents, then just multiply by 100",
            example = "1099")
    private Long amount;

    @Schema(description = "Stipe generated token, needed to pay the charge",
            example = "tok_visa")
    private String stripeToken;

    @Schema(description = "ID of the order, that is being paid",
            example = "1")
    private Long orderId;

    @Schema(description = "ID of the user, that is paying",
            example = "1")
    private Long userId;
}
