package ru.gigaden.calories_app.exception;

import jakarta.validation.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Класс отлавливает исключения и возвращает ответ в нужном формате
 */
@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler({UserNotFoundException.class, DishNotFoundException.class})
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUserNotFound(BaseNotFoundException e, WebRequest request) {
        log.error("Ошибка 404 NotFoundException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.NOT_FOUND, e.getReason());
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEmailException(final EmailIsExistException e, WebRequest request) {
        log.error("Ошибка 409 EmailIsExistException: {} в запросе {}",
                e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.CONFLICT, e.getReason());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class,
            IllegalArgumentException.class,
            ValidationException.class,
            NumberFormatException.class,
            HttpMessageNotReadableException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> invalidMethodArgument(Exception e, WebRequest request) {
        log.error("Ошибка 400 {}: {} в запросе {}",
                e.getClass(), e.getMessage(), request.getDescription(false));
        return buildErrorResponse(e, HttpStatus.BAD_REQUEST, "Неверный формат запроса");
    }

    public Map<String, String> buildErrorResponse(Exception e, HttpStatus status, String reason) {
        Map<String, String> response = new LinkedHashMap<>();
        response.put("status", status.name());
        response.put("reason", reason);
        response.put("message", e.getMessage());
        response.put("timestamp", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return response;
    }
}