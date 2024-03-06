package com.swiggy.authenticator.entities;

import com.swiggy.authenticator.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "customers")
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    @Column(nullable = false)
    private int deliveryLocationPincode;

    public Customer(String username, String password, int deliveryLocationPincode) {
        this.user = new User(username, password, UserRole.CUSTOMER);
        this.deliveryLocationPincode = deliveryLocationPincode;
    }
}
