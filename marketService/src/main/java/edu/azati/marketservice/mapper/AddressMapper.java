package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.AddressDto;
import edu.azati.marketservice.model.Address;
import org.mapstruct.*;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface AddressMapper {

    AddressDto addressToDto(Address address);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Address dtoToAddress(AddressDto request);

    @InheritConfiguration
    @Mapping(target = "createdBy", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateAddressFromDto(AddressDto dto, @MappingTarget Address entity);
}
