package edu.azati.marketservice.unit.service;

import edu.azati.marketservice.dto.UserDto;
import edu.azati.marketservice.exception.UserNotFoundException;
import edu.azati.marketservice.mapper.UserMapper;
import edu.azati.marketservice.model.User;
import edu.azati.marketservice.repository.UserRepository;
import edu.azati.marketservice.service.impl.UserService;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserMapper.class})
class UserServiceUnitTest {

    @Mock
    private UserRepository repository;

    @Spy
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);

    @InjectMocks
    private UserService service;

    @Test
    void saveTest() {
        UserDto request = UserTestUtil.buildDtoWithoutTimestampsAndId();
        User entity = UserTestUtil.buildEntity();
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.userToDto(entity), service.save(request));
    }

    @Test
    void findByIdTest() {
        User entity = UserTestUtil.buildEntity();
        when(repository.findById(entity.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.userToDto(entity), service.findById(entity.getId()));
    }

    @Test
    void findByIdNotFoundTest() {
        assertThrows(UserNotFoundException.class, () -> {
            UserDto expected = UserTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void findAllTest() {
        PageRequest request = PageRequest.of(1, 10);
        Page<User> repositoryResponse = UserTestUtil.buildFindAllRepositoryResponse(request);
        when(repository.findAll(request)).thenReturn(repositoryResponse);
        Page<UserDto> actual = service.findAll(1, 10);
        assertEquals(repositoryResponse.map(mapper::userToDto), actual);
    }

    @Test
    void updateTest() {
        User entity = UserTestUtil.buildEntity();
        UserDto dto = UserTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        when(repository.save(any())).thenReturn(entity);
        assertEquals(mapper.userToDto(entity), service.save(dto));
    }

    @Test
    void updateNotFoundTest() {
        assertThrows(UserNotFoundException.class, () -> {
            UserDto expected = UserTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }

    @Test
    void deleteTest() {
        User entity = UserTestUtil.buildEntity();
        UserDto dto = UserTestUtil.buildDto();
        when(repository.findById(dto.getId())).thenReturn(Optional.of(entity));
        assertEquals(mapper.userToDto(entity), service.delete(entity.getId()));
        verify(repository, times(1)).deleteById(entity.getId());
    }

    @Test
    void deleteNotFoundTest() {
        assertThrows(UserNotFoundException.class, () -> {
            UserDto expected = UserTestUtil.buildDto();
            when(repository.findById(expected.getId())).thenReturn(Optional.empty());
            assertEquals(expected, service.findById(expected.getId()));
        });
    }
}
