package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.CustomerDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.repositories.CustomersDao;
import jakarta.persistence.Table;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static com.swiggy.authenticator.TestConstants.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CustomerServiceTest {
    @Mock
    private CustomersDao customersDao;

    @InjectMocks
    private CustomersService customersService;

    private Customer testCustomer = new Customer(TEST_CUSTOMER_USERNAME, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_PINCODE);

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    @Test
    public void test_shouldCreateACustomer(){
        when(this.customersDao.save(any(Customer.class))).thenReturn(this.testCustomer);
        CustomerDto request = new CustomerDto(TEST_CUSTOMER_USERNAME, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_PINCODE);
        assertDoesNotThrow(()->{
            Customer savedCustomer = this.customersService.create(request);
            assertEquals(savedCustomer, this.testCustomer);
        });

        verify(this.customersDao, times(1)).save(any(Customer.class));
    }
}
