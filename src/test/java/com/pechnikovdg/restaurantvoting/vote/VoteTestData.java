package com.pechnikovdg.restaurantvoting.vote;

import com.pechnikovdg.restaurantvoting.TestMatcher;
import com.pechnikovdg.restaurantvoting.model.Vote;

import java.time.LocalDate;

import static com.pechnikovdg.restaurantvoting.model.BaseEntity.START_SEQ;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.restaurant1;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.restaurant2;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.*;

public class VoteTestData {

    public static final TestMatcher<Vote> VOTE_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Vote.class, "restaurant", "user");

    public static final int VOTE1_ID = START_SEQ + 21;

    public static final Vote vote1 = new Vote(VOTE1_ID, LocalDate.now().minusDays(2), user1, restaurant1);
    public static final Vote vote6 = new Vote(VOTE1_ID + 5, LocalDate.now().minusDays(2), user6, restaurant1);
    public static final Vote vote7 = new Vote(VOTE1_ID + 6, LocalDate.now().minusDays(2), user7, restaurant1);
    public static final Vote vote9 = new Vote(VOTE1_ID + 8, LocalDate.now().minusDays(1), user1, restaurant2);
    public static final Vote vote15 = new Vote(VOTE1_ID + 14, LocalDate.now().minusDays(1), user7, restaurant1);

}