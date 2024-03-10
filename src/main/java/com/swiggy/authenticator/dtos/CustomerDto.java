package com.swiggy.authenticator.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class CustomerDto extends UserDto {
    private int deliveryLocationPincode;

    public CustomerDto(String username, String password, int deliveryLocationPincode) {
        super(username, password);
        this.deliveryLocationPincode = deliveryLocationPincode;
    }
}
