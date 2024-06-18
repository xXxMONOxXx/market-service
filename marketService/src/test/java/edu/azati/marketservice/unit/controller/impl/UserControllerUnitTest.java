package edu.azati.marketservice.unit.controller.impl;

import edu.azati.marketservice.controller.impl.UserController;
import edu.azati.marketservice.dto.UserDto;
import edu.azati.marketservice.service.impl.UserService;
import edu.azati.marketservice.unit.util.JsonUtil;
import edu.azati.marketservice.unit.util.UserTestUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void saveTest() throws Exception {
        UserDto userRequest = UserTestUtil.buildDtoWithoutId();
        UserDto userResponse = UserTestUtil.buildDto();
        when(userService.save(userRequest)).thenReturn(userResponse);
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(userRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.data.email").value(userResponse.getEmail()))
                .andExpect(jsonPath("$.data.username").value(userResponse.getUsername()))
                .andExpect(jsonPath("$.data.password").value(userResponse.getPassword()))
                .andExpect(jsonPath("$.data.isBlocked").value(userResponse.getIsBlocked()))
                .andExpect(jsonPath("$.data.userDetails").exists())
                .andExpect(jsonPath("$.data.userDetails.birthdate")
                        .value(userResponse.getUserDetails().getBirthdate().toString()))
                .andExpect(jsonPath("$.data.userDetails.firstname")
                        .value(userResponse.getUserDetails().getFirstname()))
                .andExpect(jsonPath("$.data.userDetails.surname")
                        .value(userResponse.getUserDetails().getSurname()))
                .andExpect(jsonPath("$.data.userDetails.phone")
                        .value(userResponse.getUserDetails().getPhone()))
                .andExpect(jsonPath("$.data.userDetails.sex")
                        .value(userResponse.getUserDetails().getSex()))
                .andExpect(jsonPath("$.data.roles[0].name")
                        .value(userResponse.getRoles().get(0).getName().getValue().toUpperCase()))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(userResponse.getUpdatedAt())));
        verify(userService, times(1)).save(userRequest);
    }

    @Test
    void findByIdTest() throws Exception {
        UserDto user = UserTestUtil.buildDto();
        when(userService.findById(user.getId())).thenReturn(user);
        mockMvc.perform(get("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.username").value(user.getUsername()))
                .andExpect(jsonPath("$.data.password").value(user.getPassword()))
                .andExpect(jsonPath("$.data.isBlocked").value(user.getIsBlocked()))
                .andExpect(jsonPath("$.data.userDetails").exists())
                .andExpect(jsonPath("$.data.userDetails.birthdate")
                        .value(user.getUserDetails().getBirthdate().toString()))
                .andExpect(jsonPath("$.data.userDetails.firstname")
                        .value(user.getUserDetails().getFirstname()))
                .andExpect(jsonPath("$.data.userDetails.surname")
                        .value(user.getUserDetails().getSurname()))
                .andExpect(jsonPath("$.data.userDetails.phone")
                        .value(user.getUserDetails().getPhone()))
                .andExpect(jsonPath("$.data.userDetails.sex")
                        .value(user.getUserDetails().getSex()))
                .andExpect(jsonPath("$.data.roles[0].name")
                        .value(user.getRoles().get(0).getName().getValue().toUpperCase()))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(user.getUpdatedAt())));
        verify(userService, times(1)).findById(user.getId());
    }

    @Test
    void findAllTest() throws Exception {
        PageRequest request = PageRequest.of(1, 10);
        Page<UserDto> page = UserTestUtil.buildFindAllPage(request);
        when(userService.findAll(1, 10)).thenReturn(page);
        mockMvc.perform(get("/users")
                        .param("page", "1")
                        .param("size", "10")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userService, times(1)).findAll(1, 10);
    }

    @Test
    void updateTest() throws Exception {
        UserDto userResponse = UserTestUtil.buildDto();
        UserDto userUpdateDto = UserTestUtil.buildDtoWithoutId();
        when(userService.update(userUpdateDto, userResponse.getId())).thenReturn(userResponse);
        mockMvc.perform(patch("/users/{id}", userResponse.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(JsonUtil.toBytes(userUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(userResponse.getId()))
                .andExpect(jsonPath("$.data.email").value(userResponse.getEmail()))
                .andExpect(jsonPath("$.data.username").value(userResponse.getUsername()))
                .andExpect(jsonPath("$.data.password").value(userResponse.getPassword()))
                .andExpect(jsonPath("$.data.isBlocked").value(userResponse.getIsBlocked()))
                .andExpect(jsonPath("$.data.userDetails").exists())
                .andExpect(jsonPath("$.data.userDetails.birthdate")
                        .value(userResponse.getUserDetails().getBirthdate().toString()))
                .andExpect(jsonPath("$.data.userDetails.firstname")
                        .value(userResponse.getUserDetails().getFirstname()))
                .andExpect(jsonPath("$.data.userDetails.surname")
                        .value(userResponse.getUserDetails().getSurname()))
                .andExpect(jsonPath("$.data.userDetails.phone")
                        .value(userResponse.getUserDetails().getPhone()))
                .andExpect(jsonPath("$.data.userDetails.sex")
                        .value(userResponse.getUserDetails().getSex()))
                .andExpect(jsonPath("$.data.roles[0].name")
                        .value(userResponse.getRoles().get(0).getName().getValue().toUpperCase()))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(userResponse.getUpdatedAt())));
        verify(userService, times(1)).update(userUpdateDto, userResponse.getId());
    }

    @Test
    void deleteTest() throws Exception {
        UserDto user = UserTestUtil.buildDto();
        when(userService.delete(user.getId())).thenReturn(user);
        mockMvc.perform(delete("/users/{id}", user.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(user.getId()))
                .andExpect(jsonPath("$.data.email").value(user.getEmail()))
                .andExpect(jsonPath("$.data.username").value(user.getUsername()))
                .andExpect(jsonPath("$.data.password").value(user.getPassword()))
                .andExpect(jsonPath("$.data.isBlocked").value(user.getIsBlocked()))
                .andExpect(jsonPath("$.data.userDetails").exists())
                .andExpect(jsonPath("$.data.userDetails.birthdate")
                        .value(user.getUserDetails().getBirthdate().toString()))
                .andExpect(jsonPath("$.data.userDetails.firstname")
                        .value(user.getUserDetails().getFirstname()))
                .andExpect(jsonPath("$.data.userDetails.surname")
                        .value(user.getUserDetails().getSurname()))
                .andExpect(jsonPath("$.data.userDetails.phone")
                        .value(user.getUserDetails().getPhone()))
                .andExpect(jsonPath("$.data.userDetails.sex")
                        .value(user.getUserDetails().getSex()))
                .andExpect(jsonPath("$.data.roles[0].name")
                        .value(user.getRoles().get(0).getName().getValue().toUpperCase()))
                .andExpect(jsonPath("$.data.updatedAt")
                        .value(JsonUtil.formatLocalDateTime(user.getUpdatedAt())));
        verify(userService, times(1)).delete(user.getId());
    }
}
