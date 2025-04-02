package ru.gigaden.calories_app.dto.dish;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

/**
 * Дто для создания блюда
 */
public record DishCreateDto(@NotNull String name,
                            @NotNull @Positive Double calories,
                            @NotNull @Positive Double proteins,
                            @NotNull @Positive Double fats,
                            @NotNull @Positive Double carbohydrates) {
}
