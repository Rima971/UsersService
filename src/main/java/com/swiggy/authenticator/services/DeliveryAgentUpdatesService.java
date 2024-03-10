package com.swiggy.authenticator.services;

import com.swiggy.authenticator.dtos.DeliveryAgentUpdateDto;
import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.entities.DeliveryAgentUpdate;
import com.swiggy.authenticator.enums.DeliveryAgentUpdateType;
import com.swiggy.authenticator.exceptions.DeliveryAgentNotFoundException;
import com.swiggy.authenticator.exceptions.LocationMissingException;
import com.swiggy.authenticator.exceptions.StatusMissingException;
import com.swiggy.authenticator.exceptions.UnsupportedTypeException;
import com.swiggy.authenticator.repositories.DeliveryAgentUpdatesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeliveryAgentUpdatesService {
    @Autowired
    private DeliveryAgentsService deliveryAgentsService;

    @Autowired
    private DeliveryAgentUpdatesDao deliveryAgentUpdatesDao;

    public DeliveryAgentUpdate create(int deliveryAgentId, DeliveryAgentUpdateType type, DeliveryAgentUpdateDto dto) throws LocationMissingException, StatusMissingException, DeliveryAgentNotFoundException {
        DeliveryAgent deliveryAgent = this.deliveryAgentsService.fetch(deliveryAgentId);

        switch (type){
            case DeliveryAgentUpdateType.LOCATION -> {
                if (dto.getCurrentLocationPincode().isEmpty()) {
                    throw new LocationMissingException();
                }
                DeliveryAgentUpdate update = new DeliveryAgentUpdate(deliveryAgent, type, dto.getCurrentLocationPincode().get());
                return this.deliveryAgentUpdatesDao.save(update);
            }
            case DeliveryAgentUpdateType.STATUS -> {
                if (dto.getStatus().isEmpty()){
                    throw new StatusMissingException();
                }
                DeliveryAgentUpdate update = new DeliveryAgentUpdate(deliveryAgent, type, dto.getStatus().get());
                return this.deliveryAgentUpdatesDao.save(update);
            }
        }
        throw new UnsupportedTypeException();
    }
}
