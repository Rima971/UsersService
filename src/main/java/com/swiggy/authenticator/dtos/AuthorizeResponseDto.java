package com.swiggy.authenticator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthorizeResponseDto {
    private boolean isAuthorized;
    private int userId;
}
