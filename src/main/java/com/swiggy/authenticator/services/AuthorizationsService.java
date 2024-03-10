package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.AuthorizeResponseDto;
import com.swiggy.authenticator.entities.Authorization;
import com.swiggy.authenticator.entities.User;
import com.swiggy.authenticator.enums.UserAction;
import com.swiggy.authenticator.enums.UserRole;
import com.swiggy.authenticator.repositories.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationsService {
    @Autowired
    private UsersDao usersDao;
    private AuthorizeResponseDto create(String username, List<UserRole> acceptableRoles){
        User user = this.usersDao.findByUsername(username);
        Authorization authorization = new Authorization(user, acceptableRoles);
        return new AuthorizeResponseDto(authorization.isAuthorized(), user.getId());
    }
}
