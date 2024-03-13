package com.swiggy.authenticator.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.swiggy.authenticator.authentication.CustomUserDetails;
import com.swiggy.authenticator.dtos.AuthorizeRequestDto;
import com.swiggy.authenticator.dtos.AuthorizeResponseDto;
import com.swiggy.authenticator.dtos.CustomerRequestDto;
import com.swiggy.authenticator.entities.Customer;
import com.swiggy.authenticator.entities.User;
import com.swiggy.authenticator.enums.UserRole;
import com.swiggy.authenticator.exceptions.UserNotAuthorizedException;
import com.swiggy.authenticator.services.AuthorizationsService;
import com.swiggy.authenticator.services.CustomersService;
import com.swiggy.authenticator.services.UsersService;
import org.hamcrest.core.IsNull;
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
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static com.swiggy.authenticator.TestConstants.*;
import static com.swiggy.authenticator.constants.ErrorMessage.*;
import static com.swiggy.authenticator.constants.SuccessMessage.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthorizationsControllerTest {
    public static final String BASE_URL = "/api/authorizations";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AuthorizationsService mockedAuthorizationsService;
    @MockBean
    private UsersService mockedUsersService;

    @Spy
    private User testUser = new User(TEST_USERNAME, new BCryptPasswordEncoder().encode(TEST_PASSWORD), UserRole.ADMIN);

    @BeforeEach
    public void setUp(){
        openMocks(this);
        when(this.mockedUsersService.loadUserByUsername(TEST_USERNAME)).thenReturn(new CustomUserDetails(this.testUser));
    }

    @Test
    public void test_shouldAuthorizeAUserWithAcceptableRole() throws Exception{
        when(this.mockedAuthorizationsService.create(eq(TEST_USERNAME), any(AuthorizeRequestDto.class))).thenReturn(new AuthorizeResponseDto(true, TEST_USER_ID));
        String mappedRequest = this.objectMapper.writeValueAsString(new AuthorizeRequestDto(List.of(UserRole.ADMIN)));

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest).with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(HttpStatus.CREATED.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.CREATED.value()))
                .andExpect(jsonPath("$.message").value(AUTHORIZATION_SUCCESSFULLY_CREATED))
                .andExpect(jsonPath("$.data.userId").value(TEST_USER_ID))
                .andExpect(jsonPath("$.data.authorized").value(true));
        verify(this.mockedAuthorizationsService, times(1)).create(eq(TEST_USERNAME), any(AuthorizeRequestDto.class));
    }

    @Test
    public void test_shouldThrow401UnauthorizedForAUserWithoutAnyAcceptableRole() throws Exception{
        when(this.mockedAuthorizationsService.create(eq(TEST_USERNAME), any(AuthorizeRequestDto.class))).thenThrow(UserNotAuthorizedException.class);
        String mappedRequest = this.objectMapper.writeValueAsString(new AuthorizeRequestDto(List.of(UserRole.CUSTOMER, UserRole.DELIVERY_AGENT)));

        this.mockMvc.perform(post(BASE_URL).contentType(MediaType.APPLICATION_JSON).content(mappedRequest).with(httpBasic(TEST_USERNAME, TEST_PASSWORD)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.status").value(HttpStatus.UNAUTHORIZED.name()))
                .andExpect(jsonPath("$.statusCode").value(HttpStatus.UNAUTHORIZED.value()))
                .andExpect(jsonPath("$.message").value(USER_NOT_AUTHORIZED))
                .andExpect(jsonPath("$.data").value(IsNull.nullValue()));
        verify(this.mockedAuthorizationsService, times(1)).create(eq(TEST_USERNAME), any(AuthorizeRequestDto.class));
    }

}
