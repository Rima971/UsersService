package com.swiggy.authenticator.controllers;

import com.swiggy.authenticator.authentication.CustomUserDetails;
import com.swiggy.authenticator.dtos.AuthorizeRequestDto;
import com.swiggy.authenticator.dtos.AuthorizeResponseDto;
import com.swiggy.authenticator.dtos.GenericHttpResponse;
import com.swiggy.authenticator.exceptions.UserNotAuthorizedException;
import com.swiggy.authenticator.services.AuthorizationsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.swiggy.authenticator.constants.SuccessMessage.AUTHORIZATION_SUCCESSFULLY_CREATED;

@RestController
@RequestMapping("/api/authorizations")
public class AuthorizationsController {
    @Autowired
    private AuthorizationsService authorizationsService;

    @PostMapping("")
    public ResponseEntity<GenericHttpResponse> create(Authentication authentication, @Valid @RequestBody AuthorizeRequestDto authorizeRequestDto) throws UserNotAuthorizedException {
        CustomUserDetails user = (CustomUserDetails) authentication.getPrincipal();

        AuthorizeResponseDto response = this.authorizationsService.create(user.getUsername(), authorizeRequestDto);
        return GenericHttpResponse.create(HttpStatus.CREATED, AUTHORIZATION_SUCCESSFULLY_CREATED, response);
    }
}
