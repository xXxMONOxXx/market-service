package edu.azati.marketservice.dto;

import edu.azati.marketservice.model.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Payment DTO entity")
public class PaymentDto {

    private Long id;

    private Long userId;

    private Status currentStatus;

    private Long orderId;

    private String stripeToken;

    private LocalDateTime createdAt;
}
