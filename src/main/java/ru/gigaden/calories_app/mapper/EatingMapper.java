package ru.gigaden.calories_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingDishCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingDishResponseDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.entity.Dish;
import ru.gigaden.calories_app.entity.Eating;
import ru.gigaden.calories_app.entity.EatingDish;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EatingMapper {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(target = "eatingDate", ignore = true)
    @Mapping(source = "mealItems", target = "mealItems")
    Eating mapCreateDtoToEating(EatingCreateDto dto);

    @Mapping(target = "dish", source = "dishId", qualifiedByName = "mapToDish")
    EatingDish mapEatingDishCreateDtoToEatingDish(EatingDishCreateDto dto);

    List<EatingDish> mapEatingDishCreateDtoListToEatingDish(List<EatingDishCreateDto> dtoList);

    EatingResponseDto mapEatingToResponseDto(Eating eating);

    EatingDishResponseDto mapEatingDishToResponseDto(EatingDish eatingDish);

    @Named("mapToDish")
    default Dish mapToDish(Long dishId) {
        if (dishId == null) {
            return null;
        }
        Dish dish = new Dish();
        dish.setId(dishId);
        return dish;
    }
}