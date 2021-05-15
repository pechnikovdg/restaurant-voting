package com.pechnikovdg.restaurantvoting.restaurant;

import com.pechnikovdg.restaurantvoting.TestMatcher;
import com.pechnikovdg.restaurantvoting.model.Restaurant;

import java.util.List;

import static com.pechnikovdg.restaurantvoting.model.BaseEntity.START_SEQ;

public class RestaurantTestData {

    public static final TestMatcher<Restaurant> RESTAURANT_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Restaurant.class);

    public static final int RESTAURANT1_ID = START_SEQ;

    public static final Restaurant restaurant1 = new Restaurant(RESTAURANT1_ID, "restaurant1");
    public static final Restaurant restaurant2 = new Restaurant(RESTAURANT1_ID + 1, "restaurant2");
    public static final Restaurant restaurant3 = new Restaurant(RESTAURANT1_ID + 2, "restaurant3");

    public static final List<Restaurant> restaurants = List.of(restaurant1, restaurant2, restaurant3);

}
