package ru.gigaden.calories_app.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.gigaden.calories_app.entity.Eating;

import java.time.LocalDate;
import java.util.List;

/**
 * репа для приёмов пищи
 */
public interface EatingRepository extends JpaRepository<Eating, Long> {

    @Query("SELECT e FROM Eating e LEFT JOIN FETCH e.mealItems")
    List<Eating> findAllWithMealItems();

    /**
     * Ищем все приёмы пищи пользователя за текущий день.
     */
    @Query("""
                SELECT e
                FROM Eating e
                LEFT JOIN FETCH e.mealItems ed 
                LEFT JOIN FETCH ed.dish 
                WHERE e.user.id = :userId 
                AND CAST(e.eatingDate AS LocalDate) = CURRENT_DATE
            """)
    List<Eating> findAllByDay(Long userId);

    /**
     * Ищем все приёмы пищи за выбранный период
     *
     * @param userId - id пользователя
     * @param from   - дата начала фильтрации
     * @param to     - дата окончания фильтрации
     */
    @Query("""
                SELECT e
                FROM Eating e
                LEFT JOIN FETCH e.mealItems ed 
                LEFT JOIN FETCH ed.dish 
                WHERE e.user.id = :userId 
                AND CAST(e.eatingDate AS LocalDate) BETWEEN :from AND :to
            """)
    List<Eating> findAllByPeriod(Long userId, LocalDate from, LocalDate to);
}
