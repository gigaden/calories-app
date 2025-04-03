package ru.gigaden.calories_app.service;

import ru.gigaden.calories_app.dto.dish.DishCreateDto;
import ru.gigaden.calories_app.dto.dish.DishResponseDto;
import ru.gigaden.calories_app.entity.Dish;

import java.util.Collection;

/**
 * Класс содержит методы для управления блюдами
 */
public interface DishService {

    /**
     * Метод создаёт новое блюдо
     *
     * @param dto - объект с данными для нового блюда
     */
    DishResponseDto createDish(DishCreateDto dto);

    /**
     * Метод получает все блюда из БД
     */
    Collection<DishResponseDto> getAllDishes();

    /**
     * Метод получает блюдо по его id
     *
     * @param dishId - id блюда
     */
    Dish getDishById(Long dishId);

    /**
     * Метод получает дто блюда по его id
     *
     * @param dishId - id блюда
     */
    DishResponseDto getDishDtoById(Long dishId);

    /**
     * Метод удаляет блюдо по его id
     *
     * @param dishId - id блюда
     */
    void deleteDishById(Long dishId);

    /**
     * Метод проверяет существование блюда
     *
     * @param dishId - id блюда
     */
    void checkDishIsExist(Long dishId);
}
