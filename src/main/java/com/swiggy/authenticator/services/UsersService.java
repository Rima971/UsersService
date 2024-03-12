package com.swiggy.authenticator.services;

import com.swiggy.authenticator.authentication.CustomUserDetails;
import com.swiggy.authenticator.entities.User;
import com.swiggy.authenticator.exceptions.UserNotFoundException;
import com.swiggy.authenticator.repositories.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UsersService implements UserDetailsService {
    @Autowired
    private UsersDao usersDao;
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user =  this.usersDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
        return new CustomUserDetails(user);
    }
}
