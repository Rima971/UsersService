package com.swiggy.authenticator.dtos;

import com.swiggy.authenticator.enums.UserRole;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizeRequestDto {
    @NotNull(message = "acceptableRoles is required")
    @NotEmpty(message = "acceptableRoles cannot be empty")
    private List<UserRole> acceptableRoles;
}
