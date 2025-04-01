package ru.gigaden.calories_app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gigaden.calories_app.dto.UserCreateDto;
import ru.gigaden.calories_app.dto.UserResponseDto;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.mapper.UserMapper;
import ru.gigaden.calories_app.repository.UserRepository;
import ru.gigaden.calories_app.service.UserService;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDto createUser(UserCreateDto dto) {
        log.info("Пытаюсь добавить пользователя в бд {}", dto);
        User user = userMapper.mapCreateDtoToUser(dto);
        UserResponseDto responseDto = userMapper.mapUserToResponseDto(userRepository.save(user));
        log.info("Создан пользователь с id = {}", responseDto.id());

        return responseDto;
    }
}
