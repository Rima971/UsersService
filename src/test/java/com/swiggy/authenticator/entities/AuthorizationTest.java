package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.UserRole;
import com.swiggy.authenticator.repositories.UsersDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static com.swiggy.authenticator.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

public class AuthorizationTest {
    @Mock
    private UsersDao usersDao;

    @BeforeEach
    public void setup(){
        openMocks(this);
    }

    @Test
    public void test_shouldAuthorizeAnUserWithTheAcceptableRole(){
        List<UserRole> roles = new ArrayList<>(List.of(UserRole.ADMIN, UserRole.CUSTOMER));
        User user = new User(TEST_USERNAME, TEST_PASSWORD, UserRole.ADMIN);
        when(this.usersDao.findByUsername(TEST_USERNAME)).thenReturn(user);

        assertDoesNotThrow(()->{
            Authorization authorization = new Authorization(user, roles);
            assertTrue(authorization.isAuthorized());
        });
    }

    @Test
    public void test_shouldNotAuthorizesAnUserWithTheNonAcceptableRole(){
        List<UserRole> roles = new ArrayList<>(List.of(UserRole.ADMIN, UserRole.CUSTOMER));
        User user = new User(TEST_USERNAME, TEST_PASSWORD, UserRole.DELIVERY_AGENT);
        when(this.usersDao.findByUsername(TEST_USERNAME)).thenReturn(user);

        assertDoesNotThrow(()->{
            Authorization authorization = new Authorization(user, roles);
            assertFalse(authorization.isAuthorized());
        });
    }
}
