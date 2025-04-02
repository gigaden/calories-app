package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class DishNotFoundException extends BaseNotFoundException {
    private final String reason = "The required object was not found.";

    public DishNotFoundException(String message) {
        super(message);
    }
}