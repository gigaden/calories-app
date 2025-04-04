package ru.gigaden.calories_app.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "dishes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "calories", nullable = false)
    private Double calories;

    @Column(name = "proteins", nullable = false)
    private Double proteins;

    @Column(name = "fats", nullable = false)
    private Double fats;

    @Column(name = "carbohydrates", nullable = false)
    private Double carbohydrates;
}
