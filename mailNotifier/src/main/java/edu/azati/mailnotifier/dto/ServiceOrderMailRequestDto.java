package edu.azati.mailnotifier.dto;

import edu.azati.mailnotifier.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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
