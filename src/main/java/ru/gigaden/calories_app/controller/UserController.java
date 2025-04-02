package ru.gigaden.calories_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.calories_app.dto.user.UserCreateDto;
import ru.gigaden.calories_app.dto.user.UserResponseDto;
import ru.gigaden.calories_app.dto.user.UserUpdateDto;
import ru.gigaden.calories_app.service.UserService;

import java.util.Collection;

/**
 * Класс содержит основные эндпоинты пользователя
 */
@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Tag(name = "Пользователи", description = "Контроллер управления пользователями")
public class UserController {

    private final UserService userService;

    @PostMapping
    @Operation(summary = "Создание пользователя", description = "Позволяет добавит нового пользователя в БД")
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateDto dto) {
        UserResponseDto responseDto = userService.createUser(dto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Получение пользователей", description = "Получаем всех пользователей из БД")
    public ResponseEntity<Collection<UserResponseDto>> getAllUsers() {
        Collection<UserResponseDto> users = userService.getAllUsers();

        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Получение пользователя", description = "Получаем пользователя по его id")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable Long userId) {
        UserResponseDto userResponse = userService.getUserDtoById(userId);

        return new ResponseEntity<>(userResponse, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(summary = "Обновление пользователя", description = "Позволяет обновить данные пользователя в БД")
    public ResponseEntity<UserResponseDto> updateUserById(@PathVariable Long userId, @Valid @RequestBody UserUpdateDto dto) {
        UserResponseDto responseDto = userService.updateUserById(userId, dto);

        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Удаление пользователя", description = "Позволяет удалить пользователя по его id")
    public ResponseEntity<String> deleteUserById(@PathVariable Long userId) {
        userService.deleteUserById(userId);

        return new ResponseEntity<>("Пользователь удалён", HttpStatus.OK);
    }

}
