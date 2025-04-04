package ru.gigaden.calories_app.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gigaden.calories_app.dto.user.UserCreateDto;
import ru.gigaden.calories_app.dto.user.UserResponseDto;
import ru.gigaden.calories_app.dto.user.UserUpdateDto;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.entity.enums.*;
import ru.gigaden.calories_app.exception.EmailIsExistException;
import ru.gigaden.calories_app.exception.UserNotFoundException;
import ru.gigaden.calories_app.mapper.UserMapper;
import ru.gigaden.calories_app.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User createTestUser() {
        return User.builder()
                .id(1L)
                .name("Test User")
                .sex(Sex.MALE)
                .email("test@example.com")
                .age(30)
                .weight(80.0)
                .height(180.0)
                .target(UserTarget.WEIGHT_MAINTENANCE)
                .activity(UsersActivity.MODERATELY_ACTIVE)
                .build();
    }

    @Test
    void createUser_ShouldReturnUserResponseDto() {
        UserCreateDto dto = new UserCreateDto(
                "Test User", Sex.MALE, "test@example.com",
                30, 80.0, 180.0, UserTarget.WEIGHT_MAINTENANCE, UsersActivity.MODERATELY_ACTIVE
        );

        User user = createTestUser();
        UserResponseDto expectedResponse = new UserResponseDto(
                1L, "Test User", "test@example.com", 30, 80.0, 180.0,
                UserTarget.WEIGHT_MAINTENANCE, 2500.0, LocalDateTime.now()
        );

        when(userRepository.findUserByEmail(any())).thenReturn(Optional.empty());
        when(userMapper.mapCreateDtoToUser(any())).thenReturn(user);
        when(userRepository.save(any())).thenReturn(user);
        when(userMapper.mapUserToResponseDto(any())).thenReturn(expectedResponse);

        UserResponseDto result = userService.createUser(dto);

        assertNotNull(result);
        assertEquals("test@example.com", result.email());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void createUser_ShouldThrowEmailIsExistException() {
        UserCreateDto dto = new UserCreateDto(
                "Test User", Sex.MALE, "existing@example.com",
                30, 80.0, 180.0, UserTarget.WEIGHT_MAINTENANCE, UsersActivity.MODERATELY_ACTIVE
        );

        when(userRepository.findUserByEmail("existing@example.com"))
                .thenReturn(Optional.of(new User()));

        assertThrows(EmailIsExistException.class, () -> userService.createUser(dto));
    }

    @Test
    void getAllUsers_ShouldReturnListOfUsers() {
        User user = createTestUser();
        UserResponseDto dto = new UserResponseDto(
                1L, "Test User", "test@example.com", 30, 80.0, 180.0,
                UserTarget.WEIGHT_MAINTENANCE, 2500.0, LocalDateTime.now()
        );

        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.mapUserToResponseDto(any())).thenReturn(dto);

        Collection<UserResponseDto> result = userService.getAllUsers();

        assertEquals(1, result.size());
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void getUserById_ShouldReturnUser() {
        Long userId = 1L;
        User user = createTestUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.getUserById(userId);

        assertNotNull(result);
        assertEquals(userId, result.getId());
    }

    @Test
    void getUserById_ShouldThrowUserNotFoundException() {
        Long userId = 999L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.getUserById(userId));
    }

    @Test
    void deleteUserById_ShouldCallRepositoryDelete() {
        Long userId = 1L;
        User user = createTestUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        userService.deleteUserById(userId);

        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void updateUserById_ShouldUpdateUserFields() {
        Long userId = 1L;
        UserUpdateDto updateDto = new UserUpdateDto(
                "Updated Name", Sex.MALE, "updated@example.com",
                35, 85.0, 185.0, UserTarget.WEIGHT_LOSS, UsersActivity.VERY_ACTIVE
        );

        User existingUser = createTestUser();

        when(userRepository.findById(userId)).thenReturn(Optional.of(existingUser));
        when(userRepository.findUserByEmail("updated@example.com")).thenReturn(Optional.empty());
        when(userMapper.mapUserToResponseDto(any())).thenReturn(new UserResponseDto(
                1L, "Updated Name", "updated@example.com", 35, 85.0, 185.0,
                UserTarget.WEIGHT_LOSS, 2200.0, LocalDateTime.now()
        ));

        UserResponseDto result = userService.updateUserById(userId, updateDto);

        assertEquals("Updated Name", result.name());
        assertEquals(85.0, result.weight());
        verify(userRepository, times(1)).save(existingUser);
    }

    @Test
    void calculateBMR_ShouldReturnCorrectValueForMale() {
        User user = User.builder()
                .sex(Sex.MALE)
                .weight(80.0)
                .height(180.0)
                .age(30)
                .activity(UsersActivity.MODERATELY_ACTIVE)
                .target(UserTarget.WEIGHT_MAINTENANCE)
                .build();

        double result = userService.calculateBMR(user);

        double expectedBaseBMR = 88.362 + (13.397 * 80) + (4.799 * 180) - (5.677 * 30);
        double expectedWithActivity = expectedBaseBMR * 1.55;
        assertEquals(expectedWithActivity, result, 0.01);
    }

    @Test
    void calculateBMR_ShouldApplyWeightLossModifier() {
        User user = User.builder()
                .sex(Sex.FEMALE)
                .weight(70.0)
                .height(165.0)
                .age(25)
                .activity(UsersActivity.SEDENTARY)
                .target(UserTarget.WEIGHT_LOSS)
                .build();

        double result = userService.calculateBMR(user);

        double expectedBaseBMR = 447.593 + (9.247 * 70) + (3.098 * 165) - (4.330 * 25);
        double expectedWithActivity = expectedBaseBMR * 1.2;
        double expectedFinal = expectedWithActivity * 0.85;
        assertEquals(expectedFinal, result, 0.01);
    }
}