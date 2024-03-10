package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.UserRole;
import lombok.Data;

import java.util.List;

@Data
public class Authorization {
    private User user;
    private List<UserRole> acceptableRoles;
    private boolean isAuthorized = false;

    public Authorization(User user, List<UserRole> acceptableRoles) {
        this.user = user;
        this.acceptableRoles = acceptableRoles;
        this.authorize();
    }

    private void authorize(){
        this.isAuthorized = acceptableRoles.contains(this.user.getRole());
    }
}
