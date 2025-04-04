package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class DatesPeriodValidationException extends RuntimeException {
    private final String reason = "Incorrectly made request.";

    public DatesPeriodValidationException(String message) {
        super(message);
    }
}