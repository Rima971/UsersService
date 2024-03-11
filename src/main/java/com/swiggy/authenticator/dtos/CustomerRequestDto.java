package com.swiggy.authenticator.dtos;

import lombok.Data;

@Data
public class CustomerRequestDto extends UserDto {
    private int deliveryLocationPincode;

    public CustomerRequestDto(String username, String password, int deliveryLocationPincode) {
        super(username, password);
        this.deliveryLocationPincode = deliveryLocationPincode;
    }
}
