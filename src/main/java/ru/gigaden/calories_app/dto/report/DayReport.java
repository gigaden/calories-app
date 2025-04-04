package ru.gigaden.calories_app.dto.report;

import lombok.Builder;
import lombok.Data;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;

import java.util.List;

@Data
@Builder
public class DayReport {

    private Double caloriesPerDay;
    private List<EatingResponseDto> eatingPerDay;
}
