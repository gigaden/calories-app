package ru.gigaden.calories_app.service;

import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.dto.report.DailyCalorieCheckResult;
import ru.gigaden.calories_app.dto.report.DayReport;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Сервис содержит методы, формирующие нужные отчёты
 */
public interface ReportService {

    /**
     * Отчёт за день с суммой всех калорий и приемов пищи
     *
     * @param userId - id пользователя
     */
    DayReport getDayReportByUserId(Long userId);

    /**
     * Метод проверяет, уложился ли пользователь в свою дневную норму калорий
     *
     * @param userId - id пользователя
     * @return - объект, содержащий id пользователя, ответ о норме
     */
    DailyCalorieCheckResult checkUserIsMetInCalorieTarget(Long userId);

    /**
     * Метод получает историю питания по дня
     *
     * @param userId - id пользователя
     * @param from   - дата начала периода
     * @param to     - дата окончания периода
     */
    Collection<EatingResponseDto> getEatingHistoryByPeriod(Long userId, LocalDate from, LocalDate to);
}
