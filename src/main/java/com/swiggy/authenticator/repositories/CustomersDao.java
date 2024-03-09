package com.swiggy.authenticator.repositories;

import com.swiggy.authenticator.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomersDao extends JpaRepository<Customer, Integer> {
}
