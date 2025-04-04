package ru.gigaden.calories_app.dto.eating;

import java.time.LocalDateTime;
import java.util.List;

public record EatingResponseDto(Long id,
                                Long userId,
                                LocalDateTime eatingDate,
                                List<EatingDishResponseDto> mealItems) {
}
