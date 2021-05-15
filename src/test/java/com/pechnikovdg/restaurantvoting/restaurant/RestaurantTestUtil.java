package com.pechnikovdg.restaurantvoting.restaurant;

import com.pechnikovdg.restaurantvoting.model.Restaurant;

import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.RESTAURANT1_ID;

public class RestaurantTestUtil {

    public static Restaurant getNew() {
        return new Restaurant("newName");
    }

    public static Restaurant getUpdated() {
        return new Restaurant(RESTAURANT1_ID, "updatedName");
    }
}
