package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ServiceOrderDto;
import edu.azati.marketservice.exception.ServiceOrderNotFoundException;
import edu.azati.marketservice.mapper.ServiceOrderMapper;
import edu.azati.marketservice.model.ServiceOrder;
import edu.azati.marketservice.model.enums.Status;
import edu.azati.marketservice.repository.ServiceOrderRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Log4j2
@Service
@RequiredArgsConstructor
public class ServiceOrderService implements BaseService<ServiceOrderDto> {

    private final ServiceOrderRepository serviceOrderRepository;

    private final ServiceOrderMapper serviceOrderMapper;

    private final OrderMailService orderMailService;

    @Override
    public ServiceOrderDto save(ServiceOrderDto dto) {
        log.info("Create new service order");
        return serviceOrderMapper.serviceOrderToDto(serviceOrderRepository
                .save(serviceOrderMapper.dtoToServiceOrder(dto)));
    }

    @Override
    public ServiceOrderDto delete(Long id) {
        log.info("Deleting existing service order from database");
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        serviceOrderRepository.deleteById(id);
        return serviceOrderMapper.serviceOrderToDto(serviceOrder);
    }

    @Override
    public ServiceOrderDto findById(Long id) {
        return serviceOrderMapper.serviceOrderToDto(serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id)));
    }

    @Override
    public ServiceOrderDto update(ServiceOrderDto dto, Long id) {
        log.info("Updating existing service order");
        ServiceOrder serviceOrder = serviceOrderRepository.findById(id)
                .orElseThrow(() -> new ServiceOrderNotFoundException(id));
        boolean mustNotifyCustomer = false;
        Status oldStatus = serviceOrder.getCurrentStatus();
        if (!dto.getCurrentStatus().equals(serviceOrder.getCurrentStatus())) {
            mustNotifyCustomer = true;
        }
        serviceOrderMapper.updateServiceOrderFromDto(dto, serviceOrder);
        ServiceOrderDto response = serviceOrderMapper.serviceOrderToDto(serviceOrder);
        if (mustNotifyCustomer) {
            log.info("Status of order changed, sending email to customer");
            orderMailService.sendStatusChangeMessageForServiceOrder(oldStatus,
                    dto.getCurrentStatus(),
                    id,
                    response,
                    serviceOrder.getUpdatedAt());
        }
        return response;
    }

    @Override
    public Page<ServiceOrderDto> findAll(int pageNumber, int pageSize) {
        return serviceOrderRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(serviceOrderMapper::serviceOrderToDto);
    }

    public Integer getAmount(Long id) {
        log.info("Calculate total price for service order");
        return findById(id).getService().getPrice();
    }
}
