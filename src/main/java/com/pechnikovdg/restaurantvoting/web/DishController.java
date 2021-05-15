package com.pechnikovdg.restaurantvoting.web;

import com.pechnikovdg.restaurantvoting.model.Dish;
import com.pechnikovdg.restaurantvoting.repository.CrudDishRepository;
import com.pechnikovdg.restaurantvoting.to.DishTo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
import java.util.List;

import static com.pechnikovdg.restaurantvoting.util.DishUtil.createNewFromTo;
import static com.pechnikovdg.restaurantvoting.util.DishUtil.updateFromTo;
import static com.pechnikovdg.restaurantvoting.util.ValidationUtil.*;

@RestController
@RequestMapping(value = DishController.URL_DISHES, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class DishController {

    public static final String URL_DISHES = "/api/dishes";
    private final CrudDishRepository dishRepository;

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/{id}")
    public Dish get(@PathVariable int id) {
        log.info("get dish with id {}", id);
        return checkNotFoundWithId(dishRepository.getById(id), id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        log.info("delete dish with id {}", id);
        checkNotFoundWithId(dishRepository.delete(id) != 0, id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody DishTo dishTo, @PathVariable int id) {
        log.info("update {}", dishTo);
        Assert.notNull(dishTo, "dishTo must not be null");
        assureIdConsistent(dishTo, id);
        Dish dish = updateFromTo(get(id), dishTo);
        checkNotFoundWithId(dishRepository.save(dish), id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> create(@Valid @RequestBody DishTo dishTo) {
        log.info("create {}", dishTo);
        Assert.notNull(dishTo, "dishTo must not be null");
        checkNew(dishTo);
        Dish created = dishRepository.save(createNewFromTo(dishTo));
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(URL_DISHES + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @GetMapping("/today")
    public List<Dish> getOnCurrentDate() {
        log.info("get all dishes on current date");
        return dishRepository.getByDate(LocalDate.now());
    }
}
