package ru.gigaden.calories_app.service;

import ru.gigaden.calories_app.dto.UserCreateDto;
import ru.gigaden.calories_app.dto.UserResponseDto;

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
}
