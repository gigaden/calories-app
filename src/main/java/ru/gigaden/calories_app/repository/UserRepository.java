package ru.gigaden.calories_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.calories_app.entity.User;

import java.util.Optional;

/**
 * репа для пользователей
 */
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsUserById(Long userId);

    Optional<User> findUserByEmail(String email);
}
