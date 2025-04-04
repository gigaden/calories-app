package ru.gigaden.calories_app.validator;

import lombok.extern.slf4j.Slf4j;
import ru.gigaden.calories_app.exception.DatesPeriodValidationException;

import java.time.LocalDate;

/**
 * Класс содержит методы валидации
 */
@Slf4j
public class Validator {
    private Validator() {
    }

    /**
     * Метод проверяем период между двумя датами
     *
     * @param from - дата начала периода
     * @param to   - дата окончания периода
     * @throws ru.gigaden.calories_app.exception.DatesPeriodValidationException - выбрасывает в случае невозможности существования данного периода
     */
    public static void checkDates(LocalDate from, LocalDate to) {
        log.info("Проверяю период дат {} - {}", from, to);
        if (from.isAfter(to)) {
            throw new DatesPeriodValidationException("");
        }
        log.info("Даты {} - {} прошли проверку", from, to);
    }
}
