package com.pechnikovdg.restaurantvoting.web;

import com.pechnikovdg.restaurantvoting.model.Dish;
import com.pechnikovdg.restaurantvoting.model.Restaurant;
import com.pechnikovdg.restaurantvoting.repository.CrudDishRepository;
import com.pechnikovdg.restaurantvoting.repository.CrudRestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static com.pechnikovdg.restaurantvoting.util.ValidationUtil.*;

@RestController
@RequestMapping(value = RestaurantController.URL_RESTAURANTS, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class RestaurantController {

    public static final String URL_RESTAURANTS = "/api/restaurants";
    private final CrudRestaurantRepository restaurantRepository;
    private final CrudDishRepository dishRepository;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping
    public List<Restaurant> getAll() {
        log.info("get all restaurants");
        return restaurantRepository.findAll();
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Restaurant get(@PathVariable int id) {
        log.info("get restaurant with id {}", id);
        return checkNotFoundWithId(restaurantRepository.findById(id).orElse(null), id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete restaurant with id {}", id);
        checkNotFoundWithId(restaurantRepository.delete(id) != 0, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        log.info("update {} with id {}", restaurant, id);
        Assert.notNull(restaurant, "restaurant must not be null");
        assureIdConsistent(restaurant, id);
        checkNotFoundWithId(restaurantRepository.save(restaurant), id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> create(@Valid @RequestBody Restaurant restaurant) {
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        checkNew(restaurant);
        Restaurant created = restaurantRepository.save(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_RESTAURANTS + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/dishes")
    public List<Dish> getDishesByRestaurant(@PathVariable int id) {
        log.info("get dishes by restaurant with id {}", id);
        return dishRepository.getByRestaurantId(id);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/{id}/dishes/filter", params = "date")
    public List<Dish> getDishesByRestaurantOnDate(@PathVariable int id,
                                                  @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        log.info("get dishes by restaurant with id {} on date {}", id, date);
        return dishRepository.getByRestaurantIdAndDate(id, date);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping(value = "/{id}/dishes/filter", params = {"startDate", "endDate"})
    public List<Dish> getDishesByRestaurantBetweenDates(@PathVariable int id,
                                                        @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                                        @Nullable @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        log.info("get between dates ({} - {}) included", startDate, endDate);
        LocalDate firstDate = startDate != null ? startDate : LocalDate.of(2000, 1, 1);
        LocalDate secondDate = endDate != null ? endDate : LocalDate.of(3000, 1, 1);
        return dishRepository.getBetweenDatesIncluded(id, firstDate, secondDate);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}/dishes/today")
    public List<Dish> getDishesByRestaurantToday(@PathVariable int id) {
        log.info("get dishes by restaurant with id {} today", id);
        return dishRepository.getByRestaurantIdAndDate(id, LocalDate.now());
    }
}
