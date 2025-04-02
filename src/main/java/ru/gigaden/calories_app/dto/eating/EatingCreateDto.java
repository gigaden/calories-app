package ru.gigaden.calories_app.dto.eating;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record EatingCreateDto(@NotNull Long userId,
                              @NotNull List<EatingDishCreateDto> mealItems) {
}
