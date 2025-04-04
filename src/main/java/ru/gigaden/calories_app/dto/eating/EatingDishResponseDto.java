package ru.gigaden.calories_app.dto.eating;

public record EatingDishResponseDto(Long id,
                                    Long eatingId,
                                    Long dishId,
                                    Double portionCount) {
}
