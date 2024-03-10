package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.CustomerDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.exceptions.CustomerNotFound;
import com.swiggy.authenticator.repositories.CustomersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {
    @Autowired
    private CustomersDao customersDao;
    public Customer create(CustomerDto dto){
        Customer customer = new Customer(dto.getUsername(), dto.getPassword(), dto.getDeliveryLocationPincode());
        return this.customersDao.save(customer);
    }

    public Customer fetch(int customerId){
        return this.customersDao.findById(customerId).orElseThrow(CustomerNotFound::new);
    }

    public List<Customer> fetchAll(Optional<Integer> userId){
        if (userId.isPresent()){
            return this.customersDao.findAllByUserId(userId.get());
        }
        return this.customersDao.findAll();
    }
}
