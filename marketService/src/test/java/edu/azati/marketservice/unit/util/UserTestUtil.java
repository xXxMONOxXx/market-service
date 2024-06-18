package edu.azati.marketservice.unit.util;

import edu.azati.marketservice.dto.UserDetailsDto;
import edu.azati.marketservice.dto.UserDto;
import edu.azati.marketservice.model.User;
import edu.azati.marketservice.model.UserDetails;
import edu.azati.marketservice.unit.util.constants.UserTestConstants;
import lombok.experimental.UtilityClass;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;

@UtilityClass
public class UserTestUtil {

    public UserDto buildDto() {
        return UserDto.builder()
                .id(UserTestConstants.ID)
                .roles(Collections.singletonList(RoleTestUtil.buildDto()))
                .email(UserTestConstants.EMAIL)
                .username(UserTestConstants.USERNAME)
                .password(UserTestConstants.PASSWORD)
                .isBlocked(UserTestConstants.IS_BLOCKED)
                .updatedAt(UserTestConstants.UPDATED_AT)
                .userDetails(buildUserDetailsDto())
                .build();
    }

    public UserDto buildDtoWithoutId() {
        return UserDto.builder()
                .roles(Collections.singletonList(RoleTestUtil.buildDto()))
                .email(UserTestConstants.EMAIL)
                .username(UserTestConstants.USERNAME)
                .password(UserTestConstants.PASSWORD)
                .isBlocked(UserTestConstants.IS_BLOCKED)
                .updatedAt(UserTestConstants.UPDATED_AT)
                .userDetails(buildUserDetailsDto())
                .build();
    }

    public Page<UserDto> buildFindAllPage(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildDto()), request, 1);
    }

    private UserDetailsDto buildUserDetailsDto() {
        return UserDetailsDto.builder()
                .birthdate(UserTestConstants.BIRTHDATE)
                .firstname(UserTestConstants.FIRSTNAME)
                .surname(UserTestConstants.SURNAME)
                .phone(UserTestConstants.PHONE)
                .sex(UserTestConstants.SEX)
                .build();
    }

    public UserDto buildDtoWithoutTimestampsAndId() {
        return UserDto.builder()
                .roles(Collections.singletonList(RoleTestUtil.buildDto()))
                .email(UserTestConstants.EMAIL)
                .username(UserTestConstants.USERNAME)
                .password(UserTestConstants.PASSWORD)
                .isBlocked(UserTestConstants.IS_BLOCKED)
                .userDetails(buildUserDetailsDto())
                .build();
    }

    public User buildEntity() {
        return new User(
                UserTestConstants.ID,
                UserTestConstants.EMAIL,
                UserTestConstants.PASSWORD,
                UserTestConstants.USERNAME,
                UserTestConstants.IS_BLOCKED,
                Collections.singletonList(RoleTestUtil.buildEntity()),
                createUserDetailsForEntity(),
                null,
                null,
                null,
                null,
                UserTestConstants.UPDATED_AT
        );
    }

    private UserDetails createUserDetailsForEntity() {
        return new UserDetails(
                UserTestConstants.ID,
                UserTestConstants.FIRSTNAME,
                UserTestConstants.SURNAME,
                UserTestConstants.BIRTHDATE,
                UserTestConstants.PHONE,
                UserTestConstants.SEX
        );
    }

    public Page<User> buildFindAllRepositoryResponse(PageRequest request) {
        return new PageImpl<>(Collections.singletonList(buildEntity()), request, 1);
    }
}