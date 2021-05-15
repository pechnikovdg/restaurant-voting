package com.pechnikovdg.restaurantvoting.util;

import com.pechnikovdg.restaurantvoting.model.Restaurant;
import com.pechnikovdg.restaurantvoting.model.Vote;
import com.pechnikovdg.restaurantvoting.to.VoteTo;
import com.pechnikovdg.restaurantvoting.web.SecurityUtil;

import java.time.LocalDate;

public class VoteUtil {

    public static Vote createNewFromTo(VoteTo voteTo) {
        return new Vote(LocalDate.now(), SecurityUtil.get().getUser(), new Restaurant(voteTo.getRestaurantId(), null));
    }

    public static Vote updateFromTo(Vote vote, VoteTo voteTo) {
        vote.setRestaurant(new Restaurant(voteTo.getRestaurantId(), null));
        vote.setUser(SecurityUtil.get().getUser());
        return vote;
    }
}





