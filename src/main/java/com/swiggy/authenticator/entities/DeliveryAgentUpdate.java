package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.DeliveryAgentStatus;
import com.swiggy.authenticator.enums.DeliveryAgentUpdateType;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "deliveryAgentUpdates")
@Data
public class DeliveryAgentUpdate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @CreatedDate
    private LocalDateTime createdAt;

    @ManyToOne
    private DeliveryAgent deliveryAgent;

    @Enumerated(EnumType.STRING)
    private DeliveryAgentUpdateType type;

    @Column(nullable = false)
    private String previousValue;

    @Column(nullable = false)
    private String updatedValue;

    public DeliveryAgentUpdate(DeliveryAgent deliveryAgent, DeliveryAgentUpdateType type, String updatedValue) {
        this.deliveryAgent = deliveryAgent;
        this.type = type;
        this.updatedValue = updatedValue;
    }

    private void update(int currentLocationPincode){
        this.previousValue = String.valueOf(this.deliveryAgent.getCurrentLocationPincode());
        this.deliveryAgent.setCurrentLocationPincode(currentLocationPincode);
        this.updatedValue = String.valueOf(currentLocationPincode);
    }

    private void update(DeliveryAgentStatus status){
        this.previousValue = this.deliveryAgent.getStatus().name();
        this.deliveryAgent.setStatus(status);
        this.updatedValue = status.name();
    }
}
