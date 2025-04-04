package ru.gigaden.calories_app.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ru.gigaden.calories_app.dto.user.UserCreateDto;
import ru.gigaden.calories_app.dto.user.UserResponseDto;
import ru.gigaden.calories_app.dto.user.UserUpdateDto;
import ru.gigaden.calories_app.entity.enums.Sex;
import ru.gigaden.calories_app.entity.enums.UserTarget;
import ru.gigaden.calories_app.entity.enums.UsersActivity;
import ru.gigaden.calories_app.exception.EmailIsExistException;
import ru.gigaden.calories_app.exception.UserNotFoundException;
import ru.gigaden.calories_app.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserResponseDto createTestResponse() {
        return new UserResponseDto(
                1L, "Test User", "test@example.com", 30,
                80.0, 180.0, UserTarget.WEIGHT_MAINTENANCE,
                2500.0, null
        );
    }

    @Test
    void createUser_ShouldReturnCreatedResponse() {
        UserCreateDto dto = new UserCreateDto(
                "Test", Sex.MALE, "test@example.com", 30,
                80.0, 180.0, UserTarget.WEIGHT_MAINTENANCE,
                UsersActivity.MODERATELY_ACTIVE
        );
        UserResponseDto response = createTestResponse();

        when(userService.createUser(any())).thenReturn(response);

        ResponseEntity<UserResponseDto> result = userController.createUser(dto);

        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getAllUsers_ShouldReturnOkWithUsersList() {
        List<UserResponseDto> users = List.of(createTestResponse());
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<Collection<UserResponseDto>> result = userController.getAllUsers();

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(1, Objects.requireNonNull(result.getBody()).size());
    }

    @Test
    void getUserById_ShouldReturnUserWhenExists() {
        UserResponseDto response = createTestResponse();
        when(userService.getUserDtoById(1L)).thenReturn(response);

        ResponseEntity<UserResponseDto> result = userController.getUserById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void getUserById_ShouldThrowNotFoundException() {
        when(userService.getUserDtoById(999L))
                .thenThrow(new UserNotFoundException("Пользователь не существует"));

        assertThrows(UserNotFoundException.class,
                () -> userController.getUserById(999L));
    }

    @Test
    void updateUserById_ShouldReturnUpdatedUser() {
        UserUpdateDto dto = new UserUpdateDto(
                "Updated", Sex.MALE, "updated@example.com", 35,
                85.0, 185.0, UserTarget.WEIGHT_LOSS,
                UsersActivity.VERY_ACTIVE
        );
        UserResponseDto response = createTestResponse();
        when(userService.updateUserById(1L, dto)).thenReturn(response);

        ResponseEntity<UserResponseDto> result = userController.updateUserById(1L, dto);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(response, result.getBody());
    }

    @Test
    void deleteUserById_ShouldReturnSuccessMessage() {
        doNothing().when(userService).deleteUserById(1L);

        ResponseEntity<String> result = userController.deleteUserById(1L);

        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals("Пользователь удалён", result.getBody());
    }

    @Test
    void createUser_ShouldHandleEmailExistsException() {
        UserCreateDto dto = new UserCreateDto(
                "Test", Sex.MALE, "exists@example.com", 30,
                80.0, 180.0, UserTarget.WEIGHT_MAINTENANCE,
                UsersActivity.MODERATELY_ACTIVE
        );
        when(userService.createUser(dto))
                .thenThrow(new EmailIsExistException("Email уже существует"));

        assertThrows(EmailIsExistException.class,
                () -> userController.createUser(dto));
    }
}