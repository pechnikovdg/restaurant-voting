package com.pechnikovdg.restaurantvoting.repository;

import com.pechnikovdg.restaurantvoting.model.Dish;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudDishRepository extends JpaRepository<Dish, Integer> {

    @EntityGraph("Dish.restaurant")
    Dish getById(int id);

    List<Dish> getByRestaurantId(int id);

    List<Dish> getByRestaurantIdAndDate(int id, LocalDate date);

    @EntityGraph("Dish.restaurant")
    List<Dish> getByDate(LocalDate date);

    @Query("SELECT d FROM Dish d WHERE d.restaurant.id = :id AND d.date >= :startDate AND d.date <= :endDate")
    List<Dish> getBetweenDatesIncluded(@Param("id") int id, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    @Modifying
    @Transactional
    @Query("DELETE FROM Dish d WHERE d.id = ?1")
    int delete(int id);

}
