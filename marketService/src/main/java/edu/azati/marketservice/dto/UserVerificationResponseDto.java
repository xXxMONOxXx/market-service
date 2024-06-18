package edu.azati.marketservice.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class UserVerificationResponseDto {

    private LocalDateTime verifiedAt;

    private Boolean verified;

    private String verificationMessage;
}
