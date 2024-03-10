package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.DeliveryAgentDto;
import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import com.swiggy.authenticator.exceptions.DeliveryAgentNotFoundException;
import com.swiggy.authenticator.repositories.DeliveryAgentsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DeliveryAgentsService {
    @Autowired
    private DeliveryAgentsDao deliveryAgentsDao;
    public DeliveryAgent create(DeliveryAgentDto dto){
        DeliveryAgent customer = new DeliveryAgent(dto.getUsername(), dto.getPassword(), dto.getCurrentLocationPincode());
        return this.deliveryAgentsDao.save(customer);
    }

    public DeliveryAgent fetch(int deliveryAgentId) throws DeliveryAgentNotFoundException {
        return this.deliveryAgentsDao.findById(deliveryAgentId).orElseThrow(DeliveryAgentNotFoundException::new);
    }
    public List<DeliveryAgent> fetchAll(Optional<Integer> pincode, Optional<DeliveryAgentStatus> status){
        if (pincode.isPresent() && status.isPresent()){
            return this.deliveryAgentsDao.findALlByCurrentLocationPincodeAndStatus(pincode.get(), status.get());
        } else if (pincode.isPresent()){
            return this.deliveryAgentsDao.findALlByCurrentLocationPincode(pincode.get());
        } else if (status.isPresent()){
            return this.deliveryAgentsDao.findALlByStatus(status.get());
        }
        return this.deliveryAgentsDao.findAll();
    }
}
