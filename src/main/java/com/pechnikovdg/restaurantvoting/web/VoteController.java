package com.pechnikovdg.restaurantvoting.web;

import com.pechnikovdg.restaurantvoting.model.Vote;
import com.pechnikovdg.restaurantvoting.repository.CrudVoteRepository;
import com.pechnikovdg.restaurantvoting.to.VoteTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static com.pechnikovdg.restaurantvoting.util.ValidationUtil.*;
import static com.pechnikovdg.restaurantvoting.util.VoteUtil.createNewFromTo;
import static com.pechnikovdg.restaurantvoting.util.VoteUtil.updateFromTo;

@RestController
@RequestMapping(value = VoteController.URL_VOTES, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class VoteController {

    public static final String URL_VOTES = "/api/votes";
    private final CrudVoteRepository voteRepository;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Vote get(@PathVariable int id) {
        log.info("get vote with id {}", id);
        return checkNotFoundWithId(voteRepository.getById(id), id);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user/today")
    public Vote getCurrentVoteForAuthorizedUser() {
        log.info("get today's vote for authorized user");
        return voteRepository.getByUserIdAndDate(SecurityUtil.get().id(), LocalDate.now());
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping("/user")
    public List<Vote> getVotesForAuthorizedUser() {
        log.info("get votes for authorized user");
        return voteRepository.getByUserId(SecurityUtil.get().id());
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/filter", params = "restaurantId")
    public List<Vote> getForRestaurant(@RequestParam int restaurantId) {
        log.info("get votes for restaurant with id {}", restaurantId);
        return voteRepository.getByRestaurantId(restaurantId);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/filter", params = "userId")
    public List<Vote> getByUser(@RequestParam int userId) {
        log.info("get votes by user with id {}", userId);
        return voteRepository.getByUserId(userId);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/amount/filter", params = {"restaurantId", "date"})
    public int getAmountForRestaurantOnDate(@RequestParam int restaurantId,
                                            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get amount of votes for restaurant with id {} on date {}", restaurantId, date);
        return voteRepository.countByRestaurantIdAndDate(restaurantId, date);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/amount/today/filter", params = "restaurantId")
    public int getAmountForRestaurantToday(@RequestParam int restaurantId) {
        log.info("get amount of votes for restaurant with id {} today", restaurantId);
        return voteRepository.countByRestaurantIdAndDate(restaurantId, LocalDate.now());
    }

    @PreAuthorize("hasRole('USER')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestBody VoteTo voteTo) {
        log.info("create vote for restaurant with id {}", voteTo.getRestaurantId());
        Assert.notNull(voteTo, "voteTo must not be null");
        checkVotingTime(LocalTime.now());
        checkNew(voteTo);
        checkRepeatedVote(getCurrentVoteForAuthorizedUser() != null);
        Vote created = voteRepository.save(createNewFromTo(voteTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_VOTES + "{/id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody VoteTo voteTo) {
        log.info("update vote");
        Assert.notNull(voteTo, "voteTo must not be null");
        int id = getCurrentVoteForAuthorizedUser().getId();
        voteTo.setId(id);
        checkVotingTime(LocalTime.now());
        voteRepository.save(updateFromTo(get(id), voteTo));
    }
}