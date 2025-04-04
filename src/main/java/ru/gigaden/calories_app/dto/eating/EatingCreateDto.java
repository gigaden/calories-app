package ru.gigaden.calories_app.dto.eating;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EatingCreateDto(@NotNull Long userId,
                              @NotNull @Size(min = 1) List<@Valid EatingDishCreateDto> mealItems) {
}
