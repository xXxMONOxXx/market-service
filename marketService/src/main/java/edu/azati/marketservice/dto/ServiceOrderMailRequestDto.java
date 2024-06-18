package edu.azati.marketservice.dto;

import edu.azati.marketservice.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ServiceOrderMailRequestDto {

    private String emailReceiver;

    private Status oldStatus;

    private Status newStatus;

    private Long orderId;

    private LocalDateTime updateAt;

    private Long serviceId;

    private String serviceName;

    private AddressDto address;
}
