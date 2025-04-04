package ru.gigaden.calories_app.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gigaden.calories_app.dto.dish.DishCreateDto;
import ru.gigaden.calories_app.dto.dish.DishResponseDto;
import ru.gigaden.calories_app.entity.Dish;
import ru.gigaden.calories_app.exception.DishNotFoundException;
import ru.gigaden.calories_app.mapper.DishMapper;
import ru.gigaden.calories_app.repository.DishRepository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DishServiceImplTest {

    @Mock
    private DishRepository dishRepository;

    @Spy
    private DishMapper dishMapper = Mappers.getMapper(DishMapper.class);

    @InjectMocks
    private DishServiceImpl dishService;

    private Dish testDish;
    private DishCreateDto testCreateDto;
    private DishResponseDto testResponseDto;

    @BeforeEach
    void setUp() {
        testDish = Dish.builder()
                .id(1L)
                .name("Test Dish")
                .calories(100.0)
                .proteins(10.0)
                .fats(5.0)
                .carbohydrates(20.0)
                .build();

        testCreateDto = new DishCreateDto(
                "Test Dish",
                100.0,
                10.0,
                5.0,
                20.0
        );

        testResponseDto = new DishResponseDto(
                1L,
                "Test Dish",
                100.0,
                10.0,
                5.0,
                20.0
        );
    }

    @Test
    void createDish_ShouldReturnDishResponseDto() {
        when(dishRepository.save(any(Dish.class))).thenReturn(testDish);

        DishResponseDto result = dishService.createDish(testCreateDto);

        verify(dishMapper).mapCreateDtoToDish(testCreateDto);
        verify(dishRepository).save(any(Dish.class));
        assertEquals(testResponseDto, result);
    }

    @Test
    void getAllDishes_ShouldReturnCollectionOfDishResponseDto() {
        when(dishRepository.findAll()).thenReturn(List.of(testDish));

        var result = dishService.getAllDishes();

        assertEquals(1, result.size());
        assertEquals(testResponseDto, result.iterator().next());
    }

    @Test
    void getDishById_WhenDishExists_ShouldReturnDish() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(testDish));

        Dish result = dishService.getDishById(1L);

        assertEquals(testDish, result);
    }

    @Test
    void getDishById_WhenDishNotExists_ShouldThrowException() {
        when(dishRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(DishNotFoundException.class, () -> dishService.getDishById(999L));
    }

    @Test
    void getDishDtoById_ShouldReturnDishResponseDto() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(testDish));

        DishResponseDto result = dishService.getDishDtoById(1L);

        assertEquals(testResponseDto, result);
    }

    @Test
    void deleteDishById_ShouldInvokeRepositoryDelete() {
        when(dishRepository.findById(1L)).thenReturn(Optional.of(testDish));

        dishService.deleteDishById(1L);

        verify(dishRepository).delete(testDish);
    }

    @Test
    void checkDishIsExist_WhenDishExists_ShouldNotThrowException() {
        when(dishRepository.existsDishById(1L)).thenReturn(true);

        assertDoesNotThrow(() -> dishService.checkDishIsExist(1L));
    }

    @Test
    void checkDishIsExist_WhenDishNotExists_ShouldThrowException() {
        when(dishRepository.existsDishById(999L)).thenReturn(false);

        assertThrows(DishNotFoundException.class, () -> dishService.checkDishIsExist(999L));
    }

    @Test
    void getAllDishes_WhenNoDishes_ShouldReturnEmptyCollection() {
        when(dishRepository.findAll()).thenReturn(Collections.emptyList());

        var result = dishService.getAllDishes();

        assertTrue(result.isEmpty());
    }
}