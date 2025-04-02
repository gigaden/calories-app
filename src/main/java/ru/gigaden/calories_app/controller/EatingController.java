package ru.gigaden.calories_app.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.service.EatingService;

@RestController
@RequestMapping("/eating")
@RequiredArgsConstructor
@Tag(name = "Приём пищи", description = "Контроллер управления приёмами пищи")
public class EatingController {

    private final EatingService eatingService;

    @PostMapping
    public ResponseEntity<EatingResponseDto> createNewEating(@Valid @RequestBody EatingCreateDto dto) {
        EatingResponseDto eatingResponseDto = eatingService.createEating(dto);

        return new ResponseEntity<>(eatingResponseDto, HttpStatus.CREATED);
    }
}
