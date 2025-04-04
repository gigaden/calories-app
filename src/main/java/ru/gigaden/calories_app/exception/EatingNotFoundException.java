package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class EatingNotFoundException extends BaseNotFoundException {
    private final String reason = "The required object was not found.";

    public EatingNotFoundException(String message) {
        super(message);
    }
}