package ru.gigaden.calories_app.dto.dish;

/**
 * Дто блюда для ответа
 */
public record DishResponseDto(Long id,
                              String name,
                              Double calories,
                              Double proteins,
                              Double fats,
                              Double carbohydrates) {
}