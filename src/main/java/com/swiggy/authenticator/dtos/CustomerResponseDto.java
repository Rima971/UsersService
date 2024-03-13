package com.swiggy.authenticator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CustomerResponseDto {
    private int id;
    private int userId;
    private int deliveryLocationPincode;
}
