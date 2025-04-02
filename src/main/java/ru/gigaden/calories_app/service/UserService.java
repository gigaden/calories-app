package ru.gigaden.calories_app.service;

import ru.gigaden.calories_app.dto.user.UserCreateDto;
import ru.gigaden.calories_app.dto.user.UserResponseDto;
import ru.gigaden.calories_app.dto.user.UserUpdateDto;
import ru.gigaden.calories_app.entity.User;

import java.util.Collection;

/**
 * Класс содержит методы для управления пользователем
 */
public interface UserService {

    /**
     * Метод создаёт нового пользователя\
     *
     * @param dto - объект с данными для нового пользователя
     */
    UserResponseDto createUser(UserCreateDto dto);

    /**
     * Метод получает всех пользователей
     */
    Collection<UserResponseDto> getAllUsers();

    /**
     * Метод получает пользователя по его id
     *
     * @param userId - id пользователя
     */
    User getUserById(Long userId);

    /**
     * Метод получает дто пользователя по его id
     *
     * @param userId - id пользователя
     */
    UserResponseDto getUserDtoById(Long userId);

    /**
     * Метод удаляет пользователя по его id
     *
     * @param userId - id пользователя
     */
    void deleteUserById(Long userId);

    /**
     * Метод обновляет пользователя
     *
     * @param userId - id пользователя
     * @param dto    - сущность с новыми данными пользователя
     */
    UserResponseDto updateUserById(Long userId, UserUpdateDto dto);

    /**
     * Метод проверяет существование пользователя
     *
     * @param userId - id пользователя
     */
    void checkUserIsExist(Long userId);

    /**
     * Метод проверяет уникальность адреса электронной почты
     *
     * @param email - email для проверки
     */
    void checkEmailIsExist(String email);
}
