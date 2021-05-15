package com.pechnikovdg.restaurantvoting.vote;

import com.pechnikovdg.restaurantvoting.model.Vote;
import com.pechnikovdg.restaurantvoting.to.VoteTo;

import java.time.LocalDate;

import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.restaurant1;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.user1;

public class VoteTestUtil {

    public static Vote getNew() {
        return new Vote(null, LocalDate.now(), user1, restaurant1);
    }

    public static VoteTo getNewTo() {
        return new VoteTo(RESTAURANT1_ID);
    }
}
