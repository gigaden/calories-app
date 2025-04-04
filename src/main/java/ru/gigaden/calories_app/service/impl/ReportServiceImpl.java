package ru.gigaden.calories_app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.dto.report.DailyCalorieCheckResult;
import ru.gigaden.calories_app.dto.report.DayReport;
import ru.gigaden.calories_app.entity.Eating;
import ru.gigaden.calories_app.entity.EatingDish;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.mapper.EatingMapper;
import ru.gigaden.calories_app.service.EatingService;
import ru.gigaden.calories_app.service.ReportService;
import ru.gigaden.calories_app.service.UserService;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {

    private final UserService userService;
    private final EatingService eatingService;
    private final EatingMapper eatingMapper;

    @Override
    public DayReport getDayReportByUserId(Long userId) {
        log.info("Получаю отчёт пользователя с id = {} за день", userId);
        userService.checkUserIsExist(userId);
        Collection<Eating> eatings = eatingService.getEatingPerCurrentDayByUserId(userId);
        Double caloriesPerDay = calculateCalories(eatings);
        List<EatingResponseDto> eatingResponseDtos = eatings.stream()
                .map(eatingMapper::mapEatingToResponseDto).toList();
        log.info("Отчёт пользователя с id = {} за день получен", userId);

        return DayReport.builder()
                .caloriesPerDay(caloriesPerDay)
                .eatingPerDay(eatingResponseDtos)
                .build();
    }

    @Override
    public DailyCalorieCheckResult checkUserIsMetInCalorieTarget(Long userId) {
        log.info("Проверяю уложился ли пользователь с id = {} в дневную норму калорий", userId);
        User user = userService.getUserById(userId);
        Collection<Eating> eatings = eatingService.getEatingPerCurrentDayByUserId(userId);
        Double caloriesPerDay = calculateCalories(eatings);
        Double userMbr = userService.calculateBMR(user);
        log.info("Выполнена проверка уложился ли пользователь с id = {} в дневную норму калорий", userId);

        return DailyCalorieCheckResult.builder()
                .userId(userId)
                .isMetTheNorm(caloriesPerDay <= userMbr)
                .build();
    }

    @Override
    public Collection<EatingResponseDto> getEatingHistoryByPeriod(Long userId, LocalDate from, LocalDate to) {
        log.info("Пытаюсь получить историю питания пользователя с id = {} за период {} - {}", userId, from, to);
        userService.checkUserIsExist(userId);
        Collection<Eating> eatings = eatingService.getEatingByPeriodAndUserId(userId, from, to);
        log.info("Получена история питания пользователя с id = {} за период {} - {}", userId, from, to);

        return eatings.stream().map(eatingMapper::mapEatingToResponseDto).toList();
    }

    public Double calculateCalories(Collection<Eating> eatings) {
        double total = 0.0;
        for (Eating eating : eatings) {
            for (EatingDish eatingDish : eating.getMealItems()) {
                total += eatingDish.getDish().getCalories() * eatingDish.getPortionCount();
            }
        }
        return total;
    }
}
