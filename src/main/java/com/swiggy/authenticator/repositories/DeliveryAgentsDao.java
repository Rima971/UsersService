package com.swiggy.authenticator.repositories;

import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAgentsDao extends JpaRepository<DeliveryAgent, Integer> {
    public List<DeliveryAgent> findALlByStatus(DeliveryAgentStatus status);
    public List<DeliveryAgent> findALlByCurrentLocationPincode(int pincode);
    public List<DeliveryAgent> findALlByCurrentLocationPincodeAndStatus(int pincode, DeliveryAgentStatus status);

}
