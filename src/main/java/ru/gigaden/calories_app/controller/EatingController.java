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
import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.service.EatingService;

import java.util.Collection;

@RestController
@RequestMapping("/eatings")
@RequiredArgsConstructor
@Tag(name = "Приёмы пищи", description = "Контроллер управления приёмами пищи")
public class EatingController {

    private final EatingService eatingService;

    @PostMapping
    @Operation(summary = "Создание приёма пищи", description = "Позволяет добавить новый приём пищи в БД")
    public ResponseEntity<EatingResponseDto> createNewEating(@Valid @RequestBody EatingCreateDto dto) {
        EatingResponseDto eatingResponseDto = eatingService.createEating(dto);

        return new ResponseEntity<>(eatingResponseDto, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Получение приёмов пищи", description = "Получаем все приёмы пищи из БД")
    public ResponseEntity<Collection<EatingResponseDto>> getAllEating() {
        Collection<EatingResponseDto> eatings = eatingService.getAllEating();

        return new ResponseEntity<>(eatings, HttpStatus.OK);
    }

    @GetMapping("/{eatingId}")
    @Operation(summary = "Получение приёма пищи", description = "Получаем приём пищи по его id")
    public ResponseEntity<EatingResponseDto> getEatingById(@PathVariable Long eatingId) {
        EatingResponseDto eatingResponse = eatingService.getEatingDtoById(eatingId);

        return new ResponseEntity<>(eatingResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{eatingId}")
    @Operation(summary = "Удаление приёма пищи", description = "Позволяет удалить приём пищи по его id")
    public ResponseEntity<String> deleteEatingById(@PathVariable Long eatingId) {
        eatingService.deleteEatingById(eatingId);

        return new ResponseEntity<>("Приём пищи удалён", HttpStatus.OK);
    }
}
