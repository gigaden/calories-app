package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends BaseNotFoundException {
    private final String reason = "The required object was not found.";

    public UserNotFoundException(String message) {
        super(message);
    }
}