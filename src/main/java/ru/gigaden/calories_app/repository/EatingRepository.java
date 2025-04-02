package ru.gigaden.calories_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gigaden.calories_app.entity.Eating;

/**
 * репа для приёмов пищи
 */
public interface EatingRepository extends JpaRepository<Eating, Long> {
}
