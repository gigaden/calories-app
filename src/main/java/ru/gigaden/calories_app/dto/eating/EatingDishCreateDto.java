package ru.gigaden.calories_app.dto.eating;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record EatingDishCreateDto(@NotNull Long dishId,
                                  @NotNull @Positive Double portionCount) {
}
