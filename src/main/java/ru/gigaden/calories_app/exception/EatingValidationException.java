package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class EatingValidationException extends RuntimeException {
    private final String reason = "Incorrectly made request.";

    public EatingValidationException(String message) {
        super(message);
    }
}