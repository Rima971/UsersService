package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.CustomerRequestDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.exceptions.CustomerNotFoundException;
import com.swiggy.authenticator.repositories.CustomersDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.swiggy.authenticator.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

public class CustomersServiceTest {
    @Mock
    private CustomersDao mockedCustomersDao;

    @InjectMocks
    private CustomersService customersService;

    private final Customer testCustomer = new Customer(TEST_CUSTOMER_USERNAME, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_PINCODE);

    @BeforeEach
    public void setUp(){
        openMocks(this);
    }

    @Test
    public void test_shouldCreateACustomer(){
        when(this.mockedCustomersDao.save(any(Customer.class))).thenReturn(this.testCustomer);
        CustomerRequestDto request = new CustomerRequestDto(TEST_CUSTOMER_USERNAME, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_PINCODE);

        assertDoesNotThrow(()->{
            Customer savedCustomer = this.customersService.create(request);
            assertEquals(savedCustomer, this.testCustomer);
        });
        verify(this.mockedCustomersDao, times(1)).save(any(Customer.class));
    }

    @Test
    public void test_shouldFetchACustomer(){
        when(this.mockedCustomersDao.findById(anyInt())).thenReturn(Optional.of(this.testCustomer));

        assertDoesNotThrow(()->{
            Customer foundCustomer = this.customersService.fetch(TEST_CUSTOMER_ID);
            assertEquals(foundCustomer, this.testCustomer);
        });
        verify(this.mockedCustomersDao, times(1)).findById(TEST_CUSTOMER_ID);
    }

    @Test
    public void test_shouldThrowCustomerNotFoundWhenCustomerWithGivenIdDoesNotExist(){
        when(this.mockedCustomersDao.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, ()->this.customersService.fetch(TEST_CUSTOMER_ID));
    }

    @Test
    public void test_shouldFetchAllCustomersWhenNoFilterCriteriaIsGiven(){
        List<Customer> customers = new ArrayList<Customer>(List.of(this.testCustomer));
        when(this.mockedCustomersDao.findAll()).thenReturn(customers);

        assertDoesNotThrow(()->{
            List<Customer> fetchedList = this.customersService.fetchAll(Optional.empty());
            assertEquals(customers, fetchedList);
        });
        verify(this.mockedCustomersDao, times(1)).findAll();
    }

    @Test
    public void test_shouldFetchAllCustomersWithGivenUserId(){
        List<Customer> customers = new ArrayList<Customer>(List.of(this.testCustomer));
        when(this.mockedCustomersDao.findAllByUserId(TEST_USER_ID)).thenReturn(customers);

        assertDoesNotThrow(()->{
            List<Customer> fetchedList = this.customersService.fetchAll(Optional.of(TEST_USER_ID));
            assertEquals(customers, fetchedList);
        });
        verify(this.mockedCustomersDao, times(1)).findAllByUserId(TEST_USER_ID);
    }
}
