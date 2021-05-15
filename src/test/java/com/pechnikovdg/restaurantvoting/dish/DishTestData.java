package com.pechnikovdg.restaurantvoting.dish;

import com.pechnikovdg.restaurantvoting.TestMatcher;
import com.pechnikovdg.restaurantvoting.model.Dish;

import java.time.LocalDate;
import java.util.List;

import static com.pechnikovdg.restaurantvoting.model.BaseEntity.START_SEQ;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.*;

public class DishTestData {

    public static final TestMatcher<Dish> DISH_MATCHER = TestMatcher.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH1_ID = START_SEQ + 3;

    public static final Dish dish1 = new Dish(DISH1_ID, "dish1", 500, LocalDate.now(), restaurant1);
    public static final Dish dish2 = new Dish(DISH1_ID + 1, "dish2", 200, LocalDate.now(), restaurant1);
    public static final Dish dish3 = new Dish(DISH1_ID + 2, "dish3", 600, LocalDate.now(), restaurant1);

    public static final Dish dish4 = new Dish(DISH1_ID + 3, "dish4", 400, LocalDate.now(), restaurant2);
    public static final Dish dish5 = new Dish(DISH1_ID + 4, "dish5", 800, LocalDate.now(), restaurant2);

    public static final Dish dish6 = new Dish(DISH1_ID + 5, "dish6", 100, LocalDate.now(), restaurant3);
    public static final Dish dish7 = new Dish(DISH1_ID + 6, "dish7", 300, LocalDate.now(), restaurant3);
    public static final Dish dish8 = new Dish(DISH1_ID + 7, "dish8", 900, LocalDate.now(), restaurant3);

    public static final List<Dish> dishes = List.of(dish1, dish2, dish3, dish4, dish5, dish6, dish7, dish8);

}