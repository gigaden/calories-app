package ru.gigaden.calories_app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gigaden.calories_app.dto.dish.DishCreateDto;
import ru.gigaden.calories_app.dto.dish.DishResponseDto;
import ru.gigaden.calories_app.entity.Dish;
import ru.gigaden.calories_app.exception.DishNotFoundException;
import ru.gigaden.calories_app.exception.UserNotFoundException;
import ru.gigaden.calories_app.mapper.DishMapper;
import ru.gigaden.calories_app.repository.DishRepository;
import ru.gigaden.calories_app.service.DishService;

import java.util.Collection;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class DishServiceImpl implements DishService {

    private final DishRepository dishRepository;
    private final DishMapper dishMapper;

    @Override
    public DishResponseDto createDish(DishCreateDto dto) {
        log.info("Пытаюсь добавить блюдо в бд {}", dto);
        Dish dish = dishMapper.mapCreateDtoToDish(dto);
        DishResponseDto responseDto = dishMapper.mapDishToResponseDto(dishRepository.save(dish));
        log.info("Создан блюдо с id = {}", responseDto.id());

        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<DishResponseDto> getAllDishes() {
        log.info("Пытаюсь получить коллекцию всех блюд");
        Collection<Dish> dishes = dishRepository.findAll();
        log.info("Коллекция пользователей получена");

        return dishes.stream().map(dishMapper::mapDishToResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Dish getDishById(Long dishId) {
        log.info("Пытаюсь получить блюдо с id = {}", dishId);
        Dish dish = dishRepository.findById(dishId).orElseThrow(() -> {
            log.warn("Блюдо с id = {} не найдено", dishId);
            return new DishNotFoundException("Блюдо не существует");
        });
        log.info("Получен блюдо с id = {}", dishId);

        return dish;
    }

    @Override
    public DishResponseDto getDishDtoById(Long dishId) {
        return dishMapper.mapDishToResponseDto(getDishById(dishId));
    }

    @Override
    public void deleteDishById(Long dishId) {
        log.info("Пытаюсь удалить блюдо с id = {}", dishId);
        Dish dish = getDishById(dishId);
        dishRepository.delete(dish);
        log.info("Блюдо с id = {} удалёно", dishId);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkDishIsExist(Long dishId) {
        log.info("Проверяю существует ли блюдо с id = {}", dishId);
        if (!dishRepository.existsDishById(dishId)) {
            throw new DishNotFoundException("Блюдо не существует");
        }
    }
}
