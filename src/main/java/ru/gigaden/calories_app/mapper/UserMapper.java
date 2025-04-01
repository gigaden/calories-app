package ru.gigaden.calories_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gigaden.calories_app.dto.UserCreateDto;
import ru.gigaden.calories_app.dto.UserResponseDto;
import ru.gigaden.calories_app.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponseDto mapUserToResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    User mapCreateDtoToUser(UserCreateDto dto);
}
