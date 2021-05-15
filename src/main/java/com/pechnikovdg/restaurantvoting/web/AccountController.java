package com.pechnikovdg.restaurantvoting.web;

import com.pechnikovdg.restaurantvoting.AuthUser;
import com.pechnikovdg.restaurantvoting.model.Role;
import com.pechnikovdg.restaurantvoting.model.User;
import com.pechnikovdg.restaurantvoting.repository.CrudUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Set;

import static com.pechnikovdg.restaurantvoting.util.ValidationUtil.assureIdConsistent;
import static com.pechnikovdg.restaurantvoting.util.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AccountController.URL_ACCOUNT, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Slf4j
public class AccountController {

    public static final String URL_ACCOUNT = "/api/account";

    private final CrudUserRepository userRepository;

    @PreAuthorize("isAnonymous()")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(value = HttpStatus.CREATED)
    public ResponseEntity<User> register(@Valid @RequestBody User user) {
        log.info("register {}", user);
        Assert.notNull(user, "user must no be null");
        checkNew(user);
        user.setRoles(Set.of(Role.USER));
        user = userRepository.save(user);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/account")
                .build().toUri();
        return ResponseEntity.created(uriOfNewResource).body(user);
    }

    @PreAuthorize("hasRole('USER')")
    @GetMapping
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get authenticated {}", authUser);
        return authUser.getUser();
    }

    @PreAuthorize("hasRole('USER')")
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        log.info("delete authenticated {}", authUser);
        userRepository.deleteById(authUser.id());
    }

    @PreAuthorize("hasRole('USER')")
    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {
        log.info("update {} to {}", authUser, user);
        User oldUser = authUser.getUser();
        assureIdConsistent(user, oldUser.id());
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        userRepository.save(user);
    }
}