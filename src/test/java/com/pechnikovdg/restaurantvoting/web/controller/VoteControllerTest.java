package com.pechnikovdg.restaurantvoting.web.controller;

import com.pechnikovdg.restaurantvoting.model.Vote;
import com.pechnikovdg.restaurantvoting.repository.CrudVoteRepository;
import com.pechnikovdg.restaurantvoting.to.VoteTo;
import com.pechnikovdg.restaurantvoting.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.pechnikovdg.restaurantvoting.TestUtil.readFromJson;
import static com.pechnikovdg.restaurantvoting.restaurant.RestaurantTestData.restaurant1;
import static com.pechnikovdg.restaurantvoting.user.UserTestData.*;
import static com.pechnikovdg.restaurantvoting.vote.VoteTestData.*;
import static com.pechnikovdg.restaurantvoting.vote.VoteTestUtil.getNew;
import static com.pechnikovdg.restaurantvoting.vote.VoteTestUtil.getNewTo;
import static com.pechnikovdg.restaurantvoting.web.json.JsonUtil.writeValue;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteControllerTest extends AbstractControllerTest {

    static final String URL = "/api/votes/";

    @Autowired
    private CrudVoteRepository voteRepository;

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getCurrentVoteForAuthorizedUser() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/user/today"))
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getVotesForAuthorizedUser() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/user"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(VOTE_MATCHER.contentJson(List.of(vote1, vote9)));
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAmountForRestaurantOnDate() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/amount/filter?restaurantId=" + restaurant1.id()
                + "&date=" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void getAmountForRestaurantToday() throws Exception {
        perform(MockMvcRequestBuilders.get(URL + "/amount/today/filter?restaurantId=" + restaurant1.id()))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @WithUserDetails(value = USER_MAIL)
    void create() throws Exception {
        ValidationUtil.setVotingTimeLimit(LocalTime.MAX);
        VoteTo VoteTo = getNewTo();
        Vote newVote = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(writeValue(VoteTo)))
                .andExpect(status().isCreated());

        Vote created = readFromJson(action, Vote.class);
        int newId = created.id();
        newVote.setId(newId);
        VOTE_MATCHER.assertMatch(created, newVote);
        VOTE_MATCHER.assertMatch(voteRepository.getById(newId), newVote);
    }
}