package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.AuthorizeRequestDto;
import com.swiggy.authenticator.dtos.AuthorizeResponseDto;
import com.swiggy.authenticator.entities.Authorization;
import com.swiggy.authenticator.entities.User;
import com.swiggy.authenticator.enums.UserAction;
import com.swiggy.authenticator.enums.UserRole;
import com.swiggy.authenticator.exceptions.UserNotAuthorizedException;
import com.swiggy.authenticator.exceptions.UserNotFoundException;
import com.swiggy.authenticator.repositories.UsersDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorizationsService {
    @Autowired
    private UsersDao usersDao;
    public AuthorizeResponseDto create(String username, AuthorizeRequestDto dto) throws UserNotFoundException, UserNotAuthorizedException {
        User user = this.usersDao.findByUsername(username).orElseThrow(UserNotFoundException::new);
        Authorization authorization = new Authorization(user, dto.getAcceptableRoles());
        if (authorization.isAuthorized()){
            return new AuthorizeResponseDto(true, user.getId());
        }
        throw new UserNotAuthorizedException();
    }
}
