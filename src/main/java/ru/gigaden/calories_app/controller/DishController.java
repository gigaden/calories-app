package ru.gigaden.calories_app.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.calories_app.dto.dish.DishCreateDto;
import ru.gigaden.calories_app.dto.dish.DishResponseDto;
import ru.gigaden.calories_app.service.DishService;

import java.util.Collection;

/**
 * Класс содержит основные эндпоинты для блюд
 */
@RestController
@RequestMapping("/dishes")
@RequiredArgsConstructor
@Tag(name = "Блюда", description = "Контроллер управления блюдами")
public class DishController {

    private final DishService dishService;

    @PostMapping
    @Operation(summary = "Создание блюда", description = "Позволяет добавить новое блюдо в БД")
    public ResponseEntity<DishResponseDto> createDish(@Valid @RequestBody DishCreateDto dto) {
        DishResponseDto responseDto = dishService.createDish(dto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Получение блюд", description = "Получаем все блюда из БД")
    public ResponseEntity<Collection<DishResponseDto>> getAllDishes() {
        Collection<DishResponseDto> dishes = dishService.getAllDishes();

        return new ResponseEntity<>(dishes, HttpStatus.OK);
    }

    @GetMapping("/{dishId}")
    @Operation(summary = "Получение блюда", description = "Получаем блюдо по его id")
    public ResponseEntity<DishResponseDto> getDishById(@PathVariable Long dishId) {
        DishResponseDto dishResponse = dishService.getDishDtoById(dishId);

        return new ResponseEntity<>(dishResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{dishId}")
    @Operation(summary = "Удаление блюда", description = "Позволяет удалить блюдо по его id")
    public ResponseEntity<String> deleteDishById(@PathVariable Long dishId) {
        dishService.deleteDishById(dishId);

        return new ResponseEntity<>("Блюдо удалено", HttpStatus.OK);
    }

}
