package com.swiggy.authenticator.repositories;

import com.swiggy.authenticator.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomersDao extends JpaRepository<Customer, Integer> {
    public List<Customer> findAllByUserId(int userId);
}
