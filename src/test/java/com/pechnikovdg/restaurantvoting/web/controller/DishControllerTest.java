package com.pechnikovdg.restaurantvoting.web.controller;

import com.pechnikovdg.restaurantvoting.model.Dish;
import com.pechnikovdg.restaurantvoting.repository.CrudDishRepository;
import com.pechnikovdg.restaurantvoting.to.DishTo;
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
import static com.pechnikovdg.restaurantvoting.dish.DishTestUtil.*;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.RESTAURANT1_ID;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.ADMIN_MAIL;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.USER_MAIL;
import static com.pechnikovdg.restaurantvoting.web.json.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DishControllerTest extends AbstractControllerTest {

    static final String URL = "/api/dishes/";

    @Autowired
    private CrudDishRepository dishRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + DISH1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(dish1));
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
        perform(MockMvcRequestBuilders.delete(URL + DISH1_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(URL + DISH1_ID))
                .andExpect(status().isNoContent());
        Assertions.assertNull(dishRepository.getById(DISH1_ID));
        Assertions.assertNotNull(dishRepository.getById(DISH1_ID + 1));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void update() throws Exception {
        DishTo dishTo = getUpdatedTo();
        Dish updated = getUpdated();
        perform(MockMvcRequestBuilders.put(URL + DISH1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(dishTo)))
                .andExpect(status().isNoContent());
        DISH_MATCHER.assertMatch(updated, dishRepository.getById(DISH1_ID));
    }

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void create() throws Exception {
        DishTo dishTo = getNewTo();
        Dish newDish = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(dishTo)))
                .andExpect(status().isCreated());

        Dish created = readFromJson(action, Dish.class);
        int newId = created.id();
        newDish.setId(newId);
        DISH_MATCHER.assertMatch(created, newDish);
        DISH_MATCHER.assertMatch(dishRepository.getById(newId), newDish);
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getOnCurrentDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "today"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(dishes));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurant() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/filter?restaurantId=" + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurantOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/filter?restaurantId=" + RESTAURANT1_ID + "&date="
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurantBetweenDates() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/filter?restaurantId=" + RESTAURANT1_ID + "&startDate=&endDate="
                + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getDishesByRestaurantToday() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/today/filter?restaurantId=" + RESTAURANT1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(DISH_MATCHER.contentJson(List.of(dish1, dish2, dish3)));
    }
}