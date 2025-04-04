package ru.gigaden.calories_app.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.dto.report.DailyCalorieCheckResult;
import ru.gigaden.calories_app.dto.report.DayReport;
import ru.gigaden.calories_app.service.ReportService;

import java.time.LocalDate;
import java.util.Collection;

/**
 * Класс содержит основные эндпоинты для получения отчётов
 */
@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
@Tag(name = "Отчёты", description = "Контроллер управления отчётами")
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/{userId}")
    @Operation(summary = "Получение отчёта", description = "Получаем дневной отчёт пользователя")
    public ResponseEntity<DayReport> getDatReportByUserId(@PathVariable Long userId) {
        DayReport dayReport = reportService.getDayReportByUserId(userId);

        return new ResponseEntity<>(dayReport, HttpStatus.OK);
    }

    @GetMapping("/{userId}/calories")
    @Operation(summary = "Проверка цели по калориям", description = "Выполняем проверку уложился ли пользователь в норму")
    public ResponseEntity<DailyCalorieCheckResult> checkUserIsMetInCalorieTarget(@PathVariable Long userId) {
        DailyCalorieCheckResult result = reportService.checkUserIsMetInCalorieTarget(userId);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }


    @GetMapping("/{userId}/histories")
    @Operation(summary = "История питания", description = "Получаем историю питания пользователя за период")
    public ResponseEntity<Collection<EatingResponseDto>> checkUserIsMetInCalorieTarget(@PathVariable Long userId,
                                                                                       @RequestParam LocalDate from,
                                                                                       @RequestParam LocalDate to) {
        Collection<EatingResponseDto> responseDtos = reportService.getEatingHistoryByPeriod(userId, from, to);

        return new ResponseEntity<>(responseDtos, HttpStatus.OK);
    }
}
