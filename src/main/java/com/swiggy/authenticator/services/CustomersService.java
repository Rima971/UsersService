package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.CustomerRequestDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.exceptions.CustomerNotFoundException;
import com.swiggy.authenticator.repositories.CustomersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomersService {
    @Autowired
    private CustomersDao customersDao;
    public Customer create(CustomerRequestDto dto){
        Customer customer = new Customer(dto.getUsername(), dto.getPassword(), dto.getDeliveryLocationPincode());
        return this.customersDao.save(customer);
    }

    public Customer fetch(int customerId) throws CustomerNotFoundException {
        return this.customersDao.findById(customerId).orElseThrow(CustomerNotFoundException::new);
    }

    public List<Customer> fetchAll(Optional<Integer> userId){
        if (userId.isPresent()){
            return this.customersDao.findAllByUserId(userId.get());
        }
        return this.customersDao.findAll();
    }
}
