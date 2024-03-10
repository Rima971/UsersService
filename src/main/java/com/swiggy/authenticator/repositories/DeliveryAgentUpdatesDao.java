package com.swiggy.authenticator.repositories;

import com.swiggy.authenticator.entities.DeliveryAgent;
import com.swiggy.authenticator.entities.DeliveryAgentUpdate;
import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeliveryAgentUpdatesDao extends JpaRepository<DeliveryAgentUpdate, Integer> {
}
