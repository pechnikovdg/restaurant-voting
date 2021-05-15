package com.pechnikovdg.restaurantvoting.repository;

import com.pechnikovdg.restaurantvoting.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CrudUserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmailIgnoreCase(String email);
}
