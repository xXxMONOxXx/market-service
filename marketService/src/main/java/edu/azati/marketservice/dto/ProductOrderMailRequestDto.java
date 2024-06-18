package edu.azati.marketservice.dto;


import edu.azati.marketservice.model.enums.Status;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class ProductOrderMailRequestDto {

    private String emailReceiver;

    private Status oldStatus;

    private Status newStatus;

    private Long orderId;

    private LocalDateTime updateAt;

    private List<ProductOrderProductDto> products;

    private AddressDto address;
}
