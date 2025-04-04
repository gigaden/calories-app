package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class EmailIsExistException extends RuntimeException {
    private final String reason = "The required email is already exist.";

    public EmailIsExistException(String message) {
        super(message);
    }
}