package ru.gigaden.calories_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gigaden.calories_app.dto.dish.DishCreateDto;
import ru.gigaden.calories_app.dto.dish.DishResponseDto;
import ru.gigaden.calories_app.entity.Dish;

@Mapper(componentModel = "spring")
public interface DishMapper {

    DishResponseDto mapDishToResponseDto(Dish dish);

    @Mapping(target = "id", ignore = true)
    Dish mapCreateDtoToDish(DishCreateDto dto);
}
