package ru.gigaden.calories_app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingDishCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.entity.Eating;
import ru.gigaden.calories_app.entity.EatingDish;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.exception.EatingNotFoundException;
import ru.gigaden.calories_app.exception.EatingValidationException;
import ru.gigaden.calories_app.mapper.EatingMapper;
import ru.gigaden.calories_app.repository.EatingRepository;
import ru.gigaden.calories_app.service.DishService;
import ru.gigaden.calories_app.service.EatingService;
import ru.gigaden.calories_app.service.UserService;
import ru.gigaden.calories_app.validator.Validator;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class EatingServiceImpl implements EatingService {

    private final EatingRepository eatingRepository;
    private final UserService userService;
    private final DishService dishService;
    private final EatingMapper eatingMapper;

    @Override
    @Transactional
    public EatingResponseDto createEating(EatingCreateDto dto) {
        log.info("пытаюсь добавить новый приём пищи {}", dto);
        User user = userService.getUserById(dto.userId());
        checkDishesIsCreate(dto.mealItems());
        Eating eating = eatingMapper.mapCreateDtoToEating(dto);
        eating.setUser(user);

        if (eating.getMealItems() != null) {
            for (EatingDish eatingDish : eating.getMealItems()) {
                eatingDish.setEating(eating);
            }
        }

        Eating savedEating = eatingRepository.save(eating);
        log.info("Приём пищи добавлен {}", savedEating);

        return eatingMapper.mapEatingToResponseDto(savedEating);
    }

    @Override
    public Collection<EatingResponseDto> getAllEating() {
        log.info("Пытаюсь получить коллекцию приёмов пищи");
        Collection<Eating> eatingResponseDto = eatingRepository.findAllWithMealItems();
        log.info("Коллекция приёмов пищи получена");

        return eatingResponseDto.stream().map(eatingMapper::mapEatingToResponseDto).toList();
    }

    @Override
    public Eating getEatingById(Long eatingId) {
        log.info("Пытаюсь получить приём пищи с id = {}", eatingId);
        Eating eating = eatingRepository.findById(eatingId).orElseThrow(() -> {
            log.warn("Приём пищи с id = {} не найден", eatingId);
            return new EatingNotFoundException("Приём пищи не существует");
        });
        log.info("Получен приём пищи с id = {}", eatingId);

        return eating;
    }

    @Override
    public EatingResponseDto getEatingDtoById(Long eatingId) {
        Eating eating = getEatingById(eatingId);
        return eatingMapper.mapEatingToResponseDto(eating);
    }

    @Override
    @Transactional
    public void deleteEatingById(Long eatingId) {
        log.info("Пытаюсь удалить приём пищи с id = {}", eatingId);
        Eating eating = getEatingById(eatingId);
        eatingRepository.delete(eating);
        log.info("Приём пищи с id = {} удалён", eatingId);
    }

    @Override
    public Collection<Eating> getEatingPerCurrentDayByUserId(Long userId) {
        userService.checkUserIsExist(userId);
        log.info("Пытаюсь получить все приёмы пищи пользователя с id = {} за сегодняшний день", userId);
        Collection<Eating> eatings = eatingRepository.findAllByDay(userId);
        log.info("Все приёмы пищи пользователя с id = {} за сегодняшний день получены", userId);

        return eatings;
    }

    @Override
    public Collection<Eating> getEatingByPeriodAndUserId(Long userId, LocalDate from, LocalDate to) {
        userService.checkUserIsExist(userId);
        Validator.checkDates(from, to);
        log.info("Пытаюсь получить все приёмы пищи пользователя с id = {} за период {} - {}", userId, from, to);
        Collection<Eating> eatings = eatingRepository.findAllByPeriod(userId, from, to);
        log.info("Все приёмы пищи пользователя с id = {} за период {} - {} получены", userId, from, to);

        return eatings;
    }

    private void checkDishesIsCreate(List<EatingDishCreateDto> eatingDishes) {
        if (eatingDishes.isEmpty()) {
            log.warn("Попытка добавить пустой список приёмов пищи");
            throw new EatingValidationException("Список приёмов пищи пуст");
        }
        for (EatingDishCreateDto dto : eatingDishes) {
            dishService.checkDishIsExist(dto.dishId());
        }
    }
}
