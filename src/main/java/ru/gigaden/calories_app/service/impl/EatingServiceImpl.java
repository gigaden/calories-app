package ru.gigaden.calories_app.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.gigaden.calories_app.dto.eating.EatingCreateDto;
import ru.gigaden.calories_app.dto.eating.EatingResponseDto;
import ru.gigaden.calories_app.entity.Eating;
import ru.gigaden.calories_app.entity.EatingDish;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.mapper.EatingMapper;
import ru.gigaden.calories_app.repository.EatingRepository;
import ru.gigaden.calories_app.service.EatingService;
import ru.gigaden.calories_app.service.UserService;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class EatingServiceImpl implements EatingService {

    private final EatingRepository eatingRepository;
    private final UserService userService;
    private final EatingMapper eatingMapper;

    @Override
    public EatingResponseDto createEating(EatingCreateDto dto) {
        User user = userService.getUserById(dto.userId());

        Eating eating = eatingMapper.mapCreateDtoToEating(dto);
        eating.setUser(user);

        if (eating.getMealItems() != null) {
            for (EatingDish eatingDish : eating.getMealItems()) {
                eatingDish.setEating(eating);
            }
        }

        Eating savedEating = eatingRepository.save(eating);

        return eatingMapper.mapEatingToResponseDto(savedEating);
    }

    @Override
    public Collection<EatingResponseDto> getAllEating() {
        return List.of();
    }

    @Override
    public Eating getEatingById(Long eatingId) {
        return null;
    }

    @Override
    public EatingResponseDto getEatingDtoById(Long eatingId) {
        return null;
    }

    @Override
    public void deleteEatingById(Long eatingId) {

    }
}
