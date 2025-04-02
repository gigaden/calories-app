package ru.gigaden.calories_app.service;

import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.entity.Eating;

import java.util.Collection;

/**
 * Класс содержит методы для управления приёмами пищи
 */
public interface EatingService {

    /**
     * Метод создаёт новый приём пищи
     *
     * @param dto - объект с данными для нового приёма пищи
     */
    EatingResponseDto createEating(EatingCreateDto dto);

    /**
     * Метод получает все приёмы пищи из БД
     */
    Collection<EatingResponseDto> getAllEating();

    /**
     * Метод получает приём пищи по его id
     *
     * @param eatingId - id приёма пищи
     */
    Eating getEatingById(Long eatingId);

    /**
     * Метод получает дто приёма пищи по его id
     *
     * @param eatingId - id приёма пищи
     */
    EatingResponseDto getEatingDtoById(Long eatingId);

    /**
     * Метод удаляет приём пищи по его id
     *
     * @param eatingId - id приёма пищи
     */
    void deleteEatingById(Long eatingId);
}
