package ru.gigaden.calories_app.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingDishCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingDishResponseDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.entity.Dish;
import ru.gigaden.calories_app.entity.Eating;
import ru.gigaden.calories_app.entity.EatingDish;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.exception.EatingNotFoundException;
import ru.gigaden.calories_app.exception.EatingValidationException;
import ru.gigaden.calories_app.mapper.EatingMapper;
import ru.gigaden.calories_app.repository.EatingRepository;
import ru.gigaden.calories_app.service.DishService;
import ru.gigaden.calories_app.service.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.anyLong;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EatingServiceImplTest {

    @Mock
    private EatingRepository eatingRepository;

    @Mock
    private UserService userService;

    @Mock
    private DishService dishService;

    @Spy
    private EatingMapper eatingMapper = Mappers.getMapper(EatingMapper.class);

    @InjectMocks
    private EatingServiceImpl eatingService;

    private Eating testEating;
    private EatingCreateDto testCreateDto;
    private EatingResponseDto testResponseDto;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = User.builder()
                .id(1L)
                .email("test@example.com")
                .build();

        EatingDish testEatingDish = EatingDish.builder()
                .id(1L)
                .eating(testEating)  // <-- добавляем связь
                .dish(Dish.builder().id(1L).build())
                .portionCount(2.0)
                .build();

        testEating = Eating.builder()
                .id(1L)
                .user(testUser)
                .eatingDate(LocalDateTime.now())
                .mealItems(new ArrayList<>())
                .build();

        testEating.getMealItems().add(testEatingDish);

        testCreateDto = new EatingCreateDto(
                1L,
                List.of(new EatingDishCreateDto(1L, 2.0))
        );

        testResponseDto = new EatingResponseDto(
                1L,
                1L,
                testEating.getEatingDate(),
                List.of(new EatingDishResponseDto(1L, 1L, 1L, 2.0))
        );
    }

    @Test
    void createEating_WithValidData_ShouldReturnResponseDto() {
        // Устанавливаем двустороннюю связь между Eating и EatingDish
        testEating.getMealItems().forEach(dish -> dish.setEating(testEating));

        when(userService.getUserById(anyLong())).thenReturn(testUser);
        when(eatingRepository.save(any(Eating.class))).thenReturn(testEating);
        doNothing().when(dishService).checkDishIsExist(anyLong());

        // Явно вызываем реальный маппер для Spy
        doCallRealMethod().when(eatingMapper).mapEatingToResponseDto(any());
        doCallRealMethod().when(eatingMapper).mapEatingDishToResponseDto(any());

        EatingResponseDto result = eatingService.createEating(testCreateDto);

        // Проверяем заполнение eatingId
        EatingDishResponseDto actualDishDto = result.mealItems().get(0);
        assertEquals(testEating.getId(), actualDishDto.eatingId());

        // Остальные проверки
        verify(eatingMapper).mapCreateDtoToEating(testCreateDto);
        verify(eatingRepository).save(any(Eating.class));
        assertEquals(testResponseDto.userId(), result.userId());
    }

    @Test
    void createEating_WithEmptyMealItems_ShouldThrowException() {
        EatingCreateDto invalidDto = new EatingCreateDto(1L, Collections.emptyList());

        assertThrows(EatingValidationException.class, () -> eatingService.createEating(invalidDto));
    }

    @Test
    void getEatingById_WhenExists_ShouldReturnEating() {
        when(eatingRepository.findById(1L)).thenReturn(Optional.of(testEating));

        Eating result = eatingService.getEatingById(1L);

        assertEquals(testEating, result);
    }

    @Test
    void getEatingById_WhenNotExists_ShouldThrowException() {
        when(eatingRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(EatingNotFoundException.class, () -> eatingService.getEatingById(999L));
    }

    @Test
    void deleteEatingById_ShouldInvokeRepositoryDelete() {
        when(eatingRepository.findById(1L)).thenReturn(Optional.of(testEating));

        eatingService.deleteEatingById(1L);

        verify(eatingRepository).delete(testEating);
    }

    @Test
    void getEatingPerCurrentDayByUserId_ShouldReturnEatings() {
        doNothing().when(userService).checkUserIsExist(1L);
        when(eatingRepository.findAllByDay(1L)).thenReturn(List.of(testEating));

        var result = eatingService.getEatingPerCurrentDayByUserId(1L);

        assertEquals(1, result.size());
        assertEquals(testEating, result.iterator().next());
    }

    @Test
    void getEatingByPeriodAndUserId_WithValidDates_ShouldReturnEatings() {
        LocalDate from = LocalDate.now().minusDays(7);
        LocalDate to = LocalDate.now();

        doNothing().when(userService).checkUserIsExist(1L);
        when(eatingRepository.findAllByPeriod(1L, from, to)).thenReturn(List.of(testEating));

        var result = eatingService.getEatingByPeriodAndUserId(1L, from, to);

        assertEquals(1, result.size());
        assertEquals(testEating, result.iterator().next());
    }

    @Test
    void checkDishesIsCreate_WithInvalidDishId_ShouldThrowException() {
        EatingCreateDto invalidDto = new EatingCreateDto(1L, List.of(new EatingDishCreateDto(999L, 1.0)));
        doThrow(new EatingValidationException("Dish not found")).when(dishService).checkDishIsExist(999L);

        assertThrows(EatingValidationException.class,
                () -> eatingService.createEating(invalidDto));
    }

    @Test
    void getAllEating_WhenNoData_ShouldReturnEmptyCollection() {
        when(eatingRepository.findAllWithMealItems()).thenReturn(Collections.emptyList());

        var result = eatingService.getAllEating();

        assertTrue(result.isEmpty());
    }
}