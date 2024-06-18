package edu.azati.mailnotifier.dto;


import edu.azati.mailnotifier.enums.Status;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ProductOrderMailRequestDto {

    private String emailReceiver;

    private Status oldStatus;

    private Status newStatus;

    private Long orderId;

    private LocalDateTime updateAt;

    private List<ProductOrderProductDto> products;

    private AddressDto address;

}
