package com.swiggy.authenticator.controllers;

import com.swiggy.authenticator.dtos.CustomerRequestDto;
import com.swiggy.authenticator.dtos.GenericHttpResponse;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.services.CustomersService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.swiggy.authenticator.constants.SuccessMessage.*;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {
    @Autowired
    private CustomersService customersService;
    @PostMapping("")
    public ResponseEntity<GenericHttpResponse> create(@Valid @RequestBody CustomerRequestDto customerRequestDto){
        Customer savedCustomer = this.customersService.create(customerRequestDto);
        return GenericHttpResponse.create(HttpStatus.CREATED, CUSTOMER_SUCCESSFULLY_CREATED, savedCustomer);
    }
}
