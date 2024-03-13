package com.swiggy.authenticator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.authenticator.authentication.CustomUserDetails;
import com.swiggy.authenticator.dtos.CustomerRequestDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.services.CustomersService;
import com.swiggy.authenticator.services.UsersService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static com.swiggy.authenticator.TestConstants.*;
import static com.swiggy.authenticator.constants.SuccessMessage.CUSTOMER_SUCCESSFULLY_CREATED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomersControllerTest {
    public static final String BASE_URL = "/api/customers";
    public static final String PASSWORD = "93210eec-ceaa-47c9-bb6e-b03d9bcc040a";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CustomersService mockedCustomersService;
    @MockBean
    private UsersService mockedUsersService;

    @Spy
    private Customer testCustomer = new Customer(TEST_CUSTOMER_USERNAME, new BCryptPasswordEncoder().encode(TEST_CUSTOMER_PASSWORD), TEST_CUSTOMER_PINCODE);

    @BeforeEach
    public void setUp(){
        openMocks(this);
        when(this.mockedCustomersService.create(any(CustomerRequestDto.class))).thenReturn(this.testCustomer);
//        when(this.mockedUsersService.loadUserByUsername(TEST_CUSTOMER_USERNAME)).thenReturn(new CustomUserDetails(this.testCustomer.getUser()));
    }

    @Test
    public void test_shouldCreateACustomerSuccessfully() throws Exception{
        String mappedRequest = this.objectMapper.writeValueAsString(new CustomerRequestDto(TEST_CUSTOMER_USERNAME, TEST_CUSTOMER_PASSWORD, TEST_CUSTOMER_PINCODE));

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(CUSTOMER_SUCCESSFULLY_CREATED))
                .andExpect(jsonPath("$.data.id").value(TEST_CUSTOMER_ID))
                .andExpect(jsonPath("$.data.userId").value(TEST_USER_ID))
                .andExpect(jsonPath("$.data.deliveryLocationPincode").value(TEST_CUSTOMER_PINCODE));
        verify(this.mockedCustomersService, times(1)).create(any(CustomerRequestDto.class));
    }

}
