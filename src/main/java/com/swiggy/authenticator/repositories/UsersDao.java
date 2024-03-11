package com.swiggy.authenticator.repositories;

import com.swiggy.authenticator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersDao extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);
}
