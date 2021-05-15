package com.pechnikovdg.restaurantvoting.util;

import com.pechnikovdg.restaurantvoting.model.Dish;
import com.pechnikovdg.restaurantvoting.model.Restaurant;
import com.pechnikovdg.restaurantvoting.to.DishTo;

public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(dishTo.getDescription(), dishTo.getPrice(), dishTo.getDate(), new Restaurant(dishTo.getRestaurantId(), null));
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setDescription(dishTo.getDescription());
        dish.setPrice(dishTo.getPrice());
        dish.setDate(dishTo.getDate());
        dish.setRestaurant(new Restaurant(dishTo.getRestaurantId(), null));
        return dish;
    }
}
