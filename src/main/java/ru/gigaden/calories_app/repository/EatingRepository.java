package ru.gigaden.calories_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gigaden.calories_app.entity.Eating;

import java.util.List;

/**
 * репа для приёмов пищи
 */
public interface EatingRepository extends JpaRepository<Eating, Long> {

    @Query("SELECT DISTINCT e FROM Eating e LEFT JOIN FETCH e.mealItems")
    List<Eating> findAllWithMealItems();
}
