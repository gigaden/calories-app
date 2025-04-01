package ru.gigaden.calories_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.calories_app.entity.User;

/**
 * репа для пользователей
 */
public interface UserRepository extends JpaRepository<User, Long> {
}
