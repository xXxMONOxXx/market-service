package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.RoleDto;
import edu.azati.marketservice.model.enums.Role;
import edu.azati.marketservice.unit.util.constants.RoleTestConstants;
import lombok.experimental.UtilityClass;

@UtilityClass
public class RoleTestUtil {

    public RoleDto buildDto() {
        return RoleDto.builder()
                .name(Role.ADMIN)
                .build();
    }

    public edu.azati.marketservice.model.Role buildEntity() {
        return new edu.azati.marketservice.model.Role(RoleTestConstants.ID, RoleTestConstants.USER_ID, Role.ADMIN);
    }
}
