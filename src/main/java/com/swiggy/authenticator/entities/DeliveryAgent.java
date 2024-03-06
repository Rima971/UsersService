package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "deliveryAgents")
@Data
public class DeliveryAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    private int currentLocationPincode;

    public DeliveryAgent(String username, String password, int currentLocationPincode) {
        this.user = new User(username, password, UserRole.DELIVERY_AGENT);
        this.currentLocationPincode = currentLocationPincode;
    }
}
