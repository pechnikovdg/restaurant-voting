package com.pechnikovdg.restaurantvoting.repository;

import com.pechnikovdg.restaurantvoting.model.Vote;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudVoteRepository extends JpaRepository<Vote, Integer> {

    @EntityGraph("Vote.userAndRestaurant")
    Vote getById(int id);

    @EntityGraph("Vote.userAndRestaurant")
    Vote getByUserIdAndDate(int userId, LocalDate date);

    @EntityGraph("Vote.userAndRestaurant")
    List<Vote> getByUserId(int userId);

    int countByRestaurantIdAndDate(int restaurantId, LocalDate localDate);
}
