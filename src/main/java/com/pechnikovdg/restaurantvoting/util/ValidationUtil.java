package com.pechnikovdg.restaurantvoting.util;

import com.pechnikovdg.restaurantvoting.HasId;
import com.pechnikovdg.restaurantvoting.error.IllegalRequestDataException;
import com.pechnikovdg.restaurantvoting.error.NotFoundException;
import lombok.Setter;

import java.time.LocalTime;

public class ValidationUtil {

    @Setter
    private static LocalTime votingTimeLimit = LocalTime.of(11, 0);

    private ValidationUtil() {
    }

    public static void checkVotingTime(LocalTime localTime) {
        if (localTime.isAfter(votingTimeLimit)) {
            throw new IllegalRequestDataException("You are allowed to update your vote only until 11:00");
        }
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        checkNotFoundWithId(object != null, id);
        return object;
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void checkRepeatedVote(boolean repeated) {
        if (repeated) {
            throw new IllegalRequestDataException("You have already voted today");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
}