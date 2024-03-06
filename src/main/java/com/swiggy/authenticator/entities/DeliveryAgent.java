package com.swiggy.authenticator.entities;

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
}
