package ru.gigaden.calories_app.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.gigaden.calories_app.entity.enums.Sex;
import ru.gigaden.calories_app.entity.enums.UserTarget;
import ru.gigaden.calories_app.entity.enums.UsersActivity;

/**
 * Дто для создания пользователя
 */
public record UserCreateDto(@NotNull String name,
                            @NotNull Sex sex,
                            @NotNull @Email String email,
                            @Positive @Max(value = 200) Integer age,
                            @Positive @Max(value = 300) Double weight,
                            @Positive @Max(value = 300) Double height,
                            @NotNull UserTarget target,
                            @NotNull UsersActivity activity) {
}