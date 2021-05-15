package com.pechnikovdg.restaurantvoting.user;

import com.pechnikovdg.restaurantvoting.TestMatcher;
import com.pechnikovdg.restaurantvoting.model.Role;
import com.pechnikovdg.restaurantvoting.model.User;

import java.util.Set;

import static com.pechnikovdg.restaurantvoting.model.BaseEntity.START_SEQ;

public class UserTestData {
    public static final TestMatcher<User> USER_MATCHER = TestMatcher.usingIgnoringFieldsComparator(User.class, "roles", "password");

    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";

    public static final int USER1_ID = START_SEQ + 13;

    public static final User user1 = new User(USER1_ID, "user@gmail.com", "firstName", "lastName", "password", Set.of(Role.USER));
    public static final User user6 = new User(USER1_ID + 5, "user5@gmail.com", "firstName5", "lastName", "password5", Set.of(Role.USER));
    public static final User user7 = new User(USER1_ID + 6, "user6@gmail.com", "firstName6", "lastName", "password6", Set.of(Role.USER));

}
