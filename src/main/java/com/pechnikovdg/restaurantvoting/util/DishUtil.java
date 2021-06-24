package com.pechnikovdg.restaurantvoting.util;

import com.pechnikovdg.restaurantvoting.model.Dish;
import com.pechnikovdg.restaurantvoting.model.Restaurant;
import com.pechnikovdg.restaurantvoting.to.DishTo;

public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(dishTo.getDescription(), dishTo.getPrice(), dishTo.getDate(), new Restaurant(dishTo.getRestaurantId(), null));
    }
}
