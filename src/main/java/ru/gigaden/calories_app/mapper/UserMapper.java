package ru.gigaden.calories_app.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.gigaden.calories_app.dto.user.UserCreateDto;
import ru.gigaden.calories_app.dto.user.UserResponseDto;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.entity.enums.Sex;
import ru.gigaden.calories_app.entity.enums.UsersActivity;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "bmr", expression = "java(calculateBMR(user))")
    UserResponseDto mapUserToResponseDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdOn", ignore = true)
    User mapCreateDtoToUser(UserCreateDto dto);

    /**
     * Метод вычисляет суточную норму калорий пользователя, исходя из его данных.
     * Ничего лучше, чем вынести расчёт в маппер я не придумал).
     * Хотел добавить это в слой юзер сервиса, после маппинга юзера в юзерреспонс сэтить вычисленное поле bmr,
     * но решил оставить так
     */
    default Double calculateBMR(User user) {
        double bmr = calculateBaseBmr(user);
        bmr = calculateWithActivity(bmr, user.getActivity());

        return switch (user.getTarget()) {
            case WEIGHT_LOSS -> bmr * 0.85;
            case WEIGHT_MAINTENANCE -> bmr;
            case WEIGHT_GAIN -> bmr * 1.15;
        };
    }

    default Double calculateBaseBmr(User user) {
        if (user.getSex() == Sex.MALE) {
            return 88.362
                    + (13.397 * user.getWeight())
                    + (4.799 * user.getHeight())
                    - (5.677 * user.getAge());
        } else {
            return 447.593
                    + (9.247 * user.getWeight())
                    + (3.098 * user.getHeight())
                    - (4.330 * user.getAge());
        }
    }

    default Double calculateWithActivity(double bmr, UsersActivity activity) {
        return switch (activity) {
            case SEDENTARY -> bmr * 1.2;
            case LIGHTLY_ACTIVE -> bmr * 1.375;
            case MODERATELY_ACTIVE -> bmr * 1.55;
            case VERY_ACTIVE -> bmr * 1.725;
        };
    }
}
