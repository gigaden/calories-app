package ru.gigaden.calories_app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.gigaden.calories_app.entity.enums.UserTarget;

/**
 * Дто для создания пользователя
 */
public record UserCreateDto(@NotNull String name,
                            @Email String email,
                            @Positive @Max(value = 200) Integer age,
                            @Positive Double weight,
                            @Positive Double height,
                            @NotNull UserTarget target) {
}