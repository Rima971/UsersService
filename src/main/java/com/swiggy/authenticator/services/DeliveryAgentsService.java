package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.DeliveryAgentDto;
import com.swiggy.authenticator.entities.DeliveryAgent;
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


//    public List<DeliveryAgent> fetchAll(Optional<Integer> userId){
//        if (userId.isPresent()){
//            return this.deliveryAgentsDao.findAllByUserId(userId.get());
//        }
//        return this.deliveryAgentsDao.findAll();
//    }
}
