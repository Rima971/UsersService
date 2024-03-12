package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.AuthorizeResponseDto;
import com.swiggy.authenticator.entities.User;
import com.swiggy.authenticator.enums.UserRole;
import com.swiggy.authenticator.repositories.UsersDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static com.swiggy.authenticator.TestConstants.TEST_PASSWORD;
import static com.swiggy.authenticator.TestConstants.TEST_USERNAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AuthorizationServiceTest {
    @Mock
    private UsersDao mockedUsersDao;

    @InjectMocks
    private AuthorizationsService authorizationsService;

    private User testUser = new User(TEST_USERNAME, TEST_PASSWORD, UserRole.ADMIN);

    @BeforeEach
    public void setUp(){
        openMocks(this);
        when(this.mockedUsersDao.findByUsername(TEST_USERNAME)).thenReturn(Optional.ofNullable(this.testUser));
    }
    @Test
    public void test_shouldAuthorizeUserWithAcceptableRole(){
        AuthorizeResponseDto response = assertDoesNotThrow(()->this.authorizationsService.create(TEST_USERNAME, List.of(UserRole.ADMIN, UserRole.CUSTOMER)));
        assertNotNull(response);
        assertTrue(response.isAuthorized());
        assertEquals(this.testUser.getId(), response.getUserId());
    }

    @Test
    public void test_shouldUnauthorizeUserWithUnacceptableRole(){
        AuthorizeResponseDto response = assertDoesNotThrow(()->this.authorizationsService.create(TEST_USERNAME, List.of(UserRole.DELIVERY_AGENT, UserRole.CUSTOMER)));
        assertNotNull(response);
        assertFalse(response.isAuthorized());
        assertEquals(0, response.getUserId());
    }
}
