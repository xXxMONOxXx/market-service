package edu.azati.marketservice.service.impl;

import edu.azati.marketservice.dto.ImageDto;
import edu.azati.marketservice.dto.ServiceDto;
import edu.azati.marketservice.exception.ServiceNotFoundException;
import edu.azati.marketservice.mapper.ServiceMapper;
import edu.azati.marketservice.model.Service;
import edu.azati.marketservice.repository.ServiceRepository;
import edu.azati.marketservice.service.BaseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

@Log4j2
@org.springframework.stereotype.Service
@RequiredArgsConstructor
public class ServiceEntityService implements BaseService<ServiceDto> {

    private final ServiceMapper serviceMapper;

    private final ServiceRepository serviceRepository;

    private final ImageService imageService;

    @Override
    public ServiceDto save(ServiceDto dto) {
        log.info("Creating new service");
        return serviceMapper.serviceToDto(serviceRepository.save(serviceMapper.dtoToService(dto)));
    }

    @Override
    public ServiceDto delete(Long id) {
        log.info("Deleting existing service from database");
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));
        serviceRepository.deleteById(id);
        return serviceMapper.serviceToDto(service);
    }

    @Override
    public ServiceDto findById(Long id) {
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));
        ServiceDto serviceDto = serviceMapper.serviceToDto(service);
        serviceDto.setImages(imageService.mapImagesToImageResponseDto(service.getImages()));
        return serviceDto;
    }

    @Override
    public ServiceDto update(ServiceDto dto, Long id) {
        log.info("Updating existing service");
        Service service = serviceRepository.findById(id)
                .orElseThrow(() -> new ServiceNotFoundException(id));
        serviceMapper.updateServiceFromDto(dto, service);
        serviceRepository.save(service);
        return serviceMapper.serviceToDto(service);
    }

    @Override
    public Page<ServiceDto> findAll(int pageNumber, int pageSize) {
        return serviceRepository.findAll(PageRequest.of(pageNumber, pageSize))
                .map(service -> {
                    ServiceDto serviceDto = serviceMapper.serviceToDto(service);
                    serviceDto.setImages(imageService.mapImagesToImageResponseDto(service.getImages()));
                    return serviceDto;
                });
    }

    public void addImage(MultipartFile image, Boolean isPreview, Long serviceId) {
        log.info("Adding new image for service");
        if (Boolean.TRUE.equals(isPreview)) {
            imageService.changePreview(serviceId, false);
        }
        imageService.saveImage(ImageDto.builder()
                .serviceId(serviceId)
                .isPreview(isPreview)
                .image(image)
                .build());
    }

    public void deleteImage(Long id) {
        log.info("Deleting image, related to service");
        imageService.deleteImage(id);
    }
}
