package com.pechnikovdg.restaurantvoting.dish;

import com.pechnikovdg.restaurantvoting.model.Dish;
import com.pechnikovdg.restaurantvoting.to.DishTo;

import java.time.LocalDate;

import static com.pechnikovdg.restaurantvoting.dish.DishTestData.DISH1_ID;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.*;

public class DishTestUtil {

    public static Dish getNew() {
        return new Dish(null, "newDescription", 111, LocalDate.now(), restaurant1);
    }

    public static DishTo getNewTo() {
        return new DishTo("newDescription", 111, LocalDate.now(), RESTAURANT1_ID);
    }

    public static Dish getUpdated() {
        return new Dish(DISH1_ID, "updatedDescription", 222, LocalDate.now(), restaurant2);
    }

    public static DishTo getUpdatedTo() {
        return new DishTo("updatedDescription", 222, LocalDate.now(), RESTAURANT1_ID + 2);
    }
}
