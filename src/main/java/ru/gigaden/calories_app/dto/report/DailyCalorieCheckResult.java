package ru.gigaden.calories_app.dto.report;

import lombok.Builder;
import lombok.Data;

/**
 * Решил возвращать объект класса, чтобы в будущем можно было добавить в него дополнительные поля,
 * например "превышение нормы", "дефицит" и т.д.
 */
@Data
@Builder
public class DailyCalorieCheckResult {
    private Long userId;
    private boolean isMetTheNorm;

}
