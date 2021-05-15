package com.pechnikovdg.restaurantvoting.web.controller;

import com.pechnikovdg.restaurantvoting.model.Restaurant;
import com.pechnikovdg.restaurantvoting.repository.CrudRestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.pechnikovdg.restaurantvoting.TestUtil.readFromJson;
import static com.pechnikovdg.restaurantvoting.dish.DishTestData.*;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.*;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestUtil.getNew;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestUtil.getUpdated;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.ADMIN_MAIL;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.USER_MAIL;
import static com.pechnikovdg.restaurantvoting.web.json.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantControllerTest extends AbstractControllerTest {

    static final String URL = "/api/restaurants/";

    @Autowired
    private CrudRestaurantRepository restaurantRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurants));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(RESTAURANT_MATCHER.contentJson(restaurant1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void getNotFound() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + 100500))
                .andDo(print())
                .andExpect(status().isUnprocessableEntity());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getForbidden() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + RESTAURANT1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + RESTAURANT1_ID))
                .andExpect(status().isNoContent());
        Assertions.assertNull(restaurantRepository.findById(RESTAURANT1_ID).orElse(null));
        Assertions.assertNotNull(restaurantRepository.findById(RESTAURANT1_ID + 1).orElse(null));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        Restaurant updated = getUpdated();
        perform(MockMvcRequestBuilders.put(URL + RESTAURANT1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(updated)))
                .andExpect(status().isNoContent());
        RESTAURANT_MATCHER.assertMatch(updated, restaurantRepository.findById(RESTAURANT1_ID).orElse(null));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        Restaurant newRestaurant = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(newRestaurant)))
                .andExpect(status().isCreated());

        Restaurant created = readFromJson(action, Restaurant.class);
        int newId = created.id();
        newRestaurant.setId(newId);
        RESTAURANT_MATCHER.assertMatch(created, newRestaurant);
        RESTAURANT_MATCHER.assertMatch(restaurantRepository.findById(newId).orElse(null), newRestaurant);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + RESTAURANT1_ID + "/dishes"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurantOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + RESTAURANT1_ID + "/dishes/filter?date="
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurantBetweenDates() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + RESTAURANT1_ID + "/dishes/filter?startDate=&endDate="
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurantToday() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + RESTAURANT1_ID + "/dishes/today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }
}
