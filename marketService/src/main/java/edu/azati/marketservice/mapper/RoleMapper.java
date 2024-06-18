package edu.azati.marketservice.mapper;

import edu.azati.marketservice.dto.RoleDto;
import edu.azati.marketservice.model.Role;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING
)
public interface RoleMapper {
    RoleDto roleToDto(Role role);

    Role dtoToRole(RoleDto role);
}
