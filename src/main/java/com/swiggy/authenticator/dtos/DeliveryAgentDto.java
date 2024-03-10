package com.swiggy.authenticator.dtos;

import lombok.Data;

@Data
public class DeliveryAgentDto extends UserDto {
    private int currentLocationPincode;
    public DeliveryAgentDto(String username, String password, int currentLocationPincode) {
        super(username, password);
        this.currentLocationPincode = currentLocationPincode;
    }
}
