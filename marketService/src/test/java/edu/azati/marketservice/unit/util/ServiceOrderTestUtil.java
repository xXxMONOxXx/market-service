package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.ServiceOrderDto;
import edu.azati.marketservice.model.ServiceOrder;
import edu.azati.marketservice.model.enums.Status;
import edu.azati.marketservice.unit.util.constants.ServiceOrderTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@UtilityClass
public class ServiceOrderTestUtil {

    public ServiceOrderDto buildDto() {
        return ServiceOrderDto.builder()
                .id(ServiceOrderTestConstants.ID)
                .description(ServiceOrderTestConstants.DESCRIPTION)
                .orderHasProducts(false)
                .createdAt(ServiceOrderTestConstants.CREATED_AT)
                .createdBy(ServiceOrderTestConstants.CREATED_BY)
                .updatedAt(ServiceOrderTestConstants.UPDATED_AT)
                .service(ServiceTestUtil.buildDto())
                .build();
    }

    public Page<ServiceOrderDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    public ServiceOrderDto buildDtoWithoutId() {
        return ServiceOrderDto.builder()
                .description(ServiceOrderTestConstants.DESCRIPTION)
                .createdAt(ServiceOrderTestConstants.CREATED_AT)
                .orderHasProducts(false)
                .createdBy(ServiceOrderTestConstants.CREATED_BY)
                .updatedAt(ServiceOrderTestConstants.UPDATED_AT)
                .service(ServiceTestUtil.buildDto())
                .build();
    }

    public ServiceOrderDto buildDtoWithoutTimestampsAndId() {
        return ServiceOrderDto.builder()
                .description(ServiceOrderTestConstants.DESCRIPTION)
                .orderHasProducts(false)
                .createdBy(ServiceOrderTestConstants.CREATED_BY)
                .service(ServiceTestUtil.buildDto())
                .build();
    }

    public ServiceOrder buildEntity() {
        return new ServiceOrder(
                ServiceOrderTestConstants.ID,
                false,
                Status.PAYMENT_DUE,
                ServiceOrderTestConstants.DESCRIPTION,
                AddressTestUtil.buildEntity(),
                ServiceOrderTestConstants.CREATED_BY,
                ServiceTestUtil.buildEntity()
        );
    }

    public Page<ServiceOrder> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}
