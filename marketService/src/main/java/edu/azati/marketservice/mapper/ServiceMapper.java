package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.ServiceDto;
import edu.azati.marketservice.model.Service;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface ServiceMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Service dtoToService(ServiceDto request);

    ServiceDto serviceToDto(Service service);

    @InheritConfiguration
    @Mapping(target = "workerId", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateServiceFromDto(ServiceDto dto, @MappingTarget Service entity);
}
