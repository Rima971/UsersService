package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.CustomerDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.repositories.CustomersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomersService {
    @Autowired
    private CustomersDao customersDao;
    public Customer create(CustomerDto dto){
        Customer customer = new Customer(dto.getUsername(), dto.getPassword(), dto.getDeliveryLocationPincode());
        return customersDao.save(customer);
    }
}
