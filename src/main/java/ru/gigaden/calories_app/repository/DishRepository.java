package ru.gigaden.calories_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.calories_app.entity.Dish;

/**
 * репа для блюд
 */
public interface DishRepository extends JpaRepository<Dish, Long> {
}