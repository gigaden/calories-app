package ru.gigaden.calories_app.exception;

import lombok.Getter;

@Getter
public class BaseNotFoundException extends RuntimeException {
    private String reason;

    public BaseNotFoundException(String message) {
        super(message);
    }
}
