package edu.azati.pdfconverter.dto;

import edu.azati.pdfconverter.dto.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private Status currentStatus;

    private LocalDateTime createdAt;
}
