package ru.gigaden.calories_app.dto;

import ru.gigaden.calories_app.entity.enums.UserTarget;

import java.time.LocalDateTime;

/**
 * Дто пользователя для ответа
 */
public record UserResponseDto(Long id,
                              String name,
                              String email,
                              Integer age,
                              Double weight,
                              Double height,
                              UserTarget target,
                              Double bmr,
                              LocalDateTime createdOn) {
}
