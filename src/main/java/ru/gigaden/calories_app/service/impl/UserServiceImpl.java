package ru.gigaden.calories_app.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.gigaden.calories_app.dto.user.UserCreateDto;
import ru.gigaden.calories_app.dto.user.UserResponseDto;
import ru.gigaden.calories_app.dto.user.UserUpdateDto;
import ru.gigaden.calories_app.entity.User;
import ru.gigaden.calories_app.exception.EmailIsExistException;
import ru.gigaden.calories_app.exception.UserNotFoundException;
import ru.gigaden.calories_app.mapper.UserMapper;
import ru.gigaden.calories_app.repository.UserRepository;
import ru.gigaden.calories_app.service.UserService;

import java.util.Collection;

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
        checkEmailIsExist(dto.email());
        User user = userMapper.mapCreateDtoToUser(dto);
        UserResponseDto responseDto = userMapper.mapUserToResponseDto(userRepository.save(user));
        log.info("Создан пользователь с id = {}", responseDto.id());

        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public Collection<UserResponseDto> getAllUsers() {
        log.info("Пытаюсь получить коллекцию всех пользователей");
        Collection<User> users = userRepository.findAll();
        log.info("Коллекция пользователей получена");

        return users.stream().map(userMapper::mapUserToResponseDto).toList();
    }

    @Override
    @Transactional(readOnly = true)
    public User getUserById(Long userId) {
        log.info("Пытаюсь получить пользователя с id = {}", userId);
        User user = userRepository.findById(userId).orElseThrow(() -> {
            log.warn("Пользователь с id = {} не найден", userId);
            return new UserNotFoundException("Пользователь не существует");
        });
        log.info("Получен пользователь с id = {}", userId);

        return user;
    }

    @Override
    public UserResponseDto getUserDtoById(Long userId) {
        return userMapper.mapUserToResponseDto(getUserById(userId));
    }

    @Override
    public void deleteUserById(Long userId) {
        log.info("Пытаюсь удалить пользователя с id = {}", userId);
        User user = getUserById(userId);
        userRepository.delete(user);
        log.info("Пользователь с id = {} удалён", userId);
    }

    @Override
    public UserResponseDto updateUserById(Long userId, UserUpdateDto dto) {
        log.info("Пытаюсь обновить пользователя с id = {}", userId);
        User oldUser = getUserById(userId);
        checkEmailIsExist(dto.email());
        setUsersFields(oldUser, dto);

        userRepository.save(oldUser);
        log.info("Пользователь с id = {} обновлён", userId);

        return userMapper.mapUserToResponseDto(oldUser);
    }

    @Override
    @Transactional(readOnly = true)
    public void checkUserIsExist(Long userId) {
        log.info("Проверяю существует ли пользователь с id = {}", userId);
        if (!userRepository.existsUserById(userId)) {
            throw new UserNotFoundException("Пользователь не существует");
        }
    }

    @Override
    @Transactional(readOnly = true)
    public void checkEmailIsExist(String email) {
        log.info("Проверяю уникальность email");
        if (userRepository.findUserByEmail(email).isPresent()) {
            log.warn("Email не уникален");
            throw new EmailIsExistException("Не уникальный email");
        }
        log.info("Email уникален");
    }

    private void setUsersFields(User oldUser, UserUpdateDto dto) {
        oldUser.setName(dto.name());
        oldUser.setEmail(dto.email());
        oldUser.setAge(dto.age());
        oldUser.setWeight(dto.weight());
        oldUser.setHeight(dto.height());
        oldUser.setTarget(dto.target());
    }
}
