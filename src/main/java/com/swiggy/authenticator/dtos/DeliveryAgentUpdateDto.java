package com.swiggy.authenticator.dtos;

import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Optional;

@Data
@AllArgsConstructor
public class DeliveryAgentUpdateDto {
    private Optional<Integer> currentLocationPincode;
    private Optional<String> status;

    public Optional<DeliveryAgentStatus> getStatus(){
        return this.status.map(DeliveryAgentStatus::valueOf);
    }
}
