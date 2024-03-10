package com.swiggy.authenticator.repositories;

import com.swiggy.authenticator.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersDao extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
